package com.kevinbimonte.concourse.bundled.registry.get;

import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.sdk.resource.get.IGetConfig;
import lombok.Getter;

@Getter
public class RegistryGetConfig implements IGetConfig {
    private RegistryFormat format;

    @SerializedName("skip_download")
    private Boolean skipDownload;

    private RegistryGetConfig() {
    }

    public static RegistryGetConfig create() {
        return new RegistryGetConfig();
    }

    public RegistryGetConfig skipDownload() {
        this.skipDownload = true;

        return this;
    }

    public RegistryGetConfig setFormat(RegistryFormat format) {
        this.format = format;

        return this;
    }
}
