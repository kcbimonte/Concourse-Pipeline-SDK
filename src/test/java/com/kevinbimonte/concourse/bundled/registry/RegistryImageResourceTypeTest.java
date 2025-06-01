package com.kevinbimonte.concourse.bundled.registry;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kevinbimonte.concourse.sdk.Pipeline;
import com.kevinbimonte.concourse.sdk.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistryImageResourceTypeTest {

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