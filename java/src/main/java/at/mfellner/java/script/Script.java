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

import at.mfellner.java.brick.Brick;

import java.util.ArrayList;
import java.util.List;

public abstract class Script {
    private final List<Brick> mBrickList;
    private ScriptThread mThread;
    private ScriptCallback mCallback;

    public interface ScriptCallback {
        public void onScriptStart(Script script);

        public void onScriptFinished(Script script);
    }

    private class ScriptThread extends Thread {
        boolean mInterrupt = false;
        int mPendingExecutions = 1;

        @Override
        public void run() {
            onScriptStart();
            while (mPendingExecutions > 0) {
                mPendingExecutions--; //= mPendingExecutions - 1;
                onExecutionStart();
                for (int i = 0; i < mBrickList.size() && !mInterrupt; i++) {
                    mBrickList.get(i).getRunnable().run();
                }
                onExecutionFinish();
            }
            onScriptFinish();
        }
    }

    public Script() {
        mBrickList = new ArrayList<>();
    }

    public final void setCallaback(ScriptCallback callback) {
        mCallback = callback;
    }

    public final void addBrick(Brick brick) {
        mBrickList.add(brick);
    }

    public final synchronized void stop() {
        if (mThread != null) {
            mThread.mPendingExecutions = 0;
            mThread.mInterrupt = true;
        }
    }

    protected final synchronized void start() {
        if (mThread == null || mThread.mInterrupt || !mThread.isAlive()) {
            mThread = new ScriptThread();
            mThread.start();
        } else {
            mThread.mInterrupt = false;
            mThread.mPendingExecutions++;
        }
    }

    protected void onExecutionStart() {
    }

    protected void onExecutionFinish() {
    }

    private void onScriptStart() {
        if (mCallback != null) {
            mCallback.onScriptStart(this);
        }
    }

    private void onScriptFinish() {
        if (mCallback != null) {
            mCallback.onScriptFinished(this);
        }
    }
}
