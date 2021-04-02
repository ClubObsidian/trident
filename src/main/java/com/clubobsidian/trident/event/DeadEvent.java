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
package com.clubobsidian.trident.event;

import com.clubobsidian.trident.Event;

/**
 * Event that is called if an event is not
 * listened to by a listener.
 * Extends {@link Event}
 *
 * @author virustotalop
 */
public class DeadEvent extends Event {

    private final Event event;

    public DeadEvent(Event event) {
        this.event = event;
    }

    /**
     * Returns an event that was
     * not fired.
     *
     * @return event not fired
     */
    public Event getDeadEvent() {
        return this.event;
    }
}