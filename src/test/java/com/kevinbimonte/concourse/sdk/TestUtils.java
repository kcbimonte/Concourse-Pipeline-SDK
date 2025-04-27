package com.kevinbimonte.concourse.sdk;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestUtils {
    public static JsonElement loadFromAssets(String filename) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(String.format("assets/examples/%s", filename));

        JsonElement expected;

        try {
            assert is != null;
            try (InputStreamReader reader = new InputStreamReader(is)) {
                expected = new Gson().fromJson(reader, JsonElement.class);

                return expected;
            }
        } catch (IOException ignored) {
            throw new RuntimeException("File not found");
        }
    }
}
