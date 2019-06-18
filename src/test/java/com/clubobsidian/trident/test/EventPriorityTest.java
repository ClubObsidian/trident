package com.clubobsidian.trident.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.clubobsidian.trident.EventPriority;

public class EventPriorityTest {

	@Test
	public void testOutOfLowerBounds()
	{
		EventPriority priority = EventPriority.getByValue(-1);
		assertTrue("Priority was not null when going below bounds", priority == null);
	}
	
	@Test
	public void testOutOfUpperBounds()
	{
		EventPriority priority = EventPriority.getByValue(EventPriority.values().length);
		assertTrue("Priority was not null when going above bounds", priority == null);
	}
}