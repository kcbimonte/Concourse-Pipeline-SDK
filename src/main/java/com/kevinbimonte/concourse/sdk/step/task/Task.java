package com.kevinbimonte.concourse.sdk.step.task;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.sdk.resource.get.Get;
import com.kevinbimonte.concourse.sdk.step.AbstractAcrossStep;
import com.kevinbimonte.concourse.sdk.step.AcrossVariable;
import com.kevinbimonte.concourse.sdk.step.IStep;
import com.kevinbimonte.concourse.sdk.step.task.config.ContainerLimits;
import com.kevinbimonte.concourse.sdk.step.task.config.Output;
import com.kevinbimonte.concourse.sdk.step.task.config.TaskConfig;
import com.kevinbimonte.concourse.sdk.util.Validator;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Task extends AbstractAcrossStep<Task> implements IStep {
    private final String task;

    private TaskConfig config;
    private String file;
    private String image;

    private Boolean privileged;

    private JsonObject vars;
    private Map<String, String> params;

    @SerializedName("container_limits")
    private ContainerLimits limits;

    @SerializedName("hermetic")
    private Boolean isHermetic;

    @SerializedName("input_mapping")
    private Map<String, String> inputMapping;

    @SerializedName("output_mapping")
    private Map<String, String> outputMapping;

    private Task(String name, TaskConfig config, AcrossVariable variable) {
        if (variable != null) {
            this.addAcrossVariable(variable);
        }

        Validator.validateIdentifier(name, this);

        this.task = name;
        this.config = config;
    }

    private Task(String name, String file, AcrossVariable variable) {
        if (variable != null) {
            this.addAcrossVariable(variable);
        }

        Validator.validateIdentifier(name, this);

        this.task = name;
        this.file = file;
    }

    private Task(String name, TaskConfig config) {
        this.task = name;
        this.config = config;
    }

    private Task(String name, String file) {
        this.task = name;
        this.file = file;
    }

    public static Task create(String name, TaskConfig config) {
        return new Task(name, config, null);
    }

    public static Task createAcrossTask(String name, TaskConfig config, AcrossVariable acrossVariable) {
        return new Task(name, config, acrossVariable);
    }

    public static Task createUsingParent(String name, TaskConfig config, AbstractAcrossStep<? extends IStep> parentStep) {
        if (parentStep.getAcross() == null || parentStep.getAcross().isEmpty()) {
            return Task.create(name, config);
        }

        return new Task(name, config);
    }

    public static Task create(String name, Get get, String path) {
        return Task.createAcrossTask(name, get, path, null);
    }

    public static Task createAcrossTask(String name, Get get, String path, AcrossVariable acrossVariable) {
        if (path == null) {
            throw new RuntimeException("Path cannot be null");
        }

        if (path.startsWith("/")) {
            path = path.trim().substring(1);
        }

        String file = String.format("%s/%s", get.getIdentifier(), path);

        return new Task(name, file, acrossVariable);
    }

    public static Task createUsingParent(String name, Get get, String path, AbstractAcrossStep<? extends IStep> parentStep) {
        if (parentStep.getAcross() == null || parentStep.getAcross().isEmpty()) {
            return Task.create(name, get, path);
        }

        if (path == null) {
            throw new RuntimeException("Path cannot be null");
        }

        if (path.startsWith("/")) {
            path = path.trim().substring(1);
        }

        String file = String.format("%s/%s", get.getIdentifier(), path);

        return new Task(name, file);
    }

    public Task setImage(Get image) {
        this.image = image.getIdentifier();

        return this;
    }

    public Task setImage(Output output) {
        this.image = output.getName();

        return this;
    }

    public Task markPrivileged() {
        this.privileged = true;

        return this;
    }

    public Task addVar(String key, String value) {
        if (vars == null) {
            vars = new JsonObject();
        }

        vars.addProperty(key, value);

        return this;
    }

    public Task addVar(String key, JsonObject value) {
        if (vars == null) {
            vars = new JsonObject();
        }

        vars.add(key, value);

        return this;
    }

    public Task setCPULimit(Integer cpuLimit) {
        if (this.limits == null) {
            this.limits = new ContainerLimits();
        }

        limits.setCPU(cpuLimit);

        return this;
    }

    public Task setMemoryLimit(Integer memoryLimit) {
        if (this.limits == null) {
            this.limits = new ContainerLimits();
        }

        limits.setMemory(memoryLimit);

        return this;
    }

    public Task addParam(String key, String value) {
        if (this.params == null) {
            this.params = new HashMap<>();
        }

        this.params.put(key, value);

        return this;
    }

    /**
     * If set to true, the task will have no outbound network access. Your task will not be able to reach the internet
     * or any local network resources that aren't also inside the container.
     * <p>
     * For macOS and Windows this field has no effect since workloads on those machines are not containerized.
     *
     * @return Self
     * @implNote This setting is only supported by the containerd runtime on Linux. For other runtimes this setting
     * has no effect on container networking. Please contact your Concourse operator to find out what runtime your
     * Concourse cluster is using.
     */
    public Task markHermetic() {
        this.isHermetic = true;

        return this;
    }

    public InputMapping addInputMapping(Get get, String mappedName) {
        if (this.inputMapping == null) {
            this.inputMapping = new HashMap<>();
        }

        this.inputMapping.put(mappedName, get.getIdentifier());

        return new InputMapping(get.getIdentifier(), mappedName);
    }

    public InputMapping addInputMapping(Output output, String mappedName) {
        if (this.inputMapping == null) {
            this.inputMapping = new HashMap<>();
        }

        this.inputMapping.put(output.getName(), mappedName);

        return new InputMapping(output.getName(), mappedName);
    }

    public OutputMapping addOutputMapping(Output output, String mappedName) {
        if (this.outputMapping == null) {
            this.outputMapping = new HashMap<>();
        }

        this.outputMapping.put(output.getName(), mappedName);

        return new OutputMapping(output.getName(), mappedName);
    }

    @Override
    protected Task getSelf() {
        return this;
    }
}
