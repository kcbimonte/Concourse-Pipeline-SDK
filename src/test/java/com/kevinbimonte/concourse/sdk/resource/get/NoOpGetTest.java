package com.kevinbimonte.concourse.sdk.resource.get;

import com.kevinbimonte.concourse.bundled.git.GitResource;
import com.kevinbimonte.concourse.bundled.git.GitResourceConfig;
import com.kevinbimonte.concourse.sdk.job.Job;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoOpGetTest {
    @Test
    void createGetFromResource() {
        // Arrange
        GitResource resource = GitResource.createResource("repo", GitResourceConfig.create("https://git.company.com/repo.git"));

        // Act
        NoOpGet get = new NoOpGet(resource);

        // Assert
        assertEquals("repo", get.getIdentifier());
    }

    @Test
    void createGetFromResourceWithOtherIdentifier() {
        // Arrange
        GitResource resource = GitResource.createResource("repo", GitResourceConfig.create("https://git.company.com/repo.git"));

        // Act
        NoOpGet get = new NoOpGet(resource, "source");

        // Assert
        assertEquals("source", get.getIdentifier());
        assertEquals("repo", get.getResource());
    }

    @Test
    void setTimeout() {
        // Arrange
        GitResource resource = GitResource.createResource("repo", GitResourceConfig.create("https://git.company.com/repo.git"));
        NoOpGet get = new NoOpGet(resource);

        // Act
        get.setTimeout("5m");

        // Assert
        assertEquals("repo", get.getIdentifier());
        assertEquals("5m", get.getTimeout());
    }

    @Test
    void setInvalidTimeout() {
        // Arrange
        GitResource resource = GitResource.createResource("repo", GitResourceConfig.create("https://git.company.com/repo.git"));
        NoOpGet get = new NoOpGet(resource);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> get.setTimeout("5m1h"));
    }

    @Test
    void setAttempts() {
        // Arrange
        GitResource resource = GitResource.createResource("repo", GitResourceConfig.create("https://git.company.com/repo.git"));
        NoOpGet get = new NoOpGet(resource);

        // Act
        get.setAttempts(1);

        // Assert
        assertEquals("repo", get.getIdentifier());
        assertEquals(1, get.getAttempts());
    }

    @Test
    void setInvalidAttempts() {
        // Arrange
        GitResource resource = GitResource.createResource("repo", GitResourceConfig.create("https://git.company.com/repo.git"));
        NoOpGet get = new NoOpGet(resource);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> get.setAttempts(0));
    }

    @Test
    void addWorkerTags() {
        // Arrange
        GitResource resource = GitResource.createResource("repo", GitResourceConfig.create("https://git.company.com/repo.git"));
        NoOpGet get = new NoOpGet(resource);

        // Act
        get.addWorkerTag("tag").addWorkerTag("additional");

        // Assert
        assertEquals("repo", get.getIdentifier());
        assertEquals(2, get.getTags().size());
        assertTrue(get.getTags().contains("additional"));
    }

    @Test
    void enableTrigger() {
        // Arrange
        GitResource resource = GitResource.createResource("repo", GitResourceConfig.create("https://git.company.com/repo.git"));
        NoOpGet get = new NoOpGet(resource);

        // Act
        UnsupportedOperationException e = assertThrows(UnsupportedOperationException.class, get::enableTrigger);

        // Assert
        assertEquals("No-Op Get cannot have a trigger", e.getMessage());
    }

    @Test
    void addPassedRequirement() {
        // Arrange
        GitResource resource = GitResource.createResource("repo", GitResourceConfig.create("https://git.company.com/repo.git"));
        NoOpGet get = new NoOpGet(resource);

        // Act
        UnsupportedOperationException e = assertThrows(UnsupportedOperationException.class, () -> get.addPassedRequirement(new Job("job")));

        // Assert
        assertEquals("No-Op Get cannot have passed requirements", e.getMessage());
    }
}