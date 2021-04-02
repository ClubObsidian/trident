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