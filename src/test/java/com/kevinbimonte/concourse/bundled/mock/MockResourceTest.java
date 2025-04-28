package com.kevinbimonte.concourse.bundled.mock;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MockResourceTest {

    private Gson gson;

    @BeforeEach
    void setUp() {
        this.gson = new GsonBuilder().create();
    }

    @Test
    void createMockResource() {
        // Arrange
        MockConfig config = MockConfig.create();

        // Act
        MockResource resource = MockResource.createResource("mock", config);

        // Assert
        assertNotNull(resource);
        assertEquals("mock", resource.getName());
    }

    @Test
    void createMockResourceWithMetadata() {
        // Arrange
        MockConfig config = MockConfig.create()
                .addNewMetadataEntry("foo", "bar")
                .addNewMetadataEntry("baz", "qux");

        // Act
        MockResource resource = MockResource.createResource("mock", config);

        // Assert
        assertNotNull(resource);
        assertEquals("mock", resource.getName());

        assertInstanceOf(MockConfig.class, resource.getConfig());

        MockConfig returnedConfig = (MockConfig) resource.getConfig();

        assertEquals(2, returnedConfig.getMetadata().size());

        String expectedOutput = """
                {"name":"mock","type":"mock","source":{"metadata":[{"name":"foo","value":"bar"},{"name":"baz","value":"qux"}]}}
                """;

        JsonElement generated = JsonParser.parseString(gson.toJson(resource));
        JsonElement expected = JsonParser.parseString(expectedOutput);

        assertEquals(expected, generated);
    }
}