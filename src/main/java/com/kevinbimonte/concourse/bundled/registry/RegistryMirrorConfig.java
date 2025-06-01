package com.kevinbimonte.concourse.bundled.registry;

public class RegistryMirrorConfig {
    private final String host;

    private String username;
    private String password;

    private RegistryMirrorConfig(String host) {
        this.host = host;
    }

    public static RegistryMirrorConfig create(String host) {
        return new RegistryMirrorConfig(host);
    }

    public void setAuthentication(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
