package com.kevinbimonte.concourse.bundled.mock.get;

import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.bundled.mock.MockConfig;
import com.kevinbimonte.concourse.sdk.resource.get.IGetConfig;
import lombok.Getter;

@Getter
public class MockGetConfig implements IGetConfig {

    @SerializedName("mirror_self_via_params")
    private Boolean mirrorSelfViaParams;

//    @SerializedName("create_files_via_params")
//    private Object createFilesViaParams;


    private MockGetConfig() {
    }

    public static MockGetConfig create() {
        return new MockGetConfig();
    }

    /**
     * Same as configuring {@link MockConfig#mirrorSelf()} in source when set to true.
     * <p>
     * When not specified, defaults to false.
     *
     * @return self
     */
    public MockGetConfig mirrorSelf() {
        this.mirrorSelfViaParams = true;

        return this;
    }
}
