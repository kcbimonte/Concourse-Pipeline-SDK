package com.kevinbimonte.concourse.bundled.mock.get;

import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.bundled.mock.MockConfig;
import com.kevinbimonte.concourse.sdk.resource.get.IGetConfig;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class MockGetConfig implements IGetConfig {

    @SerializedName("mirror_self_via_params")
    private Boolean mirrorSelfViaParams;

    @SerializedName("create_files_via_params")
    private HashMap<String, String> createFiles;

    private MockGetConfig() {
    }

    public static MockGetConfig create() {
        return new MockGetConfig();
    }

    /**
     * Same as configuring {@link MockConfig#mirrorSelf()} in source when set to true.
     * <p>
     * When not specified, defaults to false.
     *
     * @return self
     */
    public MockGetConfig mirrorSelf() {
        this.mirrorSelfViaParams = true;

        return this;
    }

    /**
     * Creates a new file to be consumed by the mock resource.
     *
     * @param fileName The name of the file (e.g. file1.yml)
     * @param content  The content of the file. Should be a multi-line string.
     * @return self
     */
    public MockGetConfig createNewFile(String fileName, String content) {
        if (this.createFiles == null) {
            this.createFiles = new HashMap<>();
        }

        if (this.createFiles.containsKey(fileName)) {
            throw new IllegalArgumentException("Filename already exists in the map");
        }

        this.createFiles.put(fileName, content);

        return this;
    }
}
