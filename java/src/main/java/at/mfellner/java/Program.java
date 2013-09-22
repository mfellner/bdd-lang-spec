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

package at.mfellner.java;

import at.mfellner.java.message.Message;
import at.mfellner.java.message.MessageHandler;
import com.google.inject.Inject;

import java.util.HashMap;
import java.util.Map;

public class Program implements ScriptableObject.ObjectCallback {
    private final Map<String, ScriptableObject> mObjects;
    private final ProgramCallback mCallback;
    private int mNumObjectsScriptsFinished;

    public interface ProgramCallback {
        public void onProgramFinished(Program program);
    }

    @Inject
    public Program(ProgramCallback callback) {
        mCallback = callback;
        mObjects = new HashMap<>();
    }

    public void reset() {
        mObjects.clear();
        Message.clear();
        MessageHandler.INSTANCE.clear();
    }

    public void addNewObject(String name) {
        ScriptableObject object = new ScriptableObject(name, this);
        mObjects.put(name, object);
    }

    public ScriptableObject getObject(String name) {
        return mObjects.get(name);
    }

    public void start() {
        for (ScriptableObject object : mObjects.values()) {
            object.onStart();
        }
    }

    public boolean isRunning() {
        for (ScriptableObject object : mObjects.values()) {
            if (object.getScriptsRunning() > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public synchronized void onScriptsFinished(ScriptableObject object) {
        if (++mNumObjectsScriptsFinished == mObjects.size()) {
            mCallback.onProgramFinished(this);
        }
    }
}
