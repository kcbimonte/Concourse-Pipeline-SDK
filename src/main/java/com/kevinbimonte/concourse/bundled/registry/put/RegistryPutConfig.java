package com.kevinbimonte.concourse.bundled.registry.put;

import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.bundled.registry.get.RegistryFormat;
import com.kevinbimonte.concourse.sdk.resource.put.IPutConfig;
import com.kevinbimonte.concourse.sdk.step.task.config.Output;
import com.kevinbimonte.concourse.sdk.util.Validator;

public class RegistryPutConfig implements IPutConfig {
    private final String image;

    @SerializedName("bump_aliases")
    private Boolean bumpAliases;

    private String version;

    @SerializedName("additional_tags")
    private String additionalTagsFile;

    private RegistryPutConfig(String image) {
        this.image = image;
    }

    public static RegistryPutConfig create(Output output, RegistryFormat format) {
        if (format.equals(RegistryFormat.OCI))
            return new RegistryPutConfig(String.format("%s/image.tar", output.getName()));
        if (format.equals(RegistryFormat.OCILAYOUT))
            return new RegistryPutConfig(String.format("%s/oci", output.getName()));

        throw new IllegalArgumentException("Only OCI and OCI Layout formats allowed");
    }

    public static RegistryPutConfig create(Output output, RegistryFormat format, String artifactName) {
        if (format.equals(RegistryFormat.ROOTFS)) {
            throw new IllegalArgumentException("Only OCI and OCI Layout formats allowed");
        }

        return new RegistryPutConfig(String.format("%s/%s", output.getName(), artifactName));
    }

    public RegistryPutConfig setVersion(String version) {
        Validator.validateSemver(version);

        this.version = version;

        return this;
    }

    public RegistryPutConfig bumpAliases() {
        if (version == null) throw new RuntimeException("Version must be specified in order to bump aliases");

        this.bumpAliases = true;

        return this;
    }
}
