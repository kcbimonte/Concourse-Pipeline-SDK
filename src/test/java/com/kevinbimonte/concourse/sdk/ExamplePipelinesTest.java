package com.kevinbimonte.concourse.sdk;

import com.kevinbimonte.concourse.bundled.git.GitResource;
import com.kevinbimonte.concourse.bundled.git.GitResourceConfig;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageConfig;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageResource;
import com.kevinbimonte.concourse.bundled.time.TimeConfig;
import com.kevinbimonte.concourse.bundled.time.TimeResource;
import com.kevinbimonte.concourse.sdk.job.BuildLogRetentionPolicy;
import com.kevinbimonte.concourse.sdk.job.Job;
import com.kevinbimonte.concourse.sdk.resource.AnonymousResource;
import com.kevinbimonte.concourse.sdk.resource.Resource;
import com.kevinbimonte.concourse.sdk.resource.get.Get;
import com.kevinbimonte.concourse.sdk.step.SetPipeline;
import com.kevinbimonte.concourse.sdk.step.task.Task;
import com.kevinbimonte.concourse.sdk.step.task.config.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
class ExamplePipelinesTest {

    private static Task generateTask(AnonymousResource<RegistryImageConfig> resource, String taskName, String simpleCommand, String... commandArgs) {
        Command command = Command.createCommand(simpleCommand);

        for (String arg : commandArgs) command.addArg(arg);

        TaskConfig config = TaskConfig.create(Platform.LINUX, resource, command);

        return new Task(taskName, config);
    }

    @Test
    void helloWorld() {
        // Define Pipeline
        Pipeline pipeline = new Pipeline();

        Job job = new Job("job").markPublic();

        AnonymousResource<RegistryImageConfig> busyBox = AnonymousResource.create("busybox");

        Task simpleTask = generateTask(busyBox, "simple-task", "echo", "Hello, world!");

        job.addStep(simpleTask);

        pipeline.addJob(job);

        System.out.println(pipeline.render());
    }

    @Test
    void serialJob() {
        // Define Pipeline
        Pipeline pipeline = new Pipeline();

        Job job = new Job("serial-job").markPublic().markSerial();

        AnonymousResource<RegistryImageConfig> busyBox = AnonymousResource.create("busybox");

        Task simpleTask = generateTask(busyBox, "simple-task", "echo", "Hello, world!");

        job.addStep(simpleTask);

        pipeline.addJob(job);

        System.out.println(pipeline.render());
    }

//    @Test
//    void pipelineVar() {
//        // Define Pipeline
//        Pipeline pipeline = new Pipeline();
//
//        String firstJob = String.format("%s-job", Variable.referenceVariable("first"));
//
//        String helloVar = Variable.referenceVariable("hello");
//
//        Job job = new Job(firstJob).markPublic();
//
//        Command command = Command.createCommand("echo");
//        command.addArg(String.format("Hello, %s", helloVar));
//
//        AnonymousResource<RegistryImageConfig> busyBox = AnonymousResource.create("busybox");
//
//        TaskConfig config = TaskConfig.create(Platform.LINUX, anonResource, command);
//
//        Task task = new Task("simple-task", config);
//
//        job.addStep(task);
//
//        String levelJobName = String.format("level-%s-job", Variable.referenceVariable("number"));
//
//        Job levelJob = new Job(levelJobName).markPublic();
//
//        levelJob.addStep(task);
//
//        pipeline.addJob(levelJob);
//        pipeline.addJob(job);
//
//        System.out.println(pipeline.render());
//    }

    @Test
    void setPipeline() {
        // Define Pipeline
        Pipeline pipeline = new Pipeline();

        GitResource repo = GitResource.createResource("concourse-examples", GitResourceConfig.create("https://github.com/concourse/examples"));
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

        AnonymousResource<RegistryImageConfig> carvelytt = AnonymousResource.create("taylorsilva/carvel-ytt");

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

        System.out.println(pipeline.render());
    }

    @Test
    void createAndConsume() {
        // Define Pipeline
        Pipeline pipeline = new Pipeline();

        Job createAndConsume = new Job("create-and-consume").markPublic();

        Command makeFileCommand = Command.createCommand("sh").addArg("-exc").addArg("ls -la; echo \"Created a file on ${date}\" > ./files/created_file");

        AnonymousResource<RegistryImageConfig> busyBox = AnonymousResource.create("busybox");

        Output filesOutput = Output.create("files");

        TaskConfig makeFileConfig = TaskConfig.create(Platform.LINUX, busyBox, makeFileCommand).addOutput(filesOutput);

        createAndConsume.addStep(new Task("make-a-file", makeFileConfig));

        Command consumeFileCommand = Command.createCommand("cat").addArg("./files/created_file");

        TaskConfig consumeFileConfig = TaskConfig.create(Platform.LINUX, busyBox, consumeFileCommand).addInput(Input.create(filesOutput));

        createAndConsume.addStep(new Task("consume-the-file", consumeFileConfig));

        pipeline.addJob(createAndConsume);

        System.out.println(pipeline.render());
    }

