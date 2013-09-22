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

package at.mfellner.java.test.steps;

import at.mfellner.java.Program;
import at.mfellner.java.brick.BroadcastBrick;
import at.mfellner.java.brick.BroadcastWaitBrick;
import at.mfellner.java.brick.PrintBrick;
import at.mfellner.java.brick.WaitBrick;
import at.mfellner.java.script.RestartableWhenScript;
import at.mfellner.java.script.Script;
import at.mfellner.java.script.StartScript;
import at.mfellner.java.script.WhenScript;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

import javax.inject.Inject;
import java.io.IOException;
import java.io.OutputStream;

public class ScriptSteps {
    private final OutputStream mOutput;
    private final Program mProgram;
    Script mLastScript;

    @Inject
    public ScriptSteps(OutputStream output, Program program) {
        mOutput = output;
        mProgram = program;
    }

    @Before
    public void before() throws IOException {
        mLastScript = null;
    }

    @Given("^'(\\w+)' has a Start script$")
    public void object_has_a_start_script(String object) {
        mLastScript = new StartScript();
        mProgram.getObject(object).addScript(mLastScript);
    }

    @Given("^'(\\w+)' has a When '(\\w+)' script$")
    public void object_has_a_when_script(String object, String message) {
        mLastScript = new WhenScript(message);
        mProgram.getObject(object).addScript(mLastScript);
    }

    @Given("^'(\\w+)' has a restartable When '(\\w+)' script$")
    public void object_has_a_restartable_when_script(String object, String message) {
        mLastScript = new RestartableWhenScript(message);
        mProgram.getObject(object).addScript(mLastScript);
    }

    @And("^this script has a Broadcast '(\\w+)' brick$")
    public void script_has_a_broadcast_brick(String message) {
        BroadcastBrick brick = new BroadcastBrick(message);
        mLastScript.addBrick(brick);
    }

    @And("^this script has a BroadcastWait '(\\w+)' brick$")
    public void script_has_a_broadcast_wait_brick(String message) {
        BroadcastWaitBrick brick = new BroadcastWaitBrick(message);
        mLastScript.addBrick(brick);
    }

    @And("^this script has a Print brick with$")
    public void script_has_a_print_brick(String string) {
        PrintBrick brick = new PrintBrick(string, mOutput);
        mLastScript.addBrick(brick);
    }

    @And("^this script has a Wait (\\d+) milliseconds brick$")
    public void script_has_a_wait_ms_brick(long millis) {
        WaitBrick brick = new WaitBrick(millis);
        mLastScript.addBrick(brick);
    }

    @And("^this script has a Wait (\\d+.?\\d*) seconds? brick$")
    public void script_has_a_wait_s_brick(int seconds) {
        WaitBrick brick = new WaitBrick(seconds * 1000);
        mLastScript.addBrick(brick);
    }
}
