package org.concourseci.sdk.resource.put;

import com.google.gson.annotations.SerializedName;
import org.concourseci.sdk.resource.Resource;
import org.concourseci.sdk.resource.get.IGetConfig;
import org.concourseci.sdk.step.AbstractStep;
import org.concourseci.sdk.step.IStep;
import org.concourseci.sdk.util.Validator;

public abstract class Put extends AbstractStep<Put> implements IStep {

    @SerializedName("put")
    private final String identifier;

    private String resource;

    private IPutConfig params;

    @SerializedName("get_params")
    private IGetConfig getParams;

    @SerializedName("no_get")
    private Boolean noGet;

    public Put(Resource resource) {
        this.identifier = resource.getName();
    }

    public Put(Resource resource, String name) {
        Validator.validateIdentifier(name);

        this.identifier = name;
        this.resource = resource.getName();
    }

    public Put disableGet() {
        this.noGet = true;

        return this;
    }

    @Override
    protected Put getSelf() {
        return this;
    }
}