    @Test
    void timeTriggered() {
        // Define Pipeline
        Pipeline pipeline = new Pipeline();

        Resource every30Seconds = TimeResource.createResource("every-30s", new TimeConfig().setInterval("30s")).setIcon("clock-outline");
        pipeline.addResource(every30Seconds);

        Job job = new Job("job").markPublic();

        AnonymousResource<RegistryImageConfig> busyBox = AnonymousResource.create("busybox");

        Task simpleTask = generateTask(busyBox, "simple-task", "echo", "Hello, world!");

        job.addStep(every30Seconds.createGetDefinition().enableTrigger()).addStep(simpleTask);

        pipeline.addJob(job);

        System.out.println(pipeline.render());
    }

    @Test
    void gitTriggered() {
        // Define Pipeline
        Pipeline pipeline = new Pipeline();

        Resource concourseDocs = GitResource.createResource("concourse-docs-git", GitResourceConfig.create("https://github.com/concourse/docs")).setIcon("github");
        pipeline.addResource(concourseDocs);

        Job job = new Job("job").markPublic();

        AnonymousResource<RegistryImageConfig> busyBox = AnonymousResource.create("busybox");

        Task simpleTask = generateTask(busyBox, "list-files", "ls", "-la", "./concourse-docs-git");

        job.addStep(concourseDocs.createGetDefinition().enableTrigger()).addStep(simpleTask);

        pipeline.addJob(job);

        System.out.println(pipeline.render());
    }

    @Test
    void manuallyTriggered() {
        // Define Pipeline
        Pipeline pipeline = new Pipeline();

        Resource every30Seconds = TimeResource.createResource("every-30s", new TimeConfig().setInterval("30s")).setIcon("clock-outline");
        pipeline.addResource(every30Seconds);

        AnonymousResource<RegistryImageConfig> busyBox = AnonymousResource.create("busybox");

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

        System.out.println(pipeline.render());
    }

    @Test
    void hooks() {
        // Define Pipeline
        Pipeline pipeline = new Pipeline();

        AnonymousResource<RegistryImageConfig> busyBox = AnonymousResource.create("busybox");

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

        System.out.println(pipeline.render());
    }

    @Test
    void golangLibrary() {
        // Define Pipeline
        Pipeline pipeline = new Pipeline();

        RegistryImageConfig v120Config = RegistryImageConfig.create("golang", "1.20-alpine");
        Resource v120Image = RegistryImageResource.createResource("golang-1.20.x-image", v120Config).setIcon("docker");

        RegistryImageConfig v121Config = RegistryImageConfig.create("golang", "1.21-alpine");
        Resource v121Image = RegistryImageResource.createResource("golang-1.21.x-image", v121Config).setIcon("docker");

        RegistryImageConfig v122Config = RegistryImageConfig.create("golang", "1.22-alpine");
        Resource v122Image = RegistryImageResource.createResource("golang-1.22.x-image", v122Config).setIcon("docker");

        pipeline.addResource(v120Image).addResource(v121Image).addResource(v122Image);

        String goTest = """
                GOPATH=$PWD/go
                
                go version
                """;

        TaskConfig config = TaskConfig.create(Platform.LINUX, Command.createCommand("/bin/sh").addArg("-c").addArg(goTest));

        Job v120 = new Job("golang-1.20").markPublic()
                .addStep(v120Image.createGetDefinition().enableTrigger())
                .addStep(new Task("run-tests", config).setImage(v120Image.createGetDefinition()));
        Job v121 = new Job("golang-1.21").markPublic()
                .addStep(v121Image.createGetDefinition().enableTrigger())
                .addStep(new Task("run-tests", config).setImage(v121Image.createGetDefinition()));
        Job v122 = new Job("golang-1.22").markPublic()
                .addStep(v122Image.createGetDefinition().enableTrigger())
                .addStep(new Task("run-tests", config).setImage(v122Image.createGetDefinition()));

        pipeline.addJob(v120).addJob(v121).addJob(v122);

        System.out.println(pipeline.render());
    }

    @Test
    void railsApplication() {
        // Define Pipeline
        Pipeline pipeline = new Pipeline();

        GitResourceConfig repoConfig = GitResourceConfig.create("https://github.com/rails/rails-contributors.git");
        Resource repo = GitResource.createResource("rails-contributors-git", repoConfig).setIcon("github");
        pipeline.addResource(repo);

        // Task Config
        AnonymousResource<RegistryImageConfig> ruby = AnonymousResource.create("ruby", "2.6.5");
        String railsTesting = """
                echo "=== Setting up Postgres..."
                apt-get update
                apt-get install -y postgresql libpq-dev cmake nodejs
                cat > /etc/postgresql/*/main/pg_hba.conf <<-EOF
                host   all   postgres   localhost   trust
                EOF
                service postgresql restart
                echo "=== Project requires that we clone rails... "
                cd rails-contributors-git
                git clone --mirror https://github.com/rails/rails
                echo "=== Installing Gems..."
                gem install -N bundler
                bundle install
                echo "=== Running Tests..."
                bundle exec rails db:setup
                bundle exec rails test
                """;
        Command taskCommand = Command.createCommand("/bin/bash").addArg("-c").addArg(railsTesting);
        TaskConfig taskConfig = TaskConfig.create(Platform.LINUX, ruby, taskCommand)
                .addInput(Input.create(repo.createGetDefinition()))
                .addParam("RAILS_ENV", "test")
                .addParam("DATABASE_URL", "postgresql://postgres@localhost");

        Task task = new Task("run-tests", taskConfig);

        // Job
        Job job = new Job("test").markPublic();

        job.addStep(repo.createGetDefinition().enableTrigger()).addStep(task);

        pipeline.addJob(job);

        // Serialize
        System.out.println(pipeline.render());
    }

