package com.clubobsidian.trident.test;

import com.clubobsidian.trident.EventBus;
import com.clubobsidian.trident.test.impl.TestEventBus;
import com.clubobsidian.trident.test.impl.TestListener;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class OtherEventBusTest {

    @Test
    public void testNullExecutor() {
        EventBus bus = new TestEventBus();
        boolean registered = bus.registerEvents(new TestListener("data"));
        assertFalse("Listener was registered even though MethodExecutor is null", registered);
    }
}