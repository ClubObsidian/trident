/*  
   Copyright 2018 Club Obsidian and contributors.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.clubobsidian.trident.impl.javaassist;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.clubobsidian.trident.Listener;
import com.clubobsidian.trident.MethodExecutor;

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
	private static ClassLoader classLoader;
	
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
	
	public static void setClassLoader(ClassLoader classLoader)
	{
		JavaAssistUtil.classLoader = classLoader;
	}
	
	public static ClassPool getClassPool()
	{
		return JavaAssistUtil.pool;
	}

	public static MethodExecutor generateMethodExecutor(final Listener listener, final Method method, final boolean ignoreCanceled) 
	{
		if(listener == null || method == null)
			return null;

		try 
		{
			ClassLoader classLoader = null;
			if(JavaAssistUtil.classLoader != null)
			{
				classLoader = JavaAssistUtil.classLoader;
			}
			else
			{
				classLoader = JavaAssistEventManager.class.getClassLoader();
			}

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
			return (MethodExecutor) cl.getDeclaredConstructors()[0].newInstance(listener, method, ignoreCanceled);
		} 
		catch (NotFoundException | CannotCompileException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		return null;
	}	
}
