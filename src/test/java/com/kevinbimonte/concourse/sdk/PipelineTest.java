package com.kevinbimonte.concourse.sdk;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kevinbimonte.concourse.bundled.git.GitResource;
import com.kevinbimonte.concourse.bundled.git.GitResourceConfig;
import com.kevinbimonte.concourse.bundled.git.GitResourceType;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageConfig;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageResource;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageResourceType;
import com.kevinbimonte.concourse.sdk.job.Job;
import com.kevinbimonte.concourse.sdk.varsource.SSMVarSource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PipelineTest {

    @Test
    void emptyPipeline() {
        // Arrange

        // Act
        Pipeline pipeline = new Pipeline();

        // Assert
        assertNull(pipeline.getGroups());
        assertNull(pipeline.getResources());
        assertNull(pipeline.getJobs());
        assertNull(pipeline.getResourceTypes());
    }

    @Test
    void jobs() {
        // Arrange
        Job job1 = new Job("my_job");
        Job job2 = new Job("my_second_job");
        Pipeline pipeline = new Pipeline();

        // Act
        pipeline.addJob(job1).addJob(job2);

        // Assert
        assertFalse(pipeline.getJobs().isEmpty());
        assertEquals(2, pipeline.getJobs().size());

        assertNull(pipeline.getGroups());
        assertNull(pipeline.getResources());
        assertNull(pipeline.getResourceTypes());
    }

    @Test
    void groups() {
        // Arrange
        Group group1 = new Group("my_group");
        Group group2 = new Group("my_second_group");
        Pipeline pipeline = new Pipeline();

        // Act
        pipeline.addGroup(group1).addGroup(group2);

        // Assert
        assertFalse(pipeline.getGroups().isEmpty());
        assertEquals(2, pipeline.getGroups().size());

        assertNull(pipeline.getJobs());
        assertNull(pipeline.getResources());
        assertNull(pipeline.getResourceTypes());
    }

    @Test
    void resources() {
        // Arrange
        RegistryImageResource busybox = RegistryImageResource.createResource("busybox", RegistryImageConfig.create("busybox"));
        GitResource repo = GitResource.createResource("repo", GitResourceConfig.create("https://git.company.com/group/project.git"));
        Pipeline pipeline = new Pipeline();

        // Act
        pipeline.addResource(busybox).addResource(repo);

        // Assert
        assertFalse(pipeline.getResources().isEmpty());
        assertEquals(2, pipeline.getResources().size());

        assertNull(pipeline.getJobs());
        assertNull(pipeline.getGroups());
        assertNull(pipeline.getResourceTypes());
    }

    @Test
    void resourceTypes() {
        // Arrange
        RegistryImageResourceType registry = RegistryImageResourceType.getInstance();
        GitResourceType git = GitResourceType.getInstance();
        Pipeline pipeline = new Pipeline();

        // Act
        pipeline.addResourceType(registry).addResourceType(git);

        // Assert
        assertFalse(pipeline.getResourceTypes().isEmpty());
        assertEquals(2, pipeline.getResourceTypes().size());

        assertNull(pipeline.getGroups());
        assertNull(pipeline.getJobs());
        assertNull(pipeline.getResources());
    }

    @Test
    void varSource() {
        // Arrange
        Pipeline pipeline = new Pipeline();

        SSMVarSource varSource = SSMVarSource.create("ssm", "us-east-1");

        // Act
        pipeline.addVarSource(varSource);

        // Assert
        JsonElement render = JsonParser.parseString(pipeline.render());

        assertEquals(1, pipeline.getVarSources().size());
        assertTrue(render.getAsJsonObject().has("var_sources"));

        JsonObject entry = render.getAsJsonObject().get("var_sources").getAsJsonArray().get(0).getAsJsonObject();

        assertTrue(entry.has("config"));
        assertTrue(entry.getAsJsonObject("config").has("region"));
        assertEquals("us-east-1", entry.getAsJsonObject("config").getAsJsonPrimitive("region").getAsString());
    }
}