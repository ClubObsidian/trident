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
import com.clubobsidian.trident.test.impl.TestCancellableEvent;
import com.clubobsidian.trident.test.impl.TestListenerIgnore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class IgnoreCancelledTest {

    @Test
    public void ignoredCancelled() {
        TestListenerIgnore listener = new TestListenerIgnore();
        EventBus manager = new MethodHandleEventBus();
        manager.registerEvents(listener);
        manager.callEvent(new TestCancellableEvent());

        assertTrue("Event was not cancelled", listener.isCancelled());
        assertTrue("Event was not ignored", listener.getIgnored());
    }
}