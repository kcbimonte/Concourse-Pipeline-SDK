package com.kevinbimonte.concourse.bundled.git.get;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.kevinbimonte.concourse.bundled.git.GitResource;
import com.kevinbimonte.concourse.bundled.git.GitResourceConfig;
import com.kevinbimonte.concourse.bundled.git.GitVersion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GitGetTest {
    @Test
    void setLatestVersion() {
        // Arrange
        GitResource repo = GitResource.createResource("repo", GitResourceConfig.create("https://git.company.com/group/repo.git"));
        GitGet get = repo.createGetDefinition();

        // Act
        get.setLatestVersion();

        // Assert
        assertInstanceOf(JsonPrimitive.class, get.getVersion());
        assertEquals("latest", get.getVersion().getAsString());
    }

    @Test
    void setEveryVersion() {
        // Arrange
        GitResource repo = GitResource.createResource("repo", GitResourceConfig.create("https://git.company.com/group/repo.git"));
        GitGet get = repo.createGetDefinition();

        // Act
        get.setEveryVersion();

        // Assert
        assertInstanceOf(JsonPrimitive.class, get.getVersion());
        assertEquals("every", get.getVersion().getAsString());
    }

    @Test
    void setSpecificVersion() {
        // Arrange
        GitResource repo = GitResource.createResource("repo", GitResourceConfig.create("https://git.company.com/group/repo.git"));
        GitGet get = repo.createGetDefinition();

        // Act
        get.setVersion(GitVersion.create("12345"));

        // Assert
        assertInstanceOf(JsonObject.class, get.getVersion());
        assertTrue(get.getVersion().getAsJsonObject().has("ref"));
        assertEquals("12345", get.getVersion().getAsJsonObject().get("ref").getAsString());
    }
}