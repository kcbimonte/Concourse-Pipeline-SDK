package com.kevinbimonte.concourse.sdk.step;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.sdk.util.Validator;

import java.util.HashSet;
import java.util.Set;

public class AcrossVariable {
    @SerializedName("var")
    private final String name;

    private final Set<JsonElement> values = new HashSet<>();

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

    public void setMaxInFlight(Integer maxInFlight) {
        if (maxInFlight < 1) {
            throw new IllegalArgumentException("Max in Flight cannot be less than 1");
        }

        this.maxInFlight = new JsonPrimitive(maxInFlight);
    }

    public void runAll() {
        this.maxInFlight = new JsonPrimitive("all");
    }

    public AcrossVariable addValue(Integer value) {
        this.values.add(new JsonPrimitive(value));

        return this;
    }

    public AcrossVariable addValue(String value) {
        this.values.add(new JsonPrimitive(value));

        return this;
    }

    public AcrossVariable addValue(Boolean value) {
        this.values.add(new JsonPrimitive(value));

        return this;
    }
}
