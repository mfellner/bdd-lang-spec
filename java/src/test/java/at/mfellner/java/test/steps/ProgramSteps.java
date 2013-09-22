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
import com.google.inject.Inject;
import com.google.inject.name.Named;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.assertEquals;

public class ProgramSteps {
    private final OutputStream mOutput;
    private final Program mProgram;
    private final Object mWaitLock;

    @Inject
    public ProgramSteps(OutputStream output, @Named("WaitLock") Object waitLock, Program program) {
        mOutput = output;
        mWaitLock = waitLock;
        mProgram = program;
    }

    @After
    public void after() throws IOException {
        mOutput.flush();
        mOutput.close();
    }

    @Given("^I have a Program$")
    public void I_have_a_program() {
        mProgram.reset();
    }

    @Given("^I have an Object '(\\w+)'$")
    public void I_have_an_object(String name) {
        mProgram.addNewObject(name);
    }

    @When("^I start the program$")
    public void I_start_the_program() {
        mProgram.start();
    }

    @And("^I wait until the program has stopped$")
    public void I_wait_until_the_program_has_stopped() throws InterruptedException {
        if (mProgram.isRunning()) {
            synchronized (mWaitLock) {
                mWaitLock.wait();
            }
        }
    }

    @Then("^I should see$")
    public void I_should_see(String string) {
        assertEquals(string, mOutput.toString());
    }
}
