package com.kevinbimonte.concourse.bundled.semver;

import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.sdk.resource.IResourceConfig;

import java.net.URI;

public class SemVerConfig implements IResourceConfig {

    @SerializedName("initial_version")
    private Object initialVersion;

    private SemVerDriver driver;

    /**
     * Bucket can either map to GCS or S3
     */
    @SerializedName("bucket")
    private String bucket;

    /**
     * Key can either map to GCS or S3
     */
    private String key;

    private SemVerConfig(SemVerDriver driver) {
        this.driver = driver;
    }

    public static SemVerConfig createS3Config(String bucket, String key) {
        SemVerConfig config = new SemVerConfig(SemVerDriver.S3);

        config.bucket = bucket;
        config.key = key;

        return config;
    }

    public static SemVerConfig createGitConfig(String uri, String branch, String file) {
        URI gitUri = URI.create(uri);

        SemVerConfig config = new SemVerConfig(SemVerDriver.GIT);

        return config;
    }

    public static SemVerConfig createSwiftConfig(String container, String itemName, String region) {
        SemVerConfig config = new SemVerConfig(SemVerDriver.SWIFT);

        return config;
    }

    public static SemVerConfig createGCSConfig(String bucket, String key, String jsonKey) {
        SemVerConfig config = new SemVerConfig(SemVerDriver.GCS);

        config.bucket = bucket;
        config.key = key;

        return config;
    }
}
