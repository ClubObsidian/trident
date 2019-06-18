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
package com.clubobsidian.trident.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import com.clubobsidian.trident.EventBus;
import com.clubobsidian.trident.Listener;
import com.clubobsidian.trident.impl.javaassist.JavaAssistEventBus;
import com.clubobsidian.trident.test.impl.TestListener;

import javassist.CannotCompileException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JavaAssistEventBusTest extends EventBusTest {
	
	@Test
	public void testClassPoolExists()
	{
		JavaAssistEventBus eventBus = new JavaAssistEventBus();
		assertTrue("No class pool for JavaAssist event bus", eventBus.getClassPool() != null);
	}
	
	@Test
	public void testNullMethodOnMethodExecutor()
	{
		JavaAssistEventBus eventBus = new JavaAssistEventBus();
		try 
		{
			Method generate = eventBus.getClass().getDeclaredMethod("generateMethodExecutor", Listener.class, Method.class, boolean.class);
			generate.setAccessible(true);
			Object methodExecutor = generate.invoke(eventBus, null, null, false);
			assertTrue("Method executor is not null even though a null listener was passed", methodExecutor == null);
		} 
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFrozenMethodExecutor()
	{
		try 
		{
			JavaAssistEventBus eventBus = new JavaAssistEventBus();
			eventBus.registerEvents(new TestListener("data"));
			Field mapField = JavaAssistEventBus.class.getDeclaredField("map");
			mapField.setAccessible(true);
			ConcurrentMap<String, AtomicInteger> savedMap = (ConcurrentMap<String, AtomicInteger>) mapField.get(null);

			mapField.set(null, new ConcurrentHashMap<String,AtomicInteger>());
			boolean registered = false;

			registered = eventBus.registerEvents(new TestListener("data"));

			mapField.set(null, savedMap);
			System.out.println("Set map back to prior state");

			assertFalse("Was able to register event with invalid map", registered);
		} 
		catch (IllegalArgumentException | SecurityException | IllegalAccessException | NoSuchFieldException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	protected EventBus createNewEventBus() 
	{
		return new JavaAssistEventBus();
	}
}