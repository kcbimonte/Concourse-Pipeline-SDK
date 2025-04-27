package com.kevinbimonte.concourse.sdk.step;

import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.sdk.util.Validator;

import java.util.HashSet;
import java.util.Set;

public class AcrossVariable {
    @SerializedName("var")
    private final String name;

    private final Set<Object> values = new HashSet<>();

    @SerializedName("max_in_flight")
    private JsonPrimitive maxInFlight;

    private AcrossVariable(String name) {
        Validator.validateIdentifier(name);

        this.name = name;
    }

    public static AcrossVariable create(String name) {
        return new AcrossVariable(name);
    }

    public String getVariable() {
        return String.format("((.:%s))", this.name);
    }
}
