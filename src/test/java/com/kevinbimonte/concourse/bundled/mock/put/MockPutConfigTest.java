package com.kevinbimonte.concourse.bundled.mock.put;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MockPutConfigTest {
    MockPutConfig config;

    @BeforeEach
    void setUp() {
        config = MockPutConfig.create();
    }

    @Test
    void togglePrintEnv() {
        // Arrange

        // Act
        this.config.togglePrintEnv();

        // Assert
        assertTrue(this.config.getPrintEnv());
    }

    @Test
    void setInitialVersion() {
        // Arrange
        JsonObject obj = new JsonObject();
        obj.addProperty("my_version", "123456");

        // Act
        config.setVersion(obj);

        // Assert

        String expectedStr = """
                {
                  "my_version": "123456"
                }
                """;

        JsonElement expected = JsonParser.parseString(expectedStr);

        assertEquals(expected, config.getVersion());
    }

    @Test
    void setFileForVersion() {
        // Arrange

        // Act
        this.config.setFileForVersion("my_file.yml");

        // Assert
        assertEquals("my_file.yml", this.config.getFile());
    }

    @Test
    void cannotSpecifyVersionWhenFileSet() {
        // Arrange
        this.config.setFileForVersion("my_file.yml");

        JsonObject obj = new JsonObject();
        obj.addProperty("my_version", "123456");

        // Assert
        assertThrows(UnsupportedOperationException.class, () -> this.config.setVersion(obj));
    }

    @Test
    void cannotSpecifyFileWhenVersionSet() {
        // Arrange
        JsonObject obj = new JsonObject();
        obj.addProperty("my_version", "123456");
        this.config.setVersion(obj);

        // Assert
        assertThrows(UnsupportedOperationException.class, () -> this.config.setFileForVersion("my_file.yml"));
    }
}