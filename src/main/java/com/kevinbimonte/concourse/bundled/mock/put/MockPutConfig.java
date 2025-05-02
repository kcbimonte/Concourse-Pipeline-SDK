package com.kevinbimonte.concourse.bundled.mock.put;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.sdk.resource.put.IPutConfig;
import lombok.Getter;

@Getter
public class MockPutConfig implements IPutConfig {

    private JsonElement version;

    @SerializedName("print_env")
    private Boolean printEnv;

    private String file;

    private MockPutConfig() {
    }

    public static MockPutConfig create() {
        return new MockPutConfig();
    }

    /**
     * On put, create the specified version
     *
     * @param version The version to create
     * @return self
     */
    public MockPutConfig setVersion(JsonElement version) {
        if (file != null) {
            throw new UnsupportedOperationException("Cannot specify both version and file for version");
        }

        this.version = version;

        return this;
    }

    public MockPutConfig togglePrintEnv() {
        this.printEnv = true;

        return this;
    }

    public MockPutConfig setFileForVersion(String file) {
        if (version != null) {
            throw new UnsupportedOperationException("Cannot specify both version and file for version");
        }

        this.file = file;

        return this;
    }
}
