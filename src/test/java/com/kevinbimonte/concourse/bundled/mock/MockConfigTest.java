package com.kevinbimonte.concourse.bundled.mock;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

    @Test
    void createFiles() {
        // Arrange
        MockConfig config = MockConfig.create();
        String fileOne = """
                foo: bar
                """;
        String fileTwo = """
                #!/bin/sh
                echo "hello world"
                """;

        // Act
        config.createNewFile("file1.yml", fileOne).createNewFile("file2.sh", fileTwo);

        // Assert
        assertEquals(2, config.getCreateFiles().size());
        assertTrue(config.getCreateFiles().containsKey("file2.sh"));
    }

    @Test
    void recreateExistingFileFails() {
        // Arrange
        MockConfig config = MockConfig.create();
        String fileOne = """
                foo: bar
                """;
        String fileTwo = """
                #!/bin/sh
                echo "hello world"
                """;

        config.createNewFile("file1.yml", fileOne);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> config.createNewFile("file1.yml", fileTwo));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1ms", "1s", "1m", "1h", "1d", "1w", "1y", "1y1m100s"})
    void validCheckDelays(String duration) {
        // Arrange
        MockConfig config;

        // Act
        config = assertDoesNotThrow(() -> MockConfig.create().setCheckDelay(duration));

        // Assert
        assertEquals(duration, config.getCheckDelay());
    }

    @ParameterizedTest
    @ValueSource(strings = {"123_job", "1m1y", "1d1w"})
    void invalidCheckDelay(String duration) {
        // Arrange
        MockConfig config = MockConfig.create();

        assertThrows(IllegalArgumentException.class, () -> config.setCheckDelay(duration));
    }

    @Test
    void setCheckFailureMessage() {
        // Arrange
        MockConfig config = MockConfig.create();

        // Act
        config.setCheckFailureMessage("This is a failure message");

        // Assert
        assertEquals("This is a failure message", config.getCheckFailure());
    }

    @Test
    void addMetadata() {
        // Arrange
        MockConfig config = MockConfig.create();

        // Act
        config.addNewMetadataEntry("foo", "bar").addNewMetadataEntry("baz", "qux");

        // Assert
        assertEquals(2, config.getMetadata().size());
        assertTrue(config.getMetadata().containsKey("foo"));
    }

    @Test
    void addExistingMetadataFails() {
        // Arrange
        MockConfig config = MockConfig.create();

        config.addNewMetadataEntry("foo", "bar");

        // Act
        assertThrows(IllegalArgumentException.class, () -> config.addNewMetadataEntry("foo", "qux"));
    }

    @Test
    void setLogMessage() {
        // Arrange
        MockConfig config = MockConfig.create();

        // Act
        config.setLogMessage("This is a log message");

        // Assert
        assertEquals("This is a log message", config.getLog());
    }

}