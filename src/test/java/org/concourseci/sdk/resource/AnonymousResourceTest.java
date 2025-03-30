package org.concourseci.sdk.resource;

import org.concourseci.bundled.mock.MockResourceType;
import org.concourseci.bundled.registry.RegistryImageConfig;
import org.concourseci.bundled.registry.RegistryImageResourceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnonymousResourceTest {

    @Test
    void basicCreate() {
        // Arrange

        // Act
        AnonymousResource<RegistryImageConfig> resource = AnonymousResource.create("busybox");

        // Assert
        assertEquals(RegistryImageResourceType.getInstance().getName(), resource.getResourceType());
        assertEquals("busybox", resource.getConfig().getRepository());
        assertNull(resource.getConfig().getTag());
    }

    @Test
    void repositoryAndTag() {
        // Arrange

        // Act
        AnonymousResource<RegistryImageConfig> resource = AnonymousResource.create("busybox", "v1.0.0");

        // Assert
        assertEquals(RegistryImageResourceType.getInstance().getName(), resource.getResourceType());
        assertEquals("busybox", resource.getConfig().getRepository());
        assertEquals("v1.0.0", resource.getConfig().getTag());
    }

    @Test
    void registryConfig() {
        // Arrange
        RegistryImageConfig config = RegistryImageConfig.create("openjdk", "17");

        // Act
        AnonymousResource<RegistryImageConfig> resource = AnonymousResource.create(config);

        // Assert
        assertEquals(RegistryImageResourceType.getInstance().getName(), resource.getResourceType());
        assertEquals("openjdk", resource.getConfig().getRepository());
        assertEquals("17", resource.getConfig().getTag());
    }

    @Test
    void fullConfig() {
        // Arrange
        RegistryImageConfig config = RegistryImageConfig.create("openjdk", "17");

        // Act
        AnonymousResource<RegistryImageConfig> resource = AnonymousResource.create(MockResourceType.getInstance(), config);

        // Assert
        assertEquals(MockResourceType.getInstance().getName(), resource.getResourceType());
        assertEquals("openjdk", resource.getConfig().getRepository());
        assertEquals("17", resource.getConfig().getTag());
    }
}