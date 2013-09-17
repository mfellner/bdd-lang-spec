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
import at.mfellner.java.message.MessageHandler;
import at.mfellner.java.message.MessageHandler.MessageResponder;

public class WhenScript extends Script implements MessageResponder {
    protected final String mMessage;

    public WhenScript(String message) {
        mMessage = message;
        MessageHandler.INSTANCE.register(message, this);
    }

    @Override
    public void respondTo(String message) {
        if (mMessage.equals(message)) {
            Message.getMessage(mMessage).startResponse();
            this.start();
        }
    }

    @Override
    protected void onExecutionFinish() {
        Message.getMessage(mMessage).finishResponse();
        super.onExecutionFinish();
    }
}
