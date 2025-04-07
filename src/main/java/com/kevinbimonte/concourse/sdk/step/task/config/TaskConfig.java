package com.kevinbimonte.concourse.sdk.step.task.config;

import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.sdk.resource.AnonymousResource;
import com.kevinbimonte.concourse.sdk.resource.IResourceConfig;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class TaskConfig {
    private final Platform platform;

    @SerializedName("image_resource")
    private final AnonymousResource<?> resource;

    private Set<Input> inputs;
    private Set<Output> outputs;

    private Set<TaskCache> caches;

    private Map<String, String> params;

    @SerializedName("run")
    private final Command command;

    @SerializedName("rootfs_uri")
    private String rootfsURI;

    @SerializedName("container_limits")
    private ContainerLimits limits;

    private <T extends IResourceConfig> TaskConfig(Platform platform, AnonymousResource<T> anonymousResource, Command command) {
        this.platform = platform;
        this.resource = anonymousResource;
        this.command = command;
    }

    public static <T extends IResourceConfig> TaskConfig create(Platform platform, AnonymousResource<T> resource, Command command) {
        if (!platform.equals(Platform.LINUX)) {
            resource = null;
        }

        return new TaskConfig(platform, resource, command);
    }

    public static TaskConfig create(Platform platform, Command command) {
        return new TaskConfig(platform, null, command);
    }

    public TaskConfig addInput(Input input) {
        if (this.inputs == null) {
            this.inputs = new HashSet<>();
        }

        this.inputs.add(input);

        return this;
    }

    public TaskConfig addOutput(Output output) {
        if (this.outputs == null) {
            this.outputs = new HashSet<>();
        }

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

    /**
     * Specify the rootfs uri of the container, as interpreted by your worker's Garden backend
     *
     * @param uri A string specifying the rootfs uri of the container, as interpreted by your worker's Garden backend.
     * @return Self
     * @implNote {@link TaskConfig#resource} is the preferred way to specify base image. You should only use this
     * if you have no other option, and you really know what you're doing.
     */
    public TaskConfig setRootFSUri(String uri) {
        this.rootfsURI = uri;

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
