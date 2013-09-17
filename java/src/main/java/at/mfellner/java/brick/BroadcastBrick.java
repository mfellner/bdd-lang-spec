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

package at.mfellner.java.brick;

import at.mfellner.java.message.Message;
import at.mfellner.java.message.MessageHandler;

public class BroadcastBrick extends Brick {
    private final String mMessage;

    public BroadcastBrick(String message) {
        mMessage = message;
    }

    @Override
    public Runnable getRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                Message m = Message.getMessage(mMessage);
                MessageHandler.INSTANCE.send(m);
            }
        };
    }
}
