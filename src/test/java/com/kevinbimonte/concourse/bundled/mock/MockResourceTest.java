package com.kevinbimonte.concourse.bundled.mock;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kevinbimonte.concourse.bundled.mock.get.MockGet;
import com.kevinbimonte.concourse.bundled.mock.put.MockPut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
    void createStandardMockGet() {
        // Arrange
        MockConfig config = MockConfig.create();
        MockResource resource = MockResource.createResource("mock", config);

        // Act
        MockGet get = resource.createGetDefinition();


        // Assert
        assertNotNull(get);
        assertEquals("mock", get.getIdentifier());
    }

    @Test
    void changeMockGetIdentifier() {
        // Arrange
        MockConfig config = MockConfig.create();
        MockResource resource = MockResource.createResource("mock", config);

        // Act
        MockGet get = resource.createGetDefinition("identifier");


        // Assert
        assertNotNull(get);
        assertEquals("identifier", get.getIdentifier());
        assertEquals("mock", get.getResource());
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "((my_var))-get", "MY_GET"})
    void setInvalidMockGetIdentifier(String identifier) {
        // Arrange
        MockConfig config = MockConfig.create();
        MockResource resource = MockResource.createResource("mock", config);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> resource.createGetDefinition(identifier));
    }

    @Test
    void createStandardMockPut() {
        // Arrange
        MockConfig config = MockConfig.create();
        MockResource resource = MockResource.createResource("mock", config);

        // Act
        MockPut put = resource.createPutDefinition();


        // Assert
        assertNotNull(put);
        assertEquals("mock", put.getIdentifier());
    }

    @Test
    void changeMockPutIdentifier() {
        // Arrange
        MockConfig config = MockConfig.create();
        MockResource resource = MockResource.createResource("mock", config);

        // Act
        MockPut put = resource.createPutDefinition("identifier");


        // Assert
        assertNotNull(put);
        assertEquals("identifier", put.getIdentifier());
        assertEquals("mock", put.getResource());
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "((my_var))-get", "MY_GET"})
    void setInvalidMockPutIdentifier(String identifier) {
        // Arrange
        MockConfig config = MockConfig.create();
        MockResource resource = MockResource.createResource("mock", config);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> resource.createPutDefinition(identifier));
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