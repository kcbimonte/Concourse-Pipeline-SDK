package org.concourseci.sdk.step.task;

import com.google.gson.annotations.SerializedName;
import org.concourseci.sdk.resource.Resource;

import java.util.HashSet;
import java.util.Set;


public class TaskConfig {
    private final Platform platform;

    @SerializedName("image_resource")
    private Resource resource;

    @SerializedName("run")
    private final Command command;


    private final Set<Input> inputs = new HashSet<>();
    private final Set<Output> outputs = new HashSet<>();

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

    public static TaskConfig create(Platform platform, Command command) {
        return new TaskConfig(platform, null, command);
    }

    public TaskConfig addInput(Input input) {
        this.inputs.add(input);

        return this;
    }

    public TaskConfig addOutput(Output output) {
        this.outputs.add(output);

        return this;
    }
}
