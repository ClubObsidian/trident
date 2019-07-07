package com.clubobsidian.trident.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.clubobsidian.trident.Event;
import com.clubobsidian.trident.test.impl.TestEvent;

public class EventTest {

	@Test
	public void testName()
	{
		Event event = new TestEvent();
		assertTrue("Event name is not \"TestEvent\"", event.getName().equals("TestEvent"));
	}
}