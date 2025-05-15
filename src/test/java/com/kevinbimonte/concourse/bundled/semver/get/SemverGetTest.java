package com.kevinbimonte.concourse.bundled.semver.get;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kevinbimonte.concourse.bundled.semver.GitDriverConfig;
import com.kevinbimonte.concourse.bundled.semver.SemverBump;
import com.kevinbimonte.concourse.bundled.semver.SemverResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SemverGetTest {
    SemverResource resource;
    Gson gson;

    @BeforeEach
    void setUp() {
        resource = SemverResource.createResource("semver", GitDriverConfig.create("https://git.web.com/repo.git", "branch", "version"));
        gson = new GsonBuilder().create();
    }

    @Test
    void createGet() {
        // Arrange

        // Act
        SemverGet get = SemverGet.create(resource);

        // Assert
        assertNotNull(get);
        assertEquals("semver", get.getIdentifier());
    }

    @Test
    void createGetWithIdentifier() {
        // Arrange

        // Act
        SemverGet get = SemverGet.create(resource, "new_semver");

        // Assert
        assertNotNull(get);
        assertEquals("new_semver", get.getIdentifier());
        assertEquals("semver", get.getResource());
    }

    @Test
    void configureGet() {
        // Arrange
        SemverGet get = SemverGet.create(resource);
        SemverGetConfig config = SemverGetConfig.create().setBumpType(SemverBump.MAJOR);

        // Act
        get.setConfig(config);

        // Assert
        assertInstanceOf(SemverGetConfig.class, get.getConfig());

        String expectedOutput = """
                {"get":"semver","params":{"bump":"MAJOR"}}
                """;

        JsonElement generated = JsonParser.parseString(gson.toJson(get));
        JsonElement expected = JsonParser.parseString(expectedOutput);

        assertEquals(expected, generated);
    }

    @Test
    void configureGetWithIdentifier() {
        // Arrange
        SemverGet get = SemverGet.create(resource, "new_semver");
        SemverGetConfig config = SemverGetConfig.create().setBumpType(SemverBump.MAJOR);

        // Act
        get.setConfig(config);

        // Assert
        assertInstanceOf(SemverGetConfig.class, get.getConfig());

        String expectedOutput = """
                {"get":"new_semver","resource":"semver","params":{"bump":"MAJOR"}}
                """;

        JsonElement generated = JsonParser.parseString(gson.toJson(get));
        JsonElement expected = JsonParser.parseString(expectedOutput);

        assertEquals(expected, generated);
    }
}