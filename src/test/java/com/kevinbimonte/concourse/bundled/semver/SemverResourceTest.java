package com.kevinbimonte.concourse.bundled.semver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kevinbimonte.concourse.bundled.mock.MockConfig;
import com.kevinbimonte.concourse.bundled.mock.MockResource;
import com.kevinbimonte.concourse.sdk.Pipeline;
import com.kevinbimonte.concourse.sdk.TestUtils;
import com.kevinbimonte.concourse.sdk.job.Job;
import com.kevinbimonte.concourse.sdk.step.task.Task;
import com.kevinbimonte.concourse.sdk.variable.Variable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SemverResourceTest {
    private Gson gson;

    @BeforeEach
    void setUp() {
        this.gson = new GsonBuilder().create();
    }

    @Test
    void createSemverResource() {
        // Arrange
        GitDriverConfig config = GitDriverConfig.create("git.concourse-ci.org/repo.git", "branch", "version");

        // Act
        SemverResource resource = SemverResource.createResource("semver", config);

        // Assert
        assertNotNull(resource);
        assertEquals("semver", resource.getName());

        assertInstanceOf(GitDriverConfig.class, resource.getConfig());
    }

    @Test
    void classicUsageComparison() {
        // Arrange
        Pipeline pipeline = new Pipeline();

        GitDriverConfig config = GitDriverConfig.create("git@github.com:concourse/concourse.git", "version", "version")
                .setPrivateKey(Variable.referenceVariable("concourse-repo-private-key"));

        SemverResource semverResource = SemverResource.createResource("version", config);
        MockResource mockResource = MockResource.createResource("trigger-resource", MockConfig.create());

        pipeline.addResource(semverResource).addResource(mockResource);

        Job job = new Job("some-job")
                .addStep(mockResource.createGetDefinition().enableTrigger())
                .addStep(semverResource.createGetDefinition())
                .addStep(Task.create("a-thing-that-needs-a-version", mockResource.createGetDefinition(), "task.yml"))
                .addStep(semverResource.createPutDefinition());

        pipeline.addJob(job);

        // Act
        JsonElement generated = JsonParser.parseString(pipeline.render());

        // Assert
        JsonElement expected = TestUtils.loadFromAssets("resource-type/semver/classic-usage.json");

        assertEquals(expected, generated);
    }

    @Test
    void checklessComparison() {
        // Arrange
        Pipeline pipeline = new Pipeline();

        GitDriverConfig config = GitDriverConfig.create("git@github.com:concourse/concourse.git", "version", "version")
                .setPrivateKey(Variable.referenceVariable("concourse-repo-private-key"));

        SemverResource semverResource = SemverResource.createResource("version", config);
        MockResource mockResource = MockResource.createResource("trigger-resource", MockConfig.create());

        pipeline.addResource(semverResource).addResource(mockResource);

        Job job = new Job("some-job")
                .addStep(mockResource.createGetDefinition().enableTrigger())
                .addStep(semverResource.createPutDefinition())
                .addStep(Task.create("a-thing-that-needs-a-version", mockResource.createGetDefinition(), "task.yml"))
                .addStep(semverResource.createPutDefinition());

        pipeline.addJob(job);

        // Act
        JsonElement generated = JsonParser.parseString(pipeline.render());

        // Assert
        JsonElement expected = TestUtils.loadFromAssets("resource-type/semver/checkless.json");

        assertEquals(expected, generated);
    }
}