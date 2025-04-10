package com.kevinbimonte.concourse.sdk;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(AccessLevel.PACKAGE)
@Getter
class DisplayConfig {
    @SerializedName("background_image")
    private String backgroundImage;

    @SerializedName("background_filter")
    private String backgroundFilter;

}
