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
package trident;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.clubobsidian.trident.Event;
import com.clubobsidian.trident.EventManager;
import com.clubobsidian.trident.impl.javaassist.JavaAssistEventManager;

public class JavaAssistEventManagerTest {

	@Test
	public void testEventFiring()
	{
		TestListener test = new TestListener("test");
		EventManager manager = new JavaAssistEventManager();
		boolean registered = manager.registerEvents(test);
		
		assertTrue("Event is not registered", registered);
		assertFalse("Test is not false", test.getTest());
		
		manager.callEvent(new TestEvent());
		
		assertTrue("Test is not true", test.getTest());
		
		manager.callEvent(new TestEvent());
		
		assertFalse("Test is not false", test.getTest());
	}
	
	@Test
	public void testEventCancellable()
	{
		TestListener test = new TestListener("test");
		EventManager manager = new JavaAssistEventManager();
		manager.registerEvents(test);
		
		manager.callEvent(new TestCancelableEvent());
		
		assertTrue("Test is not true, event was not cancelled", test.getTest());
	}
	
	@Test
	public void testOrder()
	{
		TestListener test = new TestListener("");
		EventManager manager = new JavaAssistEventManager();
		manager.registerEvents(test);
		
		manager.callEvent(new TestOrderEvent());
		
		assertTrue("Events were not listened in the correct order", test.getData().equals("012345"));
	}
	
	@Test
	public void testDoubleRegister()
	{
		TestListener test = new TestListener("test");
		EventManager manager = new JavaAssistEventManager();
		manager.registerEvents(test);
		
		assertFalse("Event was double registered", manager.registerEvents(test));
	}
	
	@Test
	public void testCalledEvent()
	{
		Event event = new TestEvent();
		EventManager manager = new JavaAssistEventManager();
		
		assertTrue("Event call ran when event did not exist", !manager.callEvent(event));
	}
	
	@Test
	public void testUnregister()
	{
		TestListener test = new TestListener("test");
		EventManager manager = new JavaAssistEventManager();
		manager.registerEvents(test);
		
		assertTrue("Listener was not registered", manager.unregisterEvents(test));
		assertFalse("Listener was still registered", manager.unregisterEvents(test));
	}
}