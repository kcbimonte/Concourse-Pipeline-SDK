package com.kevinbimonte.concourse.bundled.registry.get;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageConfig;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageResource;
import com.kevinbimonte.concourse.bundled.registry.RegistryVersion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistryGetTest {
    @Test
    void setLatestVersion() {
        // Arrange
        RegistryImageResource repo = RegistryImageResource.create("repo", RegistryImageConfig.create("busybox"));
        RegistryGet get = repo.createGetDefinition();

        // Act
        get.setLatestVersion();

        // Assert
        assertInstanceOf(JsonPrimitive.class, get.getVersion());
        assertEquals("latest", get.getVersion().getAsString());
    }

    @Test
    void setEveryVersion() {
        // Arrange
        RegistryImageResource repo = RegistryImageResource.create("repo", RegistryImageConfig.create("busybox"));
        RegistryGet get = repo.createGetDefinition();

        // Act
        get.setEveryVersion();

        // Assert
        assertInstanceOf(JsonPrimitive.class, get.getVersion());
        assertEquals("every", get.getVersion().getAsString());
    }

    @Test
    void setSpecificVersion() {
        // Arrange
        RegistryImageResource repo = RegistryImageResource.create("repo", RegistryImageConfig.create("concourse/concourse"));
        RegistryGet get = repo.createGetDefinition();

        // Act
        get.setVersion(RegistryVersion.create("5.2.1-ubuntu", "sha256:91f5d180d84ee4b2cedfae45771adac62c67c3f5f615448d3c34057c09404f27"));

        // Assert
        assertInstanceOf(JsonObject.class, get.getVersion());

        assertTrue(get.getVersion().getAsJsonObject().has("tag"));
        assertTrue(get.getVersion().getAsJsonObject().has("digest"));
        assertEquals("5.2.1-ubuntu", get.getVersion().getAsJsonObject().get("tag").getAsString());
        assertEquals("sha256:91f5d180d84ee4b2cedfae45771adac62c67c3f5f615448d3c34057c09404f27", get.getVersion().getAsJsonObject().get("digest").getAsString());
    }

    @Test
    void setSpecificVersionFromTag() {
        // Arrange
        RegistryImageResource repo = RegistryImageResource.create("repo", RegistryImageConfig.create("busybox"));
        RegistryGet get = repo.createGetDefinition();

        // Act
        get.setVersion(RegistryVersion.createFromTag("v1.0.0"));

        // Assert
        assertInstanceOf(JsonObject.class, get.getVersion());

        assertTrue(get.getVersion().getAsJsonObject().has("tag"));
        assertEquals("v1.0.0", get.getVersion().getAsJsonObject().get("tag").getAsString());

        assertFalse(get.getVersion().getAsJsonObject().has("digest"));
    }

    @Test
    void setSpecificVersionFromDigest() {
        // Arrange
        RegistryImageResource repo = RegistryImageResource.create("repo", RegistryImageConfig.create("concourse/concourse"));
        RegistryGet get = repo.createGetDefinition();

        // Act
        get.setVersion(RegistryVersion.createFromDigest("sha256:91f5d180d84ee4b2cedfae45771adac62c67c3f5f615448d3c34057c09404f27"));

        // Assert
        assertInstanceOf(JsonObject.class, get.getVersion());

        assertTrue(get.getVersion().getAsJsonObject().has("digest"));
        assertEquals("sha256:91f5d180d84ee4b2cedfae45771adac62c67c3f5f615448d3c34057c09404f27", get.getVersion().getAsJsonObject().get("digest").getAsString());

        assertFalse(get.getVersion().getAsJsonObject().has("tag"));
    }
}