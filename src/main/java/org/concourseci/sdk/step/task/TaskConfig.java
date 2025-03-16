package org.concourseci.sdk.step.task;

import com.google.gson.annotations.SerializedName;
import org.concourseci.sdk.resource.Resource;


public class TaskConfig {
    private final Platform platform;

    @SerializedName("image_resource")
    private final Resource resource;

    @SerializedName("run")
    private final Command command;

    private TaskConfig(Platform platform, Resource anonymousResource, Command command) {
        this.platform = platform;
        this.resource = anonymousResource;
        this.command = command;
    }

    public static TaskConfig create(Platform platform, Resource resource, Command command) {
        if (!platform.equals(Platform.LINUX))
            resource = null;


        return new TaskConfig(platform, resource, command);
    }
}
