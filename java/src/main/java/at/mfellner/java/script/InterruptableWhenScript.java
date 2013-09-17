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

package at.mfellner.java.script;

import at.mfellner.java.message.Message;

public class InterruptableWhenScript extends WhenScript {

    public InterruptableWhenScript(String message) {
        super(message);
    }

    @Override
    public void respondTo(String message) {
        if (mMessage.equals(message)) {
            this.stop();
            Message m = Message.getMessage(mMessage);
            synchronized (m) {
                m.notifyAll();
            }
            m.startResponse();
            this.start();
        }
    }
}
