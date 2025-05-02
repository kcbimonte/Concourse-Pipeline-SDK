package com.kevinbimonte.concourse.bundled.mock.get;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MockGetConfigTest {
    MockGetConfig config;

    @BeforeEach
    void setUp() {
        config = MockGetConfig.create();
    }

    @Test
    void mirrorSelf() {
        // Arrange

        // Act
        config.mirrorSelf();

        // Assert
        assertTrue(config.getMirrorSelfViaParams());
    }

    @Test
    void createFiles() {
        // Arrange
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
}