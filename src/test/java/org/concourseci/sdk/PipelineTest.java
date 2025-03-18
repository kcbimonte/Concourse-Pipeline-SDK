package org.concourseci.sdk;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.concourseci.bundled.git.GitConfig;
import org.concourseci.bundled.git.GitResource;
import org.concourseci.bundled.registry.RegistryImageConfig;
import org.concourseci.bundled.registry.RegistryImageResourceType;
import org.concourseci.bundled.time.TimeConfig;
import org.concourseci.bundled.time.TimeResource;
import org.concourseci.sdk.job.BuildLogRetentionPolicy;
import org.concourseci.sdk.job.Job;
import org.concourseci.sdk.resource.AnonymousResource;
import org.concourseci.sdk.resource.Get;
import org.concourseci.sdk.resource.Resource;
import org.concourseci.sdk.step.SetPipeline;
import org.concourseci.sdk.step.task.*;
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

        AnonymousResource busyBox = new AnonymousResource(RegistryImageResourceType.getInstance(), RegistryImageConfig.create("busybox"));

        Task simpleTask = generateTask(busyBox, "simple-task", "echo", "Hello, world!");

        job.addStep(simpleTask);

        pipeline.addJob(job);

        System.out.println(gson.toJson(pipeline));
    }

    @Test
    void serialJob() {
        // Define Pipeline
        Pipeline pipeline = new Pipeline();

        Job job = new Job("serial-job").markPublic().markSerial();

        AnonymousResource busyBox = new AnonymousResource(RegistryImageResourceType.getInstance(), RegistryImageConfig.create("busybox"));

        Task simpleTask = generateTask(busyBox, "simple-task", "echo", "Hello, world!");

        job.addStep(simpleTask);

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

        GitResource repo = GitResource.createResource("concourse-examples", GitConfig.create("https://github.com/concourse/examples"));
        repo.setIcon("github").setCheckEvery("30m");

        pipeline.addResource(repo);

        // Set Self
        Job setSelf = new Job("set-self").markPublic();

        Get initialGet = repo.createGetDefinition().enableTrigger();

        setSelf.addStep(initialGet);
        setSelf.addStep(SetPipeline.create("self", initialGet, "pipelines/set-pipelines.yml"));

        Get blockedGet = repo.createGetDefinition().enableTrigger().addPassedRequirement(setSelf);

        // Set Example Pipelines
        Job setExamples = new Job("set-example-pipelines").markPublic();

        setExamples.addStep(blockedGet);

        setExamples.addStep(SetPipeline.create("job", blockedGet, "pipelines/hello-world.yml"));
        setExamples.addStep(SetPipeline.create("separate-task-config", blockedGet, "pipelines/separate-task-config.yml"));
        setExamples.addStep(SetPipeline.create("serial-job", blockedGet, "pipelines/serial-job.yml"));

        SetPipeline setPipelineVars = SetPipeline.create("pipeline-vars", blockedGet, "pipelines/pipeline-vars.yml");
        setPipelineVars.addVar("first", "initial").addVar("number", "9000").addVar("hello", "HAL");
        setExamples.addStep(setPipelineVars);

        SetPipeline setPipelineVarsFile = SetPipeline.create("pipeline-vars-file", blockedGet, "pipelines/pipeline-vars.yml");
        setPipelineVarsFile.addVarFile(blockedGet, "pipelines/vars-file.yml");
        setExamples.addStep(setPipelineVarsFile);

        SetPipeline instanceVarsOne = SetPipeline.create("instance-groups", blockedGet, "pipelines/pipeline-vars.yml");
        SetPipeline instanceVarsTwo = SetPipeline.create("instance-groups", blockedGet, "pipelines/pipeline-vars.yml");
        instanceVarsOne.addInstanceVar("first", "initial").addInstanceVar("number", "9000").addInstanceVar("hello", "HAL");
        instanceVarsTwo.addInstanceVar("first", "second").addInstanceVar("number", "3000").addInstanceVar("hello", "WALLY-E");
        setExamples.addStep(instanceVarsOne);
        setExamples.addStep(instanceVarsTwo);

        setExamples.addStep(SetPipeline.create("task-passing-artifact", blockedGet, "pipelines/task-passing-artifact.yml"));
        setExamples.addStep(SetPipeline.create("time-triggered", blockedGet, "pipelines/time-triggered.yml"));
        setExamples.addStep(SetPipeline.create("git-triggered", blockedGet, "pipelines/git-triggered.yml"));
        setExamples.addStep(SetPipeline.create("manual-trigger", blockedGet, "pipelines/manually-triggered.yml"));
        setExamples.addStep(SetPipeline.create("hooks", blockedGet, "pipelines/job-and-task-hooks.yml"));
        setExamples.addStep(SetPipeline.create("golang-lib", blockedGet, "pipelines/golang-lib.yml"));
        setExamples.addStep(SetPipeline.create("rails", blockedGet, "pipelines/rails-app-testing.yml"));
        setExamples.addStep(SetPipeline.create("nodejs", blockedGet, "pipelines/nodejs-app-testing.yml"));
        setExamples.addStep(SetPipeline.create("php", blockedGet, "pipelines/php-larvel-app-testing.yml"));
        setExamples.addStep(SetPipeline.create("java", blockedGet, "pipelines/java.yml"));

        // Set Rendered Pipelines
        Job setRendered = new Job("set-rendered-pipelines").markPublic();

        setRendered.addStep(blockedGet);

        AnonymousResource carvelytt = new AnonymousResource(RegistryImageResourceType.getInstance(), RegistryImageConfig.create("taylorsilva/carvel-ytt"));

        String yttGen = """
                ytt -f ./concourse-examples/pipelines/templates/simple > hello-world-rendered.yml
                ytt -f ./concourse-examples/pipelines/templates/multiple-files > multi-files-rendered.yml
                mv *.yml ./pipeline/
                """;

        Command renderCommand = Command.createCommand("sh").addArg("-cx").addArg(yttGen);

        TaskConfig renderConfig = TaskConfig.create(Platform.LINUX, carvelytt, renderCommand).addInput(Input.create(blockedGet)).addOutput(Output.create("pipeline"));
        Task renderTask = new Task("render-pipelines", renderConfig);

        setRendered.addStep(renderTask);

        setRendered.addStep(SetPipeline.create("hello-world-rendered", "pipeline/hello-world-rendered.yml"));
        setRendered.addStep(SetPipeline.create("multi-files-rendered", "pipeline/multi-files-rendered.yml"));

        pipeline.addJob(setSelf).addJob(setExamples).addJob(setRendered);

        System.out.println(gson.toJson(pipeline));
    }

    @Test
    void createAndConsume() {
        // Define Pipeline
        Pipeline pipeline = new Pipeline();

        Job createAndConsume = new Job("create-and-consume").markPublic();

        Command makeFileCommand = Command.createCommand("sh").addArg("-exc").addArg("ls -la; echo \"Created a file on ${date}\" > ./files/created_file");

        AnonymousResource busyBox = new AnonymousResource(RegistryImageResourceType.getInstance(), RegistryImageConfig.create("busybox"));

        Output filesOutput = Output.create("files");

        TaskConfig makeFileConfig = TaskConfig.create(Platform.LINUX, busyBox, makeFileCommand).addOutput(filesOutput);

        createAndConsume.addStep(new Task("make-a-file", makeFileConfig));

        Command consumeFileCommand = Command.createCommand("cat").addArg("./files/created_file");

        TaskConfig consumeFileConfig = TaskConfig.create(Platform.LINUX, busyBox, consumeFileCommand).addInput(Input.create(filesOutput));

        createAndConsume.addStep(new Task("consume-the-file", consumeFileConfig));

        pipeline.addJob(createAndConsume);

        System.out.println(gson.toJson(pipeline));
    }

    @Test
    void timeTriggered() {
        // Define Pipeline
        Pipeline pipeline = new Pipeline();

        Resource every30Seconds = TimeResource.createResource("every-30s", new TimeConfig().setInterval("30s")).setIcon("clock-outline");
        pipeline.addResource(every30Seconds);

        Job job = new Job("job").markPublic();

        AnonymousResource busyBox = new AnonymousResource(RegistryImageResourceType.getInstance(), RegistryImageConfig.create("busybox"));

        Task simpleTask = generateTask(busyBox, "simple-task", "echo", "Hello, world!");

        job.addStep(every30Seconds.createGetDefinition().enableTrigger()).addStep(simpleTask);

        pipeline.addJob(job);

        System.out.println(gson.toJson(pipeline));
    }

    @Test
    void gitTriggered() {
        // Define Pipeline
        Pipeline pipeline = new Pipeline();

        Resource concourseDocs = GitResource.createResource("concourse-docs-git", GitConfig.create("https://github.com/concourse/docs")).setIcon("github");
        pipeline.addResource(concourseDocs);

        Job job = new Job("job").markPublic();

        AnonymousResource busyBox = new AnonymousResource(RegistryImageResourceType.getInstance(), RegistryImageConfig.create("busybox"));

        Task simpleTask = generateTask(busyBox, "list-files", "ls", "-la", "./concourse-docs-git");

        job.addStep(concourseDocs.createGetDefinition().enableTrigger()).addStep(simpleTask);

        pipeline.addJob(job);

        System.out.println(gson.toJson(pipeline));
    }

    @Test
    void manuallyTriggered() {
        // Define Pipeline
        Pipeline pipeline = new Pipeline();

        Resource every30Seconds = TimeResource.createResource("every-30s", new TimeConfig().setInterval("30s")).setIcon("clock-outline");
        pipeline.addResource(every30Seconds);

        AnonymousResource busyBox = new AnonymousResource(RegistryImageResourceType.getInstance(), RegistryImageConfig.create("busybox"));

        Task simpleTask = generateTask(busyBox, "simple-task", "echo", "Hello, world!");

        Job triggeredFirst = new Job("triggered-first")
                .markPublic()
                .setBuildLogRetention(BuildLogRetentionPolicy.create().setBuilds(20))
                .addStep(every30Seconds.createGetDefinition().enableTrigger())
                .addStep(simpleTask);

        Job notTriggered = new Job("not-triggered")
                .markPublic()
                .addStep(every30Seconds.createGetDefinition().addPassedRequirement(triggeredFirst))
                .addStep(simpleTask);

        Job triggeredSecond = new Job("triggered-second")
                .markPublic()
                .setBuildLogRetention(BuildLogRetentionPolicy.create().setBuilds(20))
                .addStep(every30Seconds.createGetDefinition().addPassedRequirement(triggeredFirst).enableTrigger())
                .addStep(simpleTask);

        pipeline.addJob(triggeredFirst).addJob(triggeredSecond).addJob(notTriggered);

        System.out.println(gson.toJson(pipeline));
    }

    @Test
    void hooks() {
        // Define Pipeline
        Pipeline pipeline = new Pipeline();

        AnonymousResource busyBox = new AnonymousResource(RegistryImageResourceType.getInstance(), RegistryImageConfig.create("busybox"));

        // Successful Task Configuration
        Task onSuccessTask = generateTask(busyBox, "task-success", "echo", "This task succeeded!");
        Task onAbortTask = generateTask(busyBox, "task-aborted", "echo", "This task was aborted!");
        Task successfulTask = generateTask(busyBox, "successful-task", "sh", "-lc", "exit 0")
                .setOnSuccess(onSuccessTask)
                .setOnAbort(onAbortTask);

        // Failing Task Configuration
        Task onFailureTask = generateTask(busyBox, "task-failure", "echo", "This task failed!");
        Task failingTask = generateTask(busyBox, "failing-task", "sh", "-lc", "exit 1")
                .setOnFailure(onFailureTask);

        // Job Configuration
        Task onSuccessJob = generateTask(busyBox, "job-success", "echo", "This job succeeded!");
        Task onFailureJob = generateTask(busyBox, "job-failure", "echo", "This job failed!");
        Task onAbortJob = generateTask(busyBox, "job-aborted", "echo", "This job was aborted!");

        Job job = new Job("job")
                .markPublic()
                .addStep(successfulTask)
                .addStep(failingTask)
                .setOnSuccess(onSuccessJob)
                .setOnFailure(onFailureJob)
                .setOnAbort(onAbortJob);

        pipeline.addJob(job);

        System.out.println(gson.toJson(pipeline));
    }

    private static Task generateTask(AnonymousResource resource, String taskName, String simpleCommand, String... commandArgs) {
        Command command = Command.createCommand(simpleCommand);

        for (String arg : commandArgs) command.addArg(arg);

        TaskConfig config = TaskConfig.create(Platform.LINUX, resource, command);

        return new Task(taskName, config);
    }
}