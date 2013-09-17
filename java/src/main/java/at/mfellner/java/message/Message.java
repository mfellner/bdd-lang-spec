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

import java.util.HashMap;

public class Message {
    private static final HashMap<String, Message> MESSAGES = new HashMap<>();
    private final String mMessage;
    private int mReponses;

    public static synchronized Message getMessage(String message) {
        if (MESSAGES.containsKey(message)) {
            return MESSAGES.get(message);
        } else {
            Message m = new Message(message);
            MESSAGES.put(message, m);
            return m;
        }
    }

    private Message(String message) {
        mMessage = message;
    }

    public String getText() {
        return mMessage;
    }

    public synchronized void waitForResponders() throws InterruptedException {
        if (mReponses > 0) {
            wait();
        }
    }

    public synchronized void startResponse() {
        mReponses++;
    }

    public synchronized void finishResponse() {
        if (mReponses <= 1) {
            mReponses = 0;
            notifyAll();
        } else {
            mReponses--;
        }
    }
}
