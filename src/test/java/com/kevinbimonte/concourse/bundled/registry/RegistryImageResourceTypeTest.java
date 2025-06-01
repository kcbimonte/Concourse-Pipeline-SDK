package com.kevinbimonte.concourse.bundled.registry;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kevinbimonte.concourse.bundled.registry.get.RegistryGetConfig;
import com.kevinbimonte.concourse.sdk.Pipeline;
import com.kevinbimonte.concourse.sdk.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegistryImageResourceTypeTest {

    @Test
    void simpleResourceType() {
        // Arrange

        // Act
        RegistryImageResourceType type = RegistryImageResourceType.create();

        // Assert
        assertNotNull(type);
        assertEquals("registry-image", type.getType());
        assertEquals("registry-image", type.getName());
    }

    @Test
    void overrideResourceTypeVersion() {
        // Arrange
        RegistryImageConfig config = RegistryImageConfig.create("concourse/registry-image-resource");

        // Act
        RegistryImageResourceType type = RegistryImageResourceType.create(config);

        // Assert
        assertNotNull(type);
        assertEquals("registry-image", type.getType());
        assertEquals("registry-image", type.getName());
        assertInstanceOf(RegistryImageConfig.class, type.getConfig());

        RegistryImageConfig c = (RegistryImageConfig) type.getConfig();

        assertEquals("concourse/registry-image-resource", c.getRepository());
    }

    @Test
    void overrideResourceTypeVersionAndName() {
        // Arrange
        RegistryImageConfig config = RegistryImageConfig.create("concourse/registry-image-resource");

        // Act
        RegistryImageResourceType type = RegistryImageResourceType.create("new-registry-image", config);

        // Assert
        assertNotNull(type);
        assertEquals("registry-image", type.getType());
        assertEquals("new-registry-image", type.getName());
        assertInstanceOf(RegistryImageConfig.class, type.getConfig());

        RegistryImageConfig c = (RegistryImageConfig) type.getConfig();

        assertEquals("concourse/registry-image-resource", c.getRepository());
    }

    @Test
    void markTypePrivileged() {
        // Arrange
        RegistryImageResourceType type = RegistryImageResourceType.create();

        // Act
        type.markPrivileged();

        // Assert
        assertTrue(type.getPrivileged());
    }

    @Test
    void setGetParamsForPipeline() {
        // Arrange
        RegistryImageResourceType type = RegistryImageResourceType.create();
        RegistryGetConfig config = RegistryGetConfig.create().skipDownload();

        // Act
        type.setParams(config);

        // Assert
        assertInstanceOf(RegistryGetConfig.class, type.getParams());

        RegistryGetConfig c = (RegistryGetConfig) type.getParams();

        assertTrue(c.getSkipDownload());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1ms", "1s", "1m", "1h", "1d", "1w", "1y", "1y1m100s"})
    void validCheckDelays(String duration) {
        // Arrange
        RegistryImageResourceType type;

        // Act
        type = assertDoesNotThrow(() -> RegistryImageResourceType.create().setCheckEvery(duration));

        // Assert
        assertEquals(duration, type.getCheckEvery());
    }

    @ParameterizedTest
    @ValueSource(strings = {"123_job", "1m1y", "1d1w"})
    void invalidCheckDelay(String duration) {
        // Arrange
        RegistryImageResourceType type = RegistryImageResourceType.create();

        assertThrows(IllegalArgumentException.class, () -> type.setCheckEvery(duration));
    }

    @Test
    void addWorkerTags() {
        // Arrange
        RegistryImageResourceType type = RegistryImageResourceType.create();

        // Act
        type.addTag("worker_aws").addTag("worker_azure");

        // Assert
        assertEquals(2, type.getTags().size());
        assertTrue(type.getTags().containsAll(List.of("worker_aws", "worker_azure")));
    }

    @Test
    void defaultResourceType() {
        // Arrange
        Pipeline pipeline = new Pipeline();

        // TODO: Not sure I like how this works
        RegistryImageResourceType type = RegistryImageResourceType
                .create(RegistryImageConfig.create("concourse/registry-image-resource"))
                .setDefaults(RegistryImageConfig.create(null)
                        .setRegistryMirror(RegistryMirrorConfig.create("https://registry.mirror.example.com"))
                );

        pipeline.addResourceType(type);

        RegistryImageResource resource = RegistryImageResource.create("mirrored-image", RegistryImageConfig.create("busybox"));
        pipeline.addResource(resource);

        // Act
        JsonElement generated = JsonParser.parseString(pipeline.render());

        // Assert
        JsonElement expected = TestUtils.loadFromAssets("resource-type/default.json");

        assertEquals(expected, generated);
    }
}