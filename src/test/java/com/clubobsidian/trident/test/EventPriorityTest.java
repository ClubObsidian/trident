package com.clubobsidian.trident.test;

import com.clubobsidian.trident.EventPriority;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EventPriorityTest {

    @Test
    public void testOutOfLowerBounds() {
        EventPriority priority = EventPriority.getByValue(-1);
        assertTrue("Priority was not null when going below bounds", priority == null);
    }

    @Test
    public void testOutOfUpperBounds() {
        EventPriority priority = EventPriority.getByValue(EventPriority.values().length);
        assertTrue("Priority was not null when going above bounds", priority == null);
    }
}