package org.concourseci.sdk.resource;

import com.google.gson.annotations.SerializedName;

public class AnonymousResource {
    @SerializedName("type")
    private final String resourceType;

    @SerializedName("source")
    private final IResourceConfig config;

    public AnonymousResource(ResourceType type, IResourceConfig config) {
        this.resourceType = type.getName();
        this.config = config;
    }

}
