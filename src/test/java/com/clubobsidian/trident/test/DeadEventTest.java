/*
 *    Copyright 2021 Club Obsidian and contributors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.clubobsidian.trident.test;

import com.clubobsidian.trident.EventBus;
import com.clubobsidian.trident.eventbus.methodhandle.MethodHandleEventBus;
import com.clubobsidian.trident.test.impl.TestDeadEventListener;
import com.clubobsidian.trident.test.impl.TestEvent;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DeadEventTest {

    @Test
    public void testNoListener() {
        EventBus eventBus = new MethodHandleEventBus();
        boolean listenedTo = eventBus.callEvent(new TestEvent());

        assertFalse("Event was listened to", listenedTo);

    }

    @Test
    public void testDeadListener() {
        EventBus eventBus = new MethodHandleEventBus();
        TestDeadEventListener deadEventListener = new TestDeadEventListener();
        eventBus.registerEvents(deadEventListener);

        eventBus.callEvent(new TestEvent());

        System.out.println(deadEventListener.getTimesRan());
        assertTrue("Dead event triggered twice", deadEventListener.getTimesRan() == 1);
    }

}
