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
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.Test;

import com.clubobsidian.trident.Event;
import com.clubobsidian.trident.EventPriority;
import com.clubobsidian.trident.Listener;
import com.clubobsidian.trident.MethodExecutor;
import com.clubobsidian.trident.impl.javaassist.JavaAssistUtil;

public class JavaAssistMiscEventTest {

	@Test
	public void eventNameTest()
	{
		Event event = new TestEvent();
		assertTrue(event.getName().equals("TestEvent"));
	}
	
	@Test
	public void methodExecutorTest()
	{
		try 
		{
			Listener listener = new TestListener("test");
			Method testEventMethod = listener.getClass().getDeclaredMethod("test", TestEvent.class);
			MethodExecutor executor = JavaAssistUtil.generateMethodExecutor(listener, testEventMethod, false);
			
			assertTrue("Listeners are not equal for method executor", listener.equals(executor.getListener()));
			assertTrue("Methods are not equal for method executor", testEventMethod.equals(executor.getMethod()));
		} 
		catch (NoSuchMethodException | SecurityException e) 
		{
			e.printStackTrace();
		}		
	}
	
	@Test
	public void methodExecutorNullTest()
	{
		try 
		{
			Listener listener = null;
			Method testEventMethod = null;
			MethodExecutor executor = JavaAssistUtil.generateMethodExecutor(listener, testEventMethod, false);
			
			assertTrue("Method executor was not null", executor == null);
		} 
		catch (SecurityException e) 
		{
			e.printStackTrace();
		}		
	}
	
	@Test
	public void eventPriorityTest()
	{
		assertTrue("-1 event priority is not null", EventPriority.getByValue(-1) == null);
		assertTrue("Length event priority is not null", EventPriority.getByValue(EventPriority.values().length) == null);
	}
	
	@Test
	public void classPoolTest()
	{
		assertTrue("Class pool is null", JavaAssistUtil.getClassPool() != null);
	}
}