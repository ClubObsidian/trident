package com.clubobsidian.trident.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.clubobsidian.trident.Listener;
import com.clubobsidian.trident.impl.javaassist.JavaAssistEventManager;
import com.clubobsidian.trident.impl.javaassist.JavaAssistMethodExecutor;
import com.clubobsidian.trident.impl.javaassist.MethodCallback;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;

public class JavaAssistUtil {

	private JavaAssistUtil() {}
	
	private static ConcurrentMap<String, AtomicInteger> map;
	private static ClassPool pool;
	
	public static ClassPool getClassPool()
	{
		if(pool == null)
		{
			try 
			{
				map = new ConcurrentHashMap<>();
				pool = new ClassPool(true);
				pool.insertClassPath("com.clubobsidian.trident.impl.javaassist.MethodCallback");
				pool.insertClassPath("com.clubobsidian.trident.Event");
				pool.insertClassPath("com.clubobsidian.trident.Listener");
			} 
			catch (NotFoundException e) 
			{
				e.printStackTrace();
			}
		}
		return JavaAssistUtil.pool;
	}
	
	public static MethodCallback generateCallBack(Listener listener, Method method, ClassLoader classLoader) 
	{
		if(listener == null || method == null)
			return null;
		
		if(pool == null)
		{
			JavaAssistUtil.getClassPool();
		}

		try 
		{
			Class<?> listenerClass = Class.forName(listener.getClass().getName(), true, classLoader);
			pool.insertClassPath(new LoaderClassPath(classLoader));
			pool.insertClassPath(new ClassClassPath(listenerClass));

			String callbackClassName = listener.getClass().getName() + method.getName();

			AtomicInteger collision = map.get(callbackClassName);
			int classNum = 0;
			if(collision == null)
			{
				collision = new AtomicInteger(0);
				map.put(callbackClassName, collision);
			}
			else
			{
				classNum = collision.incrementAndGet();
			}

			callbackClassName += classNum;

			CtClass callBackClass = pool.makeClass(callbackClassName);
			callBackClass.addInterface(pool.get("com.clubobsidian.trident.impl.javaassist.MethodCallback"));

			CtField listenerField = CtField.make("private " + listener.getClass().getName() + " listener;", callBackClass);
			callBackClass.addField(listenerField);

			StringBuilder sb = new StringBuilder();
			sb.append("public " + listener.getClass().getSimpleName() + method.getName() + collision + "(" + listener.getClass().getName() + " listener)");
			sb.append("{");
			sb.append("this.listener = listener;");
			sb.append("}");

			CtConstructor con = CtNewConstructor.make(sb.toString(), callBackClass);
			callBackClass.addConstructor(con);

			String eventType = method.getParameterTypes()[0].getName();

			sb = new StringBuilder();
			sb.append("public void call(com.clubobsidian.trident.Event event)");
			sb.append("{");
			sb.append(eventType + " ev = " + "((" + eventType + ") event);");
			sb.append("listener." + method.getName() + "(ev);");
			sb.append("}");

			CtMethod call = CtNewMethod.make(sb.toString(), callBackClass);
			callBackClass.addMethod(call);

			Class<?> cl = callBackClass.toClass(classLoader, JavaAssistEventManager.class.getProtectionDomain());
			return (MethodCallback) cl.getDeclaredConstructors()[0].newInstance(listener);
		} 
		catch (NotFoundException | CannotCompileException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		return null;
	}

}
