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
package com.clubobsidian.trident.eventbus.reflection;

import com.clubobsidian.trident.Event;
import com.clubobsidian.trident.MethodExecutor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * {@inheritDoc}
 */
public class ReflectionMethodExecutor extends MethodExecutor {

    public ReflectionMethodExecutor(Object listener, Method method, boolean ignoreCancelled) {
        super(listener, method, ignoreCancelled);
    }

    @Override
    public void execute(final Event event) {
        try {
            this.getMethod().invoke(this.getListener(), event);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}