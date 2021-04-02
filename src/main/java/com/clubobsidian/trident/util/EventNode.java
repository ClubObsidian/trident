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
package com.clubobsidian.trident.util;

import com.clubobsidian.trident.MethodExecutor;

/**
 * Node class for events. Used
 * in {@link com.clubobsidian.trident.util.EventDoublyLinkedList}
 *
 * @author virustotalop
 */
public class EventNode {
    private MethodExecutor data;
    private int priority;
    private EventNode next;
    private EventNode prev;

    public EventNode(MethodExecutor data, int priority) {
        this.data = data;
        this.priority = priority;
    }

    /**
     * @return MethodExecutor for the node
     */
    public MethodExecutor getData() {
        return this.data;
    }

    /**
     * @return integer representation of the {@link com.clubobsidian.trident.Event}'s priority
     */
    public int getPriority() {
        return this.priority;
    }

    /**
     * @return node next to this node
     */
    public synchronized EventNode getNext() {
        return this.next;
    }

    /**
     * @param next node to be set as the next node
     */
    public synchronized void setNext(final EventNode next) {
        this.next = next;
    }

    /**
     * @return node behind this node
     */
    public synchronized EventNode getPrev() {
        return this.prev;
    }

    /**
     * @param prev node to be set as the previous node
     */
    public synchronized void setPrev(final EventNode prev) {
        this.prev = prev;
    }
}
