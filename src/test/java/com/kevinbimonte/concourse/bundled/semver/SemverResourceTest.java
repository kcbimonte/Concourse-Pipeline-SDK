package com.kevinbimonte.concourse.bundled.semver;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kevinbimonte.concourse.bundled.mock.MockConfig;
import com.kevinbimonte.concourse.bundled.mock.MockResource;
import com.kevinbimonte.concourse.bundled.semver.get.SemverGet;
import com.kevinbimonte.concourse.bundled.semver.get.SemverGetConfig;
import com.kevinbimonte.concourse.bundled.semver.put.SemverPut;
import com.kevinbimonte.concourse.bundled.semver.put.SemverPutConfig;
import com.kevinbimonte.concourse.sdk.Pipeline;
import com.kevinbimonte.concourse.sdk.TestUtils;
import com.kevinbimonte.concourse.sdk.job.Job;
import com.kevinbimonte.concourse.sdk.step.task.Task;
import com.kevinbimonte.concourse.sdk.variable.Variable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SemverResourceTest {

    @Test
    void createSemverResource() {
        // Arrange
        GitDriverConfig config = GitDriverConfig.create("git.concourse-ci.org/repo.git", "branch", "version");

        // Act
        SemverResource resource = SemverResource.create("semver", config);

        // Assert
        assertNotNull(resource);
        assertEquals("semver", resource.getName());

        assertInstanceOf(GitDriverConfig.class, resource.getConfig());
    }

    @Test
    void createStandardGet() {
        // Arrange
        GitDriverConfig config = GitDriverConfig.create("git.concourse-ci.org/repo.git", "branch", "version");

        SemverResource resource = SemverResource.create("semver", config);

        // Act
        SemverGet get = resource.createGetDefinition();

        // Assert
        assertNotNull(get);
        assertEquals("semver", get.getIdentifier());
    }

    @Test
    void changeGetModifier() {
        // Arrange
        GitDriverConfig config = GitDriverConfig.create("git.concourse-ci.org/repo.git", "branch", "version");

        SemverResource resource = SemverResource.create("semver", config);

        // Act
        SemverGet get = resource.createGetDefinition("identifier");

        // Assert
        assertNotNull(get);
        assertEquals("identifier", get.getIdentifier());
        assertEquals("semver", get.getResource());
    }

    @Test
    void createStandardPut() {
        // Arrange
        GitDriverConfig config = GitDriverConfig.create("git.concourse-ci.org/repo.git", "branch", "version");

        SemverResource resource = SemverResource.create("semver", config);

        // Act
        SemverPut put = resource.createPutDefinition();

        // Assert
        assertNotNull(put);
        assertEquals("semver", put.getIdentifier());
    }

    @Test
    void changePutModifier() {
        // Arrange
        GitDriverConfig config = GitDriverConfig.create("git.concourse-ci.org/repo.git", "branch", "version");

        SemverResource resource = SemverResource.create("semver", config);

        // Act
        SemverPut put = resource.createPutDefinition("identifier");

        // Assert
        assertNotNull(put);
        assertEquals("identifier", put.getIdentifier());
        assertEquals("semver", put.getResource());
    }

    @Test
    void classicUsageComparison() {
        // Arrange
        Pipeline pipeline = new Pipeline();

        GitDriverConfig config = GitDriverConfig.create("git@github.com:concourse/concourse.git", "version", "version")
                .setPrivateKey(Variable.referenceVariable("concourse-repo-private-key"));

        SemverResource semverResource = SemverResource.create("version", config);
        MockResource mockResource = MockResource.create("trigger-resource", MockConfig.create());

        pipeline.addResource(semverResource).addResource(mockResource);

        Job job = new Job("some-job")
                .addStep(mockResource.createGetDefinition().enableTrigger())
                .addStep(
                        semverResource.createGetDefinition()
                                .setConfig(SemverGetConfig.create().setBumpType(SemverBump.MAJOR))
                )
                .addStep(Task.create("a-thing-that-needs-a-version", mockResource.createGetDefinition(), "task.yml"))
                .addStep(
                        semverResource.createPutDefinition()
                                .setParams(SemverPutConfig.createWithFile("version/version"))
                );

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

        SemverResource semverResource = SemverResource.create("version", config);
        MockResource mockResource = MockResource.create("trigger-resource", MockConfig.create());

        pipeline.addResource(semverResource).addResource(mockResource);

        Job job = new Job("some-job")
                .addStep(mockResource.createGetDefinition().enableTrigger())
                .addStep(
                        semverResource.createPutDefinition()
                                .setParams(SemverPutConfig.createWithGetLatest().setBump(SemverBump.MAJOR))
                )
                .addStep(Task.create("a-thing-that-needs-a-version", mockResource.createGetDefinition(), "task.yml"))
                .addStep(
                        semverResource.createPutDefinition()
                                .setParams(SemverPutConfig.createWithFile("version/version"))
                );

        pipeline.addJob(job);

        // Act
        JsonElement generated = JsonParser.parseString(pipeline.render());

        // Assert
        JsonElement expected = TestUtils.loadFromAssets("resource-type/semver/checkless.json");

        assertEquals(expected, generated);
    }
}