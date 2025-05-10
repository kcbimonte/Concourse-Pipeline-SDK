package com.kevinbimonte.concourse.bundled.semver;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class GCSDriverConfig extends AbstractSemverDriverConfig<GCSDriverConfig> {

    private final String bucket;

    private final String key;

    @SerializedName("json_key")
    private final String jsonKey;

    private GCSDriverConfig(String bucket, String key, String jsonKey) {
        super(SemverDriver.GCS);

        this.bucket = bucket;
        this.key = key;
        this.jsonKey = jsonKey;
    }

    /**
     * Create a GCS Driver Config given the required values.
     *
     * @param bucket The name of the bucket
     * @param key The key to use for the object in the bucket tracking the version
     * @param jsonKey Either, the Concourse variable containing the contents of the GCP Account JSON Key or
     *                the serialized version of the JSON Key
     * @return A new GCS Driver Config
     */
    public static GCSDriverConfig create(String bucket, String key, String jsonKey) {
        return new GCSDriverConfig(bucket, key, jsonKey);
    }

    /**
     * Create a GCS Driver Config given the required values.
     *
     * @param bucket The name of the bucket
     * @param key The key to use for the object in the bucket tracking the version
     * @param jsonKey The JSON Object containing the GCP Account JSON Key
     * @return A new GCS Driver Config
     */
    public static GCSDriverConfig create(String bucket, String key, JsonElement jsonKey) {
        String serializedKey = jsonKey.getAsString();

        return new GCSDriverConfig(bucket, key, serializedKey);
    }

    @Override
    protected GCSDriverConfig getSelf() {
        return this;
    }
}
