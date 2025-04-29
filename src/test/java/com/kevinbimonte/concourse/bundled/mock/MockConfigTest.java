package com.kevinbimonte.concourse.bundled.mock;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MockConfigTest {

    @Test
    void mirrorSelf() {
        // Arrange
        MockConfig config = MockConfig.create();

        // Act
        config.mirrorSelf();

        // Assert
        assertTrue(config.getMirrorSelf());
    }

    @Test
    void setInitialVersion() {
        // Arrange
        MockConfig config = MockConfig.create();

        JsonObject obj = new JsonObject();
        obj.addProperty("my_version", "123456");

        // Act
        config.setInitialVersion(obj);

        // Assert

        String expectedStr = """
                {
                  "my_version": "123456"
                }
                """;

        JsonElement expected = JsonParser.parseString(expectedStr);

        assertEquals(expected, config.getInitialVersion());
    }

    @Test
    void disableInitialVersion() {
        // Arrange
        MockConfig config = MockConfig.create();

        // Act
        config.disableInitialVersion();

        // Assert
        assertTrue(config.getNoInitialVersion());
    }

    @Test
    void cannotSetInitialVersionWhenDisabled() {
        // Arrange
        MockConfig config = MockConfig.create();

        JsonObject obj = new JsonObject();
        obj.addProperty("my_version", "123456");

        config.setInitialVersion(obj);

        // Act
        assertThrows(IllegalArgumentException.class, config::disableInitialVersion);
    }

    @Test
    void cannotDisableWhenSetInitialVersion() {
        // Arrange
        MockConfig config = MockConfig.create();

        JsonObject obj = new JsonObject();
        obj.addProperty("my_version", "123456");

        config.disableInitialVersion();

        // Act
        assertThrows(IllegalArgumentException.class, () -> config.setInitialVersion(obj));
    }

    @Test
    void forceInitialVersion() {
        // Arrange
        MockConfig config = MockConfig.create();

        // Act
        config.forceVersion();

        // Assert
        assertTrue(config.getForceVersion());
    }
}