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

import java.util.LinkedList;

public class Program implements Script.ScriptCallback {
    private final LinkedList<Script> mScripts;
    private ProgramCallback mCallback;
    private int mRunningScripts;

    public interface ProgramCallback {
        public void onProgramFinish(Program program);
    }

    public Program() {
        mScripts = new LinkedList<>();
    }

    public void setCallback(ProgramCallback callback) {
        mCallback = callback;
    }

    public void addScript(Script script) {
        script.setCallaback(this);
        mScripts.add(script);
    }

    public Script getLastScript() {
        return mScripts.getLast();
    }

    public void start() {
        for (Script script : mScripts) {
            if (script instanceof StartScript) {
                ((StartScript) script).startScript();
            }
        }
    }

    public boolean isRunning() {
        return mRunningScripts != 0;
    }

    @Override
    public void onScriptStart(Script script) {
        mRunningScripts++;
    }

    @Override
    public void onScriptFinish(Script script) {
        if (--mRunningScripts == 0 && mCallback != null) {
            mCallback.onProgramFinish(this);
        }
    }
}
