package org.concourseci.bundled.registry;

import com.google.gson.annotations.SerializedName;
import org.concourseci.sdk.resource.IResourceConfig;

public class RegistryImageConfig implements IResourceConfig {
    private final String repository;

    private final String tag;

    @SerializedName("username")
    private String usernameVariable;

    @SerializedName("password")
    private String passwordVariable;

    public RegistryImageConfig(String repository, String tag) {
        this.repository = repository;
        this.tag = tag;
    }

    public static RegistryImageConfig create(String repository) {
        return RegistryImageConfig.create(repository, null);
    }

    public static RegistryImageConfig create(String repository, String tag) {
        return new RegistryImageConfig(repository, tag);
    }

    public RegistryImageConfig setCredentials(String usernameVariable, String passwordVariable) {
        this.usernameVariable = usernameVariable;
        this.passwordVariable = passwordVariable;

        return this;
    }
}
