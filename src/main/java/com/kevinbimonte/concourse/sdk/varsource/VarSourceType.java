package com.kevinbimonte.concourse.sdk.varsource;

import com.google.gson.annotations.SerializedName;

public enum VarSourceType {
    @SerializedName("vault")
    VAULT,
    @SerializedName("ssm")
    SSM,
    @SerializedName("dummy")
    DUMMY;
}
