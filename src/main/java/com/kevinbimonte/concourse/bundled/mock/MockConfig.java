package com.kevinbimonte.concourse.bundled.mock;

import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.sdk.resource.AnonymousResource;
import com.kevinbimonte.concourse.sdk.resource.IResourceConfig;
import com.kevinbimonte.concourse.sdk.resource.IVersion;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;
import com.kevinbimonte.concourse.sdk.util.Validator;
import lombok.Getter;

@Getter
public class MockConfig implements IResourceConfig {

    @SerializedName("mirror_self")
    private Boolean mirrorSelf;

    @SerializedName("initial_version")
    private IVersion initialVersion;

    @SerializedName("no_initial_version")
    private Boolean noInitialVersion;

    @SerializedName("force_version")
    private Boolean forceVersion;

    @SerializedName("check_delay")
    private String checkDelay;

    @SerializedName("check_failure")
    private String checkFailure;

    private String log;

    private MockConfig() {
    }

    public static MockConfig create() {
        return new MockConfig();
    }

    /**
     * For use on {@link AnonymousResource#create(ResourceType, IResourceConfig)}, returns
     * itself as an image that tasks can run on.
     * <p>
     * -- ex: creating an AnonymousResource with mirror_self
     * <pre>
     * {@code
     * AnonymousResource.create(MockResourceType.getInstance(), MockConfig.create().mirrorSelf());
     * }
     * </pre>
     * Generates
     * <pre>
     * {@code
     * type: mock
     * source:
     *   mirror_self: true
     * }
     * </pre>
     *
     * @return self
     */
    public MockConfig mirrorSelf() {
        this.mirrorSelf = true;

        return this;
    }

    /**
     * Initial Version to emit. If not set, will default to {@code mock}
     *
     * @param version The initial version to emit
     * @return self
     */
    public MockConfig setInitialVersion(IVersion version) {
        this.initialVersion = version;

        return this;
    }

    /**
     * Disables the initial version. Useful for testing resource triggers.
     *
     * @return self
     */
    public MockConfig disableInitialVersion() {
        this.noInitialVersion = true;

        return this;
    }

    /**
     * Always emit a version. Overwrites any param set during the check set using fly check-resource.
     *
     * @return self
     */
    public MockConfig forceVersion() {
        this.forceVersion = true;

        return this;
    }

    /**
     * Sets the check delay.
     *
     * @implNote Check Delay must conform to {@link Validator#validateDuration(String)}
     * @param checkDelay A valid check delay
     * @return self
     */
    public MockConfig setCheckDelay(String checkDelay) {
        Validator.validateDuration(checkDelay);

        this.checkDelay = checkDelay;

        return this;
    }

    /**
     * Sets the check failure message. Every Check will return this error message.
     *
     * @param message Failure Message
     * @return self
     */
    public MockConfig setCheckFailureMessage(String message) {
        this.checkFailure = message;

        return this;
    }

    /**
     * Sets the log message. Prints the message on every action.
     *
     * @param message Log Message
     * @return self
     */
    public MockConfig setLogMessage(String message) {
        this.log = message;

        return this;
    }
}
