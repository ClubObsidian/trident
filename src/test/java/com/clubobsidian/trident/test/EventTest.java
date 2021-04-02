package com.clubobsidian.trident.test;

import com.clubobsidian.trident.Event;
import com.clubobsidian.trident.test.impl.TestEvent;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EventTest {

    @Test
    public void testName() {
        Event event = new TestEvent();
        assertTrue("Event name is not \"TestEvent\"", event.getName().equals("TestEvent"));
    }
}