/*  
   Copyright 2019 Club Obsidian and contributors.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.clubobsidian.trident.test;

import com.clubobsidian.trident.EventBus;
import com.clubobsidian.trident.eventbus.javassist.JavassistEventBus;
import com.clubobsidian.trident.test.impl.TestListener;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JavaAssistEventBusTest extends EventBusTest {

    @Test
    public void testClassPoolExists() {
        JavassistEventBus eventBus = new JavassistEventBus();
        assertTrue("No class pool for JavaAssist event bus", eventBus.getClassPool() != null);
    }

    @Test
    public void testNullMethodOnMethodExecutor() {
        JavassistEventBus eventBus = new JavassistEventBus();
        try {
            Method generate = eventBus.getClass().getDeclaredMethod("generateMethodExecutor", Object.class, Method.class, boolean.class);
            generate.setAccessible(true);
            Object methodExecutor = generate.invoke(eventBus, null, null, false);
            assertTrue("Method executor is not null even though a null listener was passed", methodExecutor == null);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFrozenMethodExecutor() {
        try {
            JavassistEventBus eventBus = new JavassistEventBus();
            eventBus.registerEvents(new TestListener("data"));
            Field mapField = JavassistEventBus.class.getDeclaredField("map");
            mapField.setAccessible(true);
            ConcurrentMap<String, AtomicInteger> savedMap = (ConcurrentMap<String, AtomicInteger>) mapField.get(null);

            mapField.set(null, new ConcurrentHashMap<String, AtomicInteger>());
            boolean registered = eventBus.registerEvents(new TestListener("data"));

            mapField.set(null, savedMap);
            System.out.println("Set map back to prior state");

            assertFalse("Was able to register event with invalid map", registered);
        } catch (IllegalArgumentException | SecurityException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected EventBus createNewEventBus() {
        return new JavassistEventBus();
    }
}