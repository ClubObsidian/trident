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
 * Superclass of all events. Events should
 * extend this class.
 *
 * @author virustotalop
 */
public abstract class Event {

    /**
     * Uses reflection getClass().getSimpleName() to return
     * the name of the event class.
     *
     * @return name of the event
     */
    public String getEventName() {
        return this.getClass().getSimpleName();
    }
}
