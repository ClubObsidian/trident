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
package com.clubobsidian.trident;

import com.clubobsidian.trident.event.DeadEvent;
import com.clubobsidian.trident.util.ClassUtil;
import com.clubobsidian.trident.util.EventDoublyLinkedList;
import com.clubobsidian.trident.util.EventNode;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Abstract class for implementing EventBus
 * classes. For an example see @see com.clubobsidian.trident.eventbus.javassist.JavassistEventBus
 *
 * @author virustotalop
 */
public abstract class EventBus {

    private final Map<Object, Queue<MethodExecutor>> registeredEventListeners;
    private final Map<Class<?>, EventDoublyLinkedList> registeredExecutors;

    public EventBus() {
        this.registeredEventListeners = new ConcurrentHashMap<>();
        this.registeredExecutors = new ConcurrentHashMap<>();
    }

    /**
     * @param event Event to call
     * @return if the event was called
     */
    public boolean callEvent(final Event event) {
        EventDoublyLinkedList executors = this.registeredExecutors.get(event.getClass());

        if (executors == null) {
            if (!(event instanceof DeadEvent)) {
                this.callEvent(new DeadEvent(event));
            }
            return false;
        }

        boolean ran = false;
        EventNode node = executors.getHead();
        if (node != null) {
            ran = true;
        }
        while (node != null) {
            MethodExecutor executor = node.getData();
            if (event instanceof Cancellable) {
                Cancellable cancellable = (Cancellable) event;
                if (cancellable.isCancelled() && executor.isIgnoringCancelled()) {
                    node = node.getNext();
                    continue;
                }
            }
            executor.execute(event);
            node = node.getNext();
        }

        return ran;
    }

    /**
     * @param listener listener to be registered
     * @return if the listener was registered
     */
    public boolean registerEvents(final Object listener) {
        if (listener == null) {
            return false;
        } else if (this.registeredEventListeners.containsKey(listener)) {
            return false;
        }

        Class<?> cl = listener.getClass();
        for (Method method : cl.getDeclaredMethods()) {
            for (EventHandler handler : method.getAnnotationsByType(EventHandler.class)) {
                if (method.getParameters().length == 1) {
                    Class<?> eventClass = method.getParameterTypes()[0];
                    if (ClassUtil.hasEventSuperClass(eventClass)) {
                        if (this.registeredExecutors.get(eventClass) == null) {
                            this.registeredExecutors.put(eventClass, new EventDoublyLinkedList());
                        }
                        if (this.registeredEventListeners.get(listener) == null) {
                            this.registeredEventListeners.put(listener, new ConcurrentLinkedQueue<>());
                        }

                        boolean ignoreCancelled = handler.ignoreCancelled();
                        MethodExecutor executor = this.createMethodExecutor(listener, method, ignoreCancelled);

                        if (executor == null) {
                            return false;
                        }

                        this.registeredExecutors.get(eventClass).insert(executor, handler.priority());
                        this.registeredEventListeners.get(listener).add(executor);
                    }
                }
            }
        }
        return true;
    }

    protected abstract MethodExecutor createMethodExecutor(Object listener, Method method, boolean ignoreCancelled);

    /**
     * @param listener listener to be unregistered
     * @return if the listener was unregistered
     */
    public boolean unregisterEvents(Object listener) {
        Queue<MethodExecutor> executors = this.registeredEventListeners.remove(listener);
        if (executors == null)
            return false;

        for (EventDoublyLinkedList list : this.registeredExecutors.values()) {
            for (MethodExecutor executor : executors) {
                list.remove(executor);
            }
        }
        return true;
    }
}