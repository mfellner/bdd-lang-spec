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

import at.mfellner.java.script.Script;
import at.mfellner.java.script.StartScript;

import java.util.ArrayList;
import java.util.List;

public class ScriptableObject implements Script.ScriptCallback {
    private final String mName;
    private final List<Script> mScripts;
    private final ObjectCallback mCallback;
    private int mRunningScripts;

    public interface ObjectCallback {
        public void onScriptsFinished(ScriptableObject object);
    }

    public ScriptableObject(String name, ObjectCallback callback) {
        mName = name;
        mCallback = callback;
        mScripts = new ArrayList<>();
    }

    public void addScript(Script script) {
        script.setCallaback(this);
        mScripts.add(script);
    }

    public void onStart() {
        for (Script script : mScripts) {
            if (script instanceof StartScript) {
                ((StartScript) script).startScript();
            }
        }
    }

    public String getName() {
        return mName;
    }

    public synchronized int getScriptsRunning() {
        return mRunningScripts;
    }

    @Override
    public synchronized void onScriptStart(Script script) {
        mRunningScripts++;
    }

    @Override
    public synchronized void onScriptFinished(Script script) {
        if (--mRunningScripts == 0) {
            mCallback.onScriptsFinished(this);
        }
    }
}
