package com.kevinbimonte.concourse.sdk;

import com.kevinbimonte.concourse.bundled.git.GitResource;
import com.kevinbimonte.concourse.bundled.git.GitResourceConfig;
import com.kevinbimonte.concourse.bundled.git.GitResourceType;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageConfig;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageResource;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageResourceType;
import com.kevinbimonte.concourse.sdk.job.Job;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PipelineTest {

    @Test
    void emptyPipeline() {
        // Arrange

        // Act
        Pipeline pipeline = new Pipeline();

        // Assert
        assertTrue(pipeline.getGroups().isEmpty());
        assertTrue(pipeline.getResources().isEmpty());
        assertTrue(pipeline.getJobs().isEmpty());
        assertTrue(pipeline.getResourceTypes().isEmpty());
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

        assertTrue(pipeline.getGroups().isEmpty());
        assertTrue(pipeline.getResources().isEmpty());
        assertTrue(pipeline.getResourceTypes().isEmpty());
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

        assertTrue(pipeline.getJobs().isEmpty());
        assertTrue(pipeline.getResources().isEmpty());
        assertTrue(pipeline.getResourceTypes().isEmpty());
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

        assertTrue(pipeline.getJobs().isEmpty());
        assertTrue(pipeline.getGroups().isEmpty());
        assertTrue(pipeline.getResourceTypes().isEmpty());
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

        assertTrue(pipeline.getGroups().isEmpty());
        assertTrue(pipeline.getJobs().isEmpty());
        assertTrue(pipeline.getResources().isEmpty());
    }
}