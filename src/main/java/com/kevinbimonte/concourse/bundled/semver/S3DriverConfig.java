package com.kevinbimonte.concourse.bundled.semver;

import com.google.gson.annotations.SerializedName;

public class S3DriverConfig extends AbstractSemverDriverConfig<S3DriverConfig> {

    private final String bucket;

    private final String key;

    @SerializedName("access_key_id")
    private String accessKeyId;

    @SerializedName("secret_access_key")
    private String secretAccessKey;

    private S3DriverConfig(String bucket, String key) {
        super(SemverDriver.S3);

        this.bucket = bucket;
        this.key = key;
    }

    /**
     * Create a S3 Driver Config using the bucket and file name.
     *
     * @param bucket The name of the bucket
     * @param key    The key to use for the object in the bucket tracking the version
     * @return S3DriverConfig
     */
    private static S3DriverConfig create(String bucket, String key) {
        return new S3DriverConfig(bucket, key);
    }

    /**
     * Set basic credentials using Access Key and Secret Access Key.
     * <p>
     * Note: Should use {@link com.kevinbimonte.concourse.sdk.variable.Variable#referenceVariable(String...)} to
     * create the variable
     *
     * @param accessKeyId     The AWS access key to use when accessing the bucket
     * @param secretAccessKey The AWS secret key to use when accessing the bucket
     * @return self
     */
    public S3DriverConfig setBasicCredentials(String accessKeyId, String secretAccessKey) {
        this.accessKeyId = accessKeyId;
        this.secretAccessKey = secretAccessKey;

        return this;
    }

    @Override
    protected S3DriverConfig getSelf() {
        return this;
    }
}
