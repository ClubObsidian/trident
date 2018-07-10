package com.clubobsidian.trident.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.clubobsidian.trident.Listener;
import com.clubobsidian.trident.MethodExecutor;
import com.clubobsidian.trident.impl.javaassist.JavaAssistEventManager;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;

public final class JavaAssistUtil {

	private JavaAssistUtil() {}

	private static ClassPool pool;
	private static ConcurrentMap<String, AtomicInteger> map;
	
	static 
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
	
	public static ClassPool getClassPool()
	{
		return JavaAssistUtil.pool;
	}
	
	public static MethodExecutor generateMethodExecutor(Listener listener, Method method) 
	{
		if(listener == null || method == null)
			return null;

		try 
		{
			ClassLoader classLoader = JavaAssistEventManager.class.getClassLoader();
			Class<?> listenerClass = Class.forName(listener.getClass().getName(), true, classLoader);
			pool.insertClassPath(new LoaderClassPath(classLoader));
			pool.insertClassPath(new ClassClassPath(listenerClass));

			String callbackClassName = listener.getClass().getName() + method.getName();

			AtomicInteger collision = map.get(callbackClassName);
			int classNum = -1;
			if(collision == null)
			{
				collision = new AtomicInteger(0);
				classNum = 0;
				map.put(callbackClassName, collision);
			}
			else
			{
				classNum = collision.incrementAndGet();
			}

			callbackClassName += classNum;

			CtClass methodExecutorClass = pool.makeClass(callbackClassName);
			methodExecutorClass.setSuperclass(pool.get("com.clubobsidian.trident.MethodExecutor"));

			String eventType = method.getParameterTypes()[0].getName();
			String listenerType = listener.getClass().getName();
			
			StringBuilder sb = new StringBuilder();
			sb.append("public void execute(com.clubobsidian.trident.Event event)");
			sb.append("{");
			sb.append(eventType + " ev = " + "((" + eventType + ") event);");
			sb.append("((" + listenerType + ")" + "this.getListener())." + method.getName() + "(ev);");
			sb.append("}");

			CtMethod call = CtNewMethod.make(sb.toString(), methodExecutorClass);
			methodExecutorClass.addMethod(call);

			Class<?> cl = methodExecutorClass.toClass(classLoader, JavaAssistEventManager.class.getProtectionDomain());
			return (MethodExecutor) cl.getDeclaredConstructors()[0].newInstance(listener, method);
		} 
		catch (NotFoundException | CannotCompileException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		return null;
	}	
}