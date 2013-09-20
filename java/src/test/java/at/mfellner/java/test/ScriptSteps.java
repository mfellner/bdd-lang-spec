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

package at.mfellner.java.test;

import at.mfellner.java.*;
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
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;

public class ScriptSteps {
    Program mProgram;
    ByteArrayOutputStream mOutput;

    @Before
    public void before() {
        mProgram = new Program();
        mOutput = new ByteArrayOutputStream();
        mProgram.setCallback(new Program.ProgramCallback() {
            @Override
            public void onProgramFinish(Program program) {
                synchronized (program) {
                    program.notify();
                }
            }
        });
    }

    @Given("^I have a Start script$")
    public void I_have_a_start_script() {
        Script script = new StartScript();
        mProgram.addScript(script);
    }

    @Given("^I have a When '(\\w+)' script$")
    public void I_have_a_when_script(final String message) {
        Script script = new WhenScript(message);
        mProgram.addScript(script);
    }

    @Given("^I have a restartable When '(\\w+)' script$")
    public void I_have_a_restartable_when_script(final String message) {
        Script script = new RestartableWhenScript(message);
        mProgram.addScript(script);
    }

    @And("^this script has a Broadcast '(\\w+)' brick$")
    public void script_has_a_broadcast_brick(String message) {
        BroadcastBrick brick = new BroadcastBrick(message);
        mProgram.getLastScript().addBrick(brick);
    }

    @And("^this script has a BroadcastWait '(\\w+)' brick$")
    public void script_has_a_broadcast_wait_brick(String message) {
        BroadcastWaitBrick brick = new BroadcastWaitBrick(message);
        mProgram.getLastScript().addBrick(brick);
    }

    @And("^this script has a Print brick with$")
    public void script_has_a_print_brick(String string) {
        PrintBrick brick = new PrintBrick(string, mOutput);
        mProgram.getLastScript().addBrick(brick);
    }

    @And("^this script has a Wait (\\d+) milliseconds brick$")
    public void script_has_a_wait_ms_brick(long millis) {
        WaitBrick brick = new WaitBrick(millis);
        mProgram.getLastScript().addBrick(brick);
    }

    @And("^this script has a Wait (\\d+.?\\d*) seconds? brick$")
    public void script_has_a_wait_s_brick(int seconds) {
        WaitBrick brick = new WaitBrick(seconds * 1000);
        mProgram.getLastScript().addBrick(brick);
    }

    @When("^I start the program$")
    public void I_start_the_program() {
        mProgram.start();
    }

    @And("^I wait until the program has stopped$")
    public void I_wait_until_the_program_has_stopped() throws InterruptedException {
        if (mProgram.isRunning()) {
            synchronized (mProgram) {
                mProgram.wait();
            }
        }
    }

    @Then("^I should see$")
    public void I_should_see(String string) {
        assertEquals(string, mOutput.toString());
    }
}
