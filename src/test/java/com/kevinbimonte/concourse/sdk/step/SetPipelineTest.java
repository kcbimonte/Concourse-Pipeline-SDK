package com.kevinbimonte.concourse.sdk.step;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kevinbimonte.concourse.sdk.Pipeline;
import com.kevinbimonte.concourse.sdk.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SetPipelineTest {

    @Test
    void setPipelineAcross() {
        // Arrange
        Pipeline pipeline = new Pipeline();

        // Act

        // Assert
        JsonElement generated = JsonParser.parseString(pipeline.render());
        JsonElement expected = TestUtils.loadFromAssets("across/set_pipeline_across.json");

        assertEquals(expected, generated);
    }
}