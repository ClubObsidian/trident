package com.clubobsidian.trident.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.clubobsidian.trident.EventBus;
import com.clubobsidian.trident.eventbus.javassist.JavassistEventBus;
import com.clubobsidian.trident.test.impl.TestDeadEventListener;
import com.clubobsidian.trident.test.impl.TestEvent;

public class DeadEventTest {

	@Test
	public void testNoListener()
	{
		EventBus eventBus = new JavassistEventBus();
		boolean listenedTo = eventBus.callEvent(new TestEvent());
		
		assertFalse("Event was listened to", listenedTo);
		
	}
	
	@Test
	public void testDeadListener()
	{
		EventBus eventBus = new JavassistEventBus();
		TestDeadEventListener deadEventListener = new TestDeadEventListener();
		eventBus.registerEvents(deadEventListener);
		
		eventBus.callEvent(new TestEvent());
		
		System.out.println(deadEventListener.getTimesRan());
		assertTrue("Dead event triggered twice", deadEventListener.getTimesRan() == 1);
	}
	
}
