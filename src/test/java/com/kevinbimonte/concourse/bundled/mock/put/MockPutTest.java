package com.kevinbimonte.concourse.bundled.mock.put;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kevinbimonte.concourse.bundled.mock.MockConfig;
import com.kevinbimonte.concourse.bundled.mock.MockResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MockPutTest {
    MockResource resource;
    Gson gson;

    @BeforeEach
    void setUp() {
        resource = MockResource.createResource("mock", MockConfig.create());
        gson = new GsonBuilder().create();
    }

    @Test
    void createMockPut() {
        // Arrange

        // Act
        MockPut put = MockPut.create(resource);

        // Assert
        assertNotNull(put);
        assertEquals("mock", put.getIdentifier());
    }

    @Test
    void createMockPutWithIdentifier() {
        // Arrange

        // Act
        MockPut put = MockPut.create(resource, "new_mock");

        // Assert
        assertNotNull(put);
        assertEquals("new_mock", put.getIdentifier());
        assertEquals("mock", put.getResource());
    }

    @Test
    void configurePut() {
        // Arrange
        MockPut put = MockPut.create(resource);
        MockPutConfig config = MockPutConfig.create().setFileForVersion("my_file.yml");

        // Act
        put.setParams(config);

        // Assert
        assertInstanceOf(MockPutConfig.class, put.getParams());

        String expectedOutput = """
                {"put":"mock","params":{"file":"my_file.yml"}}
                """;

        JsonElement generated = JsonParser.parseString(gson.toJson(put));
        JsonElement expected = JsonParser.parseString(expectedOutput);

        assertEquals(expected, generated);
    }

    @Test
    void configurePutWithIdentifier() {
        // Arrange
        MockPut put = MockPut.create(resource, "new_put");
        MockPutConfig config = MockPutConfig.create().setFileForVersion("my_file.yml");

        // Act
        put.setParams(config);

        // Assert
        assertInstanceOf(MockPutConfig.class, put.getParams());

        String expectedOutput = """
                {"put":"new_put","resource":"mock","params":{"file":"my_file.yml"}}
                """;

        JsonElement generated = JsonParser.parseString(gson.toJson(put));
        JsonElement expected = JsonParser.parseString(expectedOutput);

        assertEquals(expected, generated);
    }
}