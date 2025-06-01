package com.kevinbimonte.concourse.bundled.mock;

import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.sdk.resource.AnonymousResource;
import com.kevinbimonte.concourse.sdk.resource.IResourceConfig;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;
import com.kevinbimonte.concourse.sdk.util.Validator;
import lombok.Getter;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Getter
public class MockConfig implements IResourceConfig {

    @SerializedName("mirror_self")
    private Boolean mirrorSelf;

    @SerializedName("initial_version")
    private JsonElement initialVersion;

    @SerializedName("no_initial_version")
    private Boolean noInitialVersion;

    @SerializedName("force_version")
    private Boolean forceVersion;

    @SerializedName("create_files")
    private HashMap<String, String> createFiles;

    @SerializedName("check_delay")
    private String checkDelay;

    @SerializedName("check_failure")
    private String checkFailure;

    @JsonAdapter(MetadataSerializer.class)
    @SerializedName("metadata")
    private HashMap<String, String> metadata;

    private String log;

    private MockConfig() {
    }

    public static MockConfig create() {
        return new MockConfig();
    }

    /**
     * For use on {@link AnonymousResource#create(ResourceType, IResourceConfig)}, returns
     * itself as an image that tasks can run on.
     * <p>
     * -- ex: creating an AnonymousResource with mirror_self
     * <pre>
     * {@code
     * AnonymousResource.create(MockResourceType.getInstance(), MockConfig.create().mirrorSelf());
     * }
     * </pre>
     * Generates
     * <pre>
     * {@code
     * type: mock
     * source:
     *   mirror_self: true
     * }
     * </pre>
     *
     * @return self
     */
    public MockConfig mirrorSelf() {
        this.mirrorSelf = true;

        return this;
    }

    /**
     * Initial Version to emit. If not set, will default to {@code mock}
     *
     * @param version The initial version to emit
     * @return self
     */
    public MockConfig setInitialVersion(JsonElement version) {
        if (this.noInitialVersion != null && this.noInitialVersion) {
            throw new IllegalArgumentException("Cannot set version when Initial Version is Disabled");
        }

        this.initialVersion = version;

        return this;
    }

    /**
     * Disables the initial version. Useful for testing resource triggers.
     *
     * @return self
     */
    public MockConfig disableInitialVersion() {
        if (this.initialVersion != null) {
            throw new IllegalArgumentException("Cannot disable Initial Version when Initial Version is set");
        }

        this.noInitialVersion = true;

        return this;
    }

    /**
     * Always emit a version. Overwrites any param set during the check set using fly check-resource.
     *
     * @return self
     */
    public MockConfig forceVersion() {
        this.forceVersion = true;

        return this;
    }

    /**
     * Creates a new file to be consumed by the mock resource.
     *
     * @param fileName The name of the file (e.g. file1.yml)
     * @param content  The content of the file. Should be a multi-line string.
     * @return self
     */
    public MockConfig createNewFile(String fileName, String content) {
        if (this.createFiles == null) {
            this.createFiles = new HashMap<>();
        }

        if (this.createFiles.containsKey(fileName)) {
            throw new IllegalArgumentException("Filename already exists in the map");
        }

        this.createFiles.put(fileName, content.trim());

        return this;
    }

    /**
     * Sets the check delay.
     *
     * @param checkDelay A valid check delay
     * @return self
     * @implNote Check Delay must conform to {@link Validator#validateDuration(String)}
     */
    public MockConfig setCheckDelay(String checkDelay) {
        Validator.validateDuration(checkDelay);

        this.checkDelay = checkDelay;

        return this;
    }

    /**
     * Sets the check failure message. Every Check will return this error message.
     *
     * @param message Failure Message
     * @return self
     */
    public MockConfig setCheckFailureMessage(String message) {
        this.checkFailure = message;

        return this;
    }

    /**
     * Adds an entry to the list of metadata to be returned on every get and put.
     *
     * @param name  Name
     * @param value Value
     * @return self
     */
    public MockConfig addNewMetadataEntry(String name, String value) {
        if (this.metadata == null) {
            this.metadata = new HashMap<>();
        }

        if (this.metadata.containsKey(name)) {
            throw new IllegalArgumentException("Metadata name already exists in the map");
        }

        metadata.put(name, value);

        return this;
    }


    /**
     * Sets the log message. Prints the message on every action.
     *
     * @param message Log Message
     * @return self
     */
    public MockConfig setLogMessage(String message) {
        this.log = message;

        return this;
    }

    private static class MetadataSerializer implements JsonSerializer<Map<String, String>> {

        @Override
        public JsonElement serialize(Map<String, String> src, Type typeOfSrc, JsonSerializationContext context) {
            JsonArray arr = new JsonArray();

            for (Map.Entry<String, String> entry : src.entrySet()) {
                JsonObject obj = new JsonObject();

                obj.addProperty("name", entry.getKey());
                obj.addProperty("value", entry.getValue());

                arr.add(obj);
            }

            return arr;
        }
    }
}
