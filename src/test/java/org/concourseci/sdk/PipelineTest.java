package org.concourseci.sdk;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.concourseci.bundled.git.GitConfig;
import org.concourseci.bundled.git.GitResource;
import org.concourseci.bundled.registry.RegistryImageConfig;
import org.concourseci.bundled.registry.RegistryImageResourceType;
import org.concourseci.sdk.resource.AnonymousResource;
import org.concourseci.sdk.resource.Get;
import org.concourseci.sdk.resource.Resource;
import org.concourseci.sdk.step.task.Command;
import org.concourseci.sdk.step.task.Platform;
import org.concourseci.sdk.step.task.Task;
import org.concourseci.sdk.step.task.TaskConfig;
import org.concourseci.sdk.variable.Variable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PipelineTest {

    Gson gson;

    @BeforeEach
    void setUp() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Test
    void helloWorld() {
        // Define Pipeline
        Pipeline pipeline = new Pipeline();

        Job job = new Job("job").markPublic();

        Command command = Command.createCommand("echo");
        command.addArg("Hello world!");

        AnonymousResource anonResource = new AnonymousResource(RegistryImageResourceType.getInstance(), RegistryImageConfig.create("busybox"));

        TaskConfig config = TaskConfig.create(Platform.LINUX, anonResource, command);

        Task task = new Task("simple-task", config);

        job.addStep(task);

        pipeline.addJob(job);

        System.out.println(gson.toJson(pipeline));
    }

    @Test
    void serialJob() {
        // Define Pipeline
        Pipeline pipeline = new Pipeline();

        Job job = new Job("serial-job").markPublic().markSerial();

        Command command = Command.createCommand("echo");
        command.addArg("Hello world!");

        AnonymousResource anonResource = new AnonymousResource(RegistryImageResourceType.getInstance(), RegistryImageConfig.create("busybox"));

        TaskConfig config = TaskConfig.create(Platform.LINUX, anonResource, command);

        Task task = new Task("simple-task", config);

        job.addStep(task);

        pipeline.addJob(job);

        System.out.println(gson.toJson(pipeline));
    }

    @Test
    void pipelineVar() {
        // Define Pipeline
        Pipeline pipeline = new Pipeline();

        String firstJob = String.format("%s-job", Variable.referenceVariable("first"));

        String helloVar = Variable.referenceVariable("hello");

        Job job = new Job(firstJob).markPublic();

        Command command = Command.createCommand("echo");
        command.addArg(String.format("Hello, %s", helloVar));

        AnonymousResource anonResource = new AnonymousResource(RegistryImageResourceType.getInstance(), RegistryImageConfig.create("busybox"));

        TaskConfig config = TaskConfig.create(Platform.LINUX, anonResource, command);

        Task task = new Task("simple-task", config);

        job.addStep(task);

        String levelJobName = String.format("level-%s-job", Variable.referenceVariable("number"));

        Job levelJob = new Job(levelJobName).markPublic();

        levelJob.addStep(task);

        pipeline.addJob(levelJob);
        pipeline.addJob(job);

        System.out.println(gson.toJson(pipeline));
    }

    @Test
    void setPipeline() {
        // Define Pipeline
        Pipeline pipeline = new Pipeline();

        GitResource repo = GitResource.createResource("concourse-examples", GitConfig.create("https://github.com/concourse/examples", "master"));

        pipeline.addResource(repo);

        // Set Self
        Job setSelf = new Job("set-self").markPublic();

        Get initialGet = repo.getGetDefinition().enableTrigger();

        setSelf.addStep(initialGet);

        // Set Example Pipelines
        Job setExamples = new Job("set-example-pipelines").markPublic();

        Get examplesGet = repo.getGetDefinition().enableTrigger().addPassedRequirement(setSelf);

        setExamples.addStep(examplesGet);

        // Set Rendered Pipelines
        Job setRendered = new Job("set-rendered-pipelines").markPublic();

        Get renderedGet = repo.getGetDefinition().enableTrigger().addPassedRequirement(setSelf);

        setRendered.addStep(renderedGet);

        pipeline.addJob(setSelf).addJob(setExamples).addJob(setRendered);

        System.out.println(gson.toJson(pipeline));
    }
}