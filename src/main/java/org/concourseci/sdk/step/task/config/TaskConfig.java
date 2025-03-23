package org.concourseci.sdk.step.task.config;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import org.concourseci.sdk.resource.AnonymousResource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class TaskConfig {
    private final Platform platform;

    @SerializedName("image_resource")
    private AnonymousResource resource;

    @SerializedName("run")
    private final Command command;

    private final Set<Input> inputs = new HashSet<>();
    private final Set<Output> outputs = new HashSet<>();

    private Set<TaskCache> caches;

    @SerializedName("container_limits")
    private ContainerLimits limits;

    private Map<String, String> params;

    private TaskConfig(Platform platform, AnonymousResource anonymousResource, Command command) {
        this.platform = platform;
        this.resource = anonymousResource;
        this.command = command;
    }

    public static TaskConfig create(Platform platform, AnonymousResource resource, Command command) {
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

    public TaskConfig addParam(String key, String value) {
        if (params == null) params = new HashMap<>();

        params.put(key, value);

        return this;
    }

    public TaskConfig addCache(String cache) {
        if (this.caches == null) {
            this.caches = new HashSet<>();
        }

        this.caches.add(new TaskCache(cache));

        return this;
    }

    public TaskConfig setCPULimit(Integer cpuLimit) {
        if (this.limits == null) {
            this.limits = new ContainerLimits();
        }

        limits.setCPU(cpuLimit);

        return this;
    }

    public TaskConfig setMemoryLimit(Integer memoryLimit) {
        if (this.limits == null) {
            this.limits = new ContainerLimits();
        }

        limits.setMemory(memoryLimit);

        return this;
    }
}
