package com.clubobsidian.trident.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.clubobsidian.trident.Event;
import com.clubobsidian.trident.EventBus;
import com.clubobsidian.trident.Listener;
import com.clubobsidian.trident.test.impl.TestCancelableEvent;
import com.clubobsidian.trident.test.impl.TestEvent;
import com.clubobsidian.trident.test.impl.TestListener;
import com.clubobsidian.trident.test.impl.TestOrderEvent;

public abstract class EventBusTest {

	@Test
	public void testEventFiring()
	{
		TestListener test = new TestListener("test");
		EventBus manager = this.createNewEventBus();
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
		EventBus manager = this.createNewEventBus();
		manager.registerEvents(test);
		
		manager.callEvent(new TestCancelableEvent());
		
		assertTrue("Test is not true, event was not cancelled", test.getTest());
	}
	
	@Test
	public void testOrder()
	{
		TestListener test = new TestListener("");
		EventBus manager = this.createNewEventBus();
		manager.registerEvents(test);
		
		manager.callEvent(new TestOrderEvent());
		
		assertTrue("Events were not listened in the correct order", test.getData().equals("012345"));
	}
	
	@Test
	public void testDoubleRegister()
	{
		TestListener test = new TestListener("test");
		EventBus manager = this.createNewEventBus();
		manager.registerEvents(test);
		
		assertFalse("Event was double registered", manager.registerEvents(test));
	}
	
	@Test
	public void testCalledEvent()
	{
		Event event = new TestEvent();
		EventBus manager = this.createNewEventBus();
		
		assertFalse("Event call ran when event did not exist", manager.callEvent(event));
	}
	
	@Test
	public void testUnregister()
	{
		TestListener test = new TestListener("test");
		EventBus manager = this.createNewEventBus();
		manager.registerEvents(test);
		
		assertTrue("Listener was not registered", manager.unregisterEvents(test));
		assertFalse("Listener was still registered", manager.unregisterEvents(test));
	}
	
	@Test
	public void testEmptyCallEvent()
	{
		EventBus manager = this.createNewEventBus();
		boolean called = manager.callEvent(new TestEvent());
		
		assertFalse("Event was called when a listener is not registered", called);
	}
	
	@Test
	public void testNullRegister()
	{
		EventBus manager = this.createNewEventBus();
		boolean registered = manager.registerEvents(null);
		
		assertFalse("Listener was still registered event though the listener was null", registered);
	}
	
	@Test
	public void testPrivateListener()
	{
		EventBus manager = this.createNewEventBus();
		boolean registered = manager.registerEvents(new PrivateListener());
		
		assertTrue("Listener was still registered event though the listener was private", registered);
	}
	
	protected abstract EventBus createNewEventBus();
	
	
	private class PrivateListener implements Listener {}
}