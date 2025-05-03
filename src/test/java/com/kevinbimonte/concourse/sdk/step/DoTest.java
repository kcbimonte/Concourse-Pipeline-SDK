package com.kevinbimonte.concourse.sdk.step;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kevinbimonte.concourse.bundled.mock.MockConfig;
import com.kevinbimonte.concourse.bundled.mock.MockResourceType;
import com.kevinbimonte.concourse.sdk.Pipeline;
import com.kevinbimonte.concourse.sdk.TestUtils;
import com.kevinbimonte.concourse.sdk.job.Job;
import com.kevinbimonte.concourse.sdk.resource.AnonymousResource;
import com.kevinbimonte.concourse.sdk.step.task.Task;
import com.kevinbimonte.concourse.sdk.step.task.config.Command;
import com.kevinbimonte.concourse.sdk.step.task.config.Platform;
import com.kevinbimonte.concourse.sdk.step.task.config.TaskConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DoTest {

    @Test
    void addStepInitializesSet() {
        // Arrange
        Do doStep = Do.create();

        SetPipeline setOne = SetPipeline.create("set-pipeline-one", "output/pipeline.yml");

        // Act
        doStep.addStep(setOne);

        // Assert
        assertEquals(1, doStep.getSteps().size());
    }

    @Test
    void chainAddSteps() {
        // Arrange
        Do doStep = Do.create();

        SetPipeline setOne = SetPipeline.create("set-pipeline-one", "output/pipeline.yml");
        SetPipeline setTwo = SetPipeline.create("set-pipeline-two", "output/pipeline.yml");

        // Act
        doStep.addStep(setOne).addStep(setTwo);

        // Assert
        assertEquals(2, doStep.getSteps().size());
    }

    @Test
    void doAcross() {
        // Arrange
        Pipeline pipeline = new Pipeline();
        Job job = new Job("job");

        AcrossVariable variable = AcrossVariable.create("name").addValue("Kaladin").addValue("Jasnah");
        Do doStep = Do.create().addAcrossVariable(variable);

        Command helloVariable = Command.createCommand("echo").addArg(String.format("Hello %s!", variable.getVariable()));
        TaskConfig sayHello = TaskConfig.create(Platform.LINUX, AnonymousResource.create(MockResourceType.getInstance(), MockConfig.create().mirrorSelf()), helloVariable);
        Task hello = Task.create("saying-hello", sayHello);

        Command byeVariable = Command.createCommand("echo").addArg(String.format("Bye %s!", variable.getVariable()));
        TaskConfig sayBye = TaskConfig.create(Platform.LINUX, AnonymousResource.create(MockResourceType.getInstance(), MockConfig.create().mirrorSelf()), byeVariable);
        Task bye = Task.create("saying-bye", sayBye);

        // Act
        doStep.addStep(hello).addStep(bye);
        job.addStep(doStep);
        pipeline.addJob(job);

        // Assert
        assertEquals(1, pipeline.getJobs().size());

        JsonElement generated = JsonParser.parseString(pipeline.render());
        JsonElement expected = TestUtils.loadFromAssets("across/do_across.json");

        assertEquals(expected, generated);
    }

    @Test
    void inputOutputAcross() {
        // Arrange
        Pipeline pipeline = new Pipeline();

        // Act

        // Assert
        JsonElement generated = JsonParser.parseString(pipeline.render());
        JsonElement expected = TestUtils.loadFromAssets("across/input_output_across.json");

        assertEquals(expected, generated);
    }

}