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

/**
 * Enum representation of priority for registered events.
 * Default priority is {@link EventPriority#NORMAL}
 *
 * @author virustotalop
 */
public enum EventPriority {

    LOWEST(0),
    LOW(1),
    NORMAL(2),
    HIGH(3),
    HIGHEST(4),
    MONITOR(5);

    private final int value;

    EventPriority(final int value) {
        this.value = value;
    }

    /**
     * @return the integer representation of the underlying enum
     */
    public int getValue() {
        return this.value;
    }

    /**
     * @param value the integer value of an eventpriority
     * @return The EventPriority found by the given value
     */
    public static EventPriority getByValue(final int value) {
        if(value < 0 || value >= EventPriority.values().length) {
            return null;
        }
        return EventPriority.values()[value];
    }
}