    @Test
    void javaApplication() {
        // Define Pipeline
        Pipeline pipeline = new Pipeline();

        GitResourceConfig repoConfig = GitResourceConfig.create("https://github.com/apache/kafka.git");
        Resource repo = GitResource.createResource("apache-kafka-git", repoConfig).setIcon("github");
        pipeline.addResource(repo);

        // Task Config
        AnonymousResource<RegistryImageConfig> gradle = AnonymousResource.create("gradle", "jdk8-slim");
        String javaTesting = """
                java -Xmx32m -version
                javac -J-Xmx32m -version
                
                cd apache-kafka-git
                
                gradle wrapper
                ./gradlew rat
                ./gradlew systemTestLibs
                """;
        Command testingCommand = Command.createCommand("/bin/sh")
                .addArg("-c")
                .addArg(javaTesting)
                .setUser("root");

        TaskConfig config = TaskConfig.create(Platform.LINUX, gradle, testingCommand)
                .addInput(Input.create(repo.createGetDefinition()))
                .addCache("$HOME/.m2/repository")
                .addCache("$HOME/.gradle/caches/")
                .addCache("$HOME/.gradle/wrapper/");

        Task task = new Task("run-tests", config);

        // Job Config
        Job job = new Job("test")
                .markPublic()
                .addStep(repo.createGetDefinition().enableTrigger())
                .addStep(task);

        pipeline.addJob(job);

        // Serialize
        System.out.println(pipeline.render());
    }

    @Test
    void nodeJSApplication() {
        // Define Pipeline
        Pipeline pipeline = new Pipeline();

        GitResourceConfig repoConfig = GitResourceConfig.create("https://github.com/nodejs/nodejs.org.git");
        Resource repo = GitResource.createResource("repo", repoConfig).setIcon("github");
        pipeline.addResource(repo);

        RegistryImageConfig registryConfig = RegistryImageConfig.create("node", "18");
        Resource image = RegistryImageResource.createResource("node-image", registryConfig);
        pipeline.addResource(image);

        // Task Config
        Output dependencies = Output.create("dependencies").setPath("repo/node_modules");
        Command installCommand = Command.createCommand("npm").addArg("install").setWorkingDirectory("repo");
        TaskConfig installConfig = TaskConfig.create(Platform.LINUX, installCommand)
                .addInput(Input.create(repo.createGetDefinition()))
                .addOutput(dependencies);
        Task install = new Task("install", installConfig);
        install.setImage(image.createGetDefinition());

        Command testCommand = Command.createCommand("npm").addArg("run").addArg("test").setWorkingDirectory("repo");
        TaskConfig testConfig = TaskConfig.create(Platform.LINUX, testCommand)
                .addInput(Input.create(repo.createGetDefinition()))
                .addInput(Input.create(dependencies).setPath("repo/node_modules"));
        Task test = new Task("test", testConfig);
        test.setImage(image.createGetDefinition());

        // Job Config
        Job job = new Job("test")
                .markPublic()
                .addStep(image.createGetDefinition())
                .addStep(repo.createGetDefinition().enableTrigger())
                .addStep(install)
                .addStep(test);

        pipeline.addJob(job);

        // Serialize
        System.out.println(pipeline.render());
    }

    @Test
    void phpApplication() {
        // Define Pipeline
        Pipeline pipeline = new Pipeline();

        GitResourceConfig repoConfig = GitResourceConfig.create("https://github.com/beyondcode/laravel-websockets.git");
        Resource repo = GitResource.createResource("larvel-websockets-git", repoConfig).setIcon("github");
        pipeline.addResource(repo);

        // Task Config
        String phpTest = """
                cd larvel-websockets-git
                
                composer install
                vendor/bin/phpunit --coverage-text --coverage-clover=coverage.clover
                """;
        Command command = Command.createCommand("/bin/sh").addArg("-c").addArg(phpTest);
        AnonymousResource<RegistryImageConfig> resource = AnonymousResource.create("composer");
        TaskConfig config = TaskConfig.create(Platform.LINUX, resource, command)
                .addInput(Input.create(repo.createGetDefinition()));
        Task task = new Task("run-tests", config);

        // Job Config
        Job job = new Job("test").markPublic()
                .addStep(repo.createGetDefinition().enableTrigger())
                .addStep(task);

        pipeline.addJob(job);

        // Serialize
        System.out.println(pipeline.render());
    }
}