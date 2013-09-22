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

package at.mfellner.java.test.guice;

import at.mfellner.java.Program;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class CucumberProgramCallback implements Program.ProgramCallback {
    private final Object mWaitLock;

    @Inject
    public CucumberProgramCallback(@Named("WaitLock") Object waitLock) {
        mWaitLock = waitLock;
    }

    @Override
    public void onProgramFinished(Program program) {
        synchronized (mWaitLock) {
            mWaitLock.notifyAll();
        }
    }
}
