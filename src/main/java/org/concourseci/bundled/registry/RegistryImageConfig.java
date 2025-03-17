package org.concourseci.bundled.registry;

import org.concourseci.sdk.resource.IResourceConfig;

public class RegistryImageConfig implements IResourceConfig {
    private final String repository;

    private String tag = null;

    private RegistryImageConfig(String repository) {
        this.repository = repository;
    }

    public static RegistryImageConfig create(String repository) {
        return new RegistryImageConfig(repository);
    }

    public static RegistryImageConfig create(String repository, String tag) {
        RegistryImageConfig config = new RegistryImageConfig(repository);

        config.tag = tag;

        return config;
    }
}
