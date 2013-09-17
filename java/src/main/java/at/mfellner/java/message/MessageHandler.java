/*
 * Copyright 2013 Maximilian Fellner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package at.mfellner.java.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum MessageHandler {
    INSTANCE;
    private final Map<String, List<MessageResponder>> mRegistry;

    public interface MessageResponder {
        public void respondTo(String message);
    }

    MessageHandler() {
        mRegistry = new HashMap<>();
    }

    public void register(String message, MessageResponder responder) {
        if (mRegistry.containsKey(message)) {
            List<MessageResponder> list = mRegistry.get(message);
            if (!list.contains(responder)) {
                list.add(responder);
            }
        } else {
            List<MessageResponder> list = new ArrayList<>();
            list.add(responder);
            mRegistry.put(message, list);
        }
    }

    public void send(Message m) {
        String message = m.getText();
        if (mRegistry.containsKey(message)) {
            for (MessageResponder responder : mRegistry.get(message)) {
                responder.respondTo(message);
            }
        }
    }
}
