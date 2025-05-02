package com.kevinbimonte.concourse.bundled.mock.get;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kevinbimonte.concourse.bundled.mock.MockConfig;
import com.kevinbimonte.concourse.bundled.mock.MockResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MockGetTest {

    MockResource resource;
    Gson gson;

    @BeforeEach
    void setUp() {
        resource = MockResource.createResource("mock", MockConfig.create());
        gson = new GsonBuilder().create();
    }

    @Test
    void createMockGet() {
        // Arrange

        // Act
        MockGet get = MockGet.create(resource);

        // Assert
        assertNotNull(get);
        assertEquals("mock", get.getIdentifier());
    }

    @Test
    void createMockGetWithIdentifier() {
        // Arrange

        // Act
        MockGet get = MockGet.create(resource, "new_mock");

        // Assert
        assertNotNull(get);
        assertEquals("new_mock", get.getIdentifier());
        assertEquals("mock", get.getResource());
    }

    @Test
    void configureGet() {
        // Arrange
        MockGet get = MockGet.create(resource);
        MockGetConfig config = MockGetConfig.create().mirrorSelf();

        // Act
        get.setConfig(config);

        // Assert
        assertInstanceOf(MockGetConfig.class, get.getConfig());

        String expectedOutput = """
                {"get":"mock","params":{"mirror_self_via_params":true}}
                """;

        JsonElement generated = JsonParser.parseString(gson.toJson(get));
        JsonElement expected = JsonParser.parseString(expectedOutput);

        assertEquals(expected, generated);
    }

    @Test
    void configureGetWithIdentifier() {
        // Arrange
        MockGet get = MockGet.create(resource, "new_get");
        MockGetConfig config = MockGetConfig.create().mirrorSelf();

        // Act
        get.setConfig(config);

        // Assert
        assertInstanceOf(MockGetConfig.class, get.getConfig());

        String expectedOutput = """
                {"get":"new_get","resource":"mock","params":{"mirror_self_via_params":true}}
                """;

        JsonElement generated = JsonParser.parseString(gson.toJson(get));
        JsonElement expected = JsonParser.parseString(expectedOutput);

        assertEquals(expected, generated);
    }
}