package com.kevinbimonte.concourse.sdk.step;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kevinbimonte.concourse.bundled.git.GitResource;
import com.kevinbimonte.concourse.bundled.git.GitResourceConfig;
import com.kevinbimonte.concourse.bundled.git.get.GitGet;
import com.kevinbimonte.concourse.sdk.Pipeline;
import com.kevinbimonte.concourse.sdk.TestUtils;
import com.kevinbimonte.concourse.sdk.job.Job;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SetPipelineTest {

    @Test
    void setPipelineAcross() {
        // Arrange
        Pipeline pipeline = new Pipeline();
        GitResource ci = GitResource.createResource("ci", GitResourceConfig.create("https://github.com/concourse/examples.git"));
        pipeline.addResource(ci);

        Job job = new Job("job");
        GitGet getDefinition = ci.createGetDefinition();
        job.addStep(getDefinition);

        AcrossVariable variable = AcrossVariable.create("pipeline").addValue("hello-world").addValue("time-triggered");

        String pipelineFile = String.format("pipelines/%s.yml", variable.getVariable());
        SetPipeline setPipeline = SetPipeline.createAcrossPipeline(variable.getVariable(), getDefinition, pipelineFile, variable);

        // Act
        job.addStep(setPipeline);
        pipeline.addJob(job);

        // Assert
        JsonElement generated = JsonParser.parseString(pipeline.render());
        JsonElement expected = TestUtils.loadFromAssets("across/set_pipeline_across.json");

        assertEquals(expected, generated);
    }
}