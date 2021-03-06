/*
 * Copyright (C) 2014 The Calrissian Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.calrissian.flowmix.support;


import org.calrissian.mango.domain.Event;

public class WindowItem {
    Event event;
    long timestamp;
    String previousStream;

    public WindowItem(Event event, long timestamp, String previousStream) {
        this.event = event;
        this.timestamp = timestamp;
        this.previousStream = previousStream;
    }

    public Event getEvent() {
        return event;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getPreviousStream() {
      return previousStream;
    }

    @Override
    public String toString() {
        return "WindowItem{" +
                "event=" + event +
                ", timestamp=" + timestamp +
                '}';
    }
}
