package com.kevinbimonte.concourse.sdk.step.task;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kevinbimonte.concourse.bundled.git.GitResource;
import com.kevinbimonte.concourse.bundled.git.GitResourceConfig;
import com.kevinbimonte.concourse.bundled.git.get.GitGet;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageConfig;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageResource;
import com.kevinbimonte.concourse.sdk.Pipeline;
import com.kevinbimonte.concourse.sdk.TestUtils;
import com.kevinbimonte.concourse.sdk.resource.AnonymousResource;
import com.kevinbimonte.concourse.sdk.step.task.config.Command;
import com.kevinbimonte.concourse.sdk.step.task.config.Output;
import com.kevinbimonte.concourse.sdk.step.task.config.Platform;
import com.kevinbimonte.concourse.sdk.step.task.config.TaskConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    void simpleTask() {
        // Arrange
        TaskConfig config = TaskConfig.create(Platform.LINUX, AnonymousResource.create("busybox"), Command.createCommand("sh").addArg("hello"));

        // Act
        Task task = new Task("task", config);

        // Assert
        assertEquals("task", task.getTask());
        assertInstanceOf(RegistryImageConfig.class, task.getConfig().getResource().getConfig());

        RegistryImageConfig returnedConfig = (RegistryImageConfig) task.getConfig().getResource().getConfig();

        assertEquals("busybox", returnedConfig.getRepository());
    }

    @Test
    void taskWithYAMLTemplate() {
        // Arrange
        GitResourceConfig gitConfig = GitResourceConfig.create("https://git.my_domain.com/repo.git");
        GitResource resource = GitResource.createResource("repo", gitConfig);
        GitGet get = resource.createGetDefinition();

        // Act
        Task task = new Task("task", get, "pipeline/templates/my_job.yml");

        // Assert
        assertEquals("task", task.getTask());
        assertNull(task.getConfig());
        assertEquals("repo/pipeline/templates/my_job.yml", task.getFile());
    }

    @Test
    void taskWithYAMLTemplateWithLeadingSlash() {
        // Arrange
        GitResourceConfig gitConfig = GitResourceConfig.create("https://git.my_domain.com/repo.git");
        GitResource resource = GitResource.createResource("repo", gitConfig);
        GitGet get = resource.createGetDefinition();

        // Act
        Task task = new Task("task", get, "/pipeline/templates/my_second_job.yml");

        // Assert
        assertEquals("task", task.getTask());
        assertNull(task.getConfig());
        assertEquals("repo/pipeline/templates/my_second_job.yml", task.getFile());
    }

    @Test
    void taskWithNullPath() {
        // Arrange
        GitResourceConfig gitConfig = GitResourceConfig.create("https://git.my_domain.com/repo.git");
        GitResource resource = GitResource.createResource("repo", gitConfig);
        GitGet get = resource.createGetDefinition();

        // Assert
        assertThrows(RuntimeException.class, () -> new Task("task", get, null));
    }

    @Test
    void specifyingTaskImage() {
        // Arrange
        RegistryImageConfig config = RegistryImageConfig.create("busybox");
        RegistryImageResource busyBox = RegistryImageResource.createResource("busy_box", config);

        Task task = new Task("task", TaskConfig.create(Platform.LINUX, Command.createCommand("echo").addArg("Hello World")));

        // Act
        task.setImage(busyBox.createGetDefinition());

        // Assert
        assertNotNull(task.getImage());
        assertEquals("busy_box", task.getImage());
    }

    @Test
    void markPrivileged() {
        // Arrange
        TaskConfig config = TaskConfig.create(Platform.LINUX, AnonymousResource.create("busybox"), Command.createCommand("sh").addArg("hello"));
        Task task = new Task("task", config);

        // Act
        task.markPrivileged();

        // Assert
        assertTrue(task.getPrivileged());
    }

    @Test
    void markHermetic() {
        // Arrange
        TaskConfig config = TaskConfig.create(Platform.LINUX, AnonymousResource.create("busybox"), Command.createCommand("sh").addArg("hello"));
        Task task = new Task("task", config);

        // Act
        task.markHermetic();

        // Assert
        assertTrue(task.getIsHermetic());
    }

    @Test
    void addUnstructuredVariables() {
        // Arrange
        GitResourceConfig gitConfig = GitResourceConfig.create("https://git.my_domain.com/repo.git");
        GitResource resource = GitResource.createResource("repo", gitConfig);
        GitGet get = resource.createGetDefinition();
        Task task = new Task("task", get, "/pipeline/templates/my_second_job.yml");

        // Act
        JsonObject object = new JsonObject();
        object.addProperty("sub_key", "sub_value");

        task.addVar("key", "value")
                .addVar("complex", object)
                .addVar("second", "second");

        // Assert
        assertNotNull(task.getVars());
        assertTrue(task.getVars().has("key"));
        assertEquals("value", task.getVars().get("key").getAsString());

        assertTrue(task.getVars().has("complex"));
        assertInstanceOf(JsonObject.class, task.getVars().get("complex"));
        assertTrue(task.getVars().get("complex").getAsJsonObject().has("sub_key"));
        assertEquals("sub_value", task.getVars().get("complex").getAsJsonObject().get("sub_key").getAsString());

        assertTrue(task.getVars().has("second"));
    }

    @Test
    void setCPULimit() {
        // Arrange
        TaskConfig config = TaskConfig.create(Platform.LINUX, AnonymousResource.create("busybox"), Command.createCommand("sh").addArg("hello"));
        Task task = new Task("task", config);

        // Act
        task.setCPULimit(2);

        // Assert
        assertEquals(2, task.getLimits().getCpu());
        assertNull(task.getLimits().getMemory());
    }

    @Test
    void setMemoryLimits() {
        // Arrange
        TaskConfig config = TaskConfig.create(Platform.LINUX, AnonymousResource.create("busybox"), Command.createCommand("sh").addArg("hello"));
        Task task = new Task("task", config);

        // Act
        task.setMemoryLimit(1024);

        // Assert
        assertEquals(1024, task.getLimits().getMemory());
        assertNull(task.getLimits().getCpu());
    }

    @Test
    void chainContainerLimits() {
        // Arrange
        TaskConfig config = TaskConfig.create(Platform.LINUX, AnonymousResource.create("busybox"), Command.createCommand("sh").addArg("hello"));
        Task task = new Task("task", config);

        // Act
        task.setMemoryLimit(1024).setCPULimit(2);

        // Assert
        assertEquals(1024, task.getLimits().getMemory());
        assertEquals(2, task.getLimits().getCpu());
    }

    @Test
    void addEnvVarParameters() {
        // Arrange
        TaskConfig config = TaskConfig.create(Platform.LINUX, AnonymousResource.create("busybox"), Command.createCommand("sh").addArg("hello"));
        Task task = new Task("task", config);

        // Act
        task.addParam("ECHO_ME", "Eat your fruits").addParam("ALSO_ME", "veggies");

        // Assert
        assertEquals(2, task.getParams().size());
        assertEquals("Eat your fruits", task.getParams().get("ECHO_ME"));
    }

    @Test
    void addInputMappingFromGet() {
        // Arrange
        GitResource resource = GitResource.createResource("repo", GitResourceConfig.create("https://git.website.com/group/repo.git"));
        GitGet get = resource.createGetDefinition();

        Task task = new Task("task", TaskConfig.create(Platform.LINUX, AnonymousResource.create("busybox"), Command.createCommand("echo").addArg("Hello, world!")));

        // Act
        InputMapping mapping = task.addInputMapping(get, "main");

        // Assert
        assertEquals(1, task.getInputMapping().size());

        assertEquals("main", task.getInputMapping().get("repo"));

        assertEquals("repo", mapping.getName());
        assertEquals("main", mapping.getMappedName());
    }

    @Test
    void addInputMappingFromOutput() {
        // Arrange
        Output output = Output.create("repo");

        Task task = new Task("task", TaskConfig.create(Platform.LINUX, AnonymousResource.create("busybox"), Command.createCommand("echo").addArg("Hello, world!")));

        // Act
        InputMapping mapping = task.addInputMapping(output, "main");

        // Assert
        assertEquals(1, task.getInputMapping().size());

        assertEquals("main", task.getInputMapping().get("repo"));

        assertEquals("repo", mapping.getName());
        assertEquals("main", mapping.getMappedName());
    }

    @Test
    void addOutputMapping() {
        // Arrange
        Output output = Output.create("repo");

        Task task = new Task("task", TaskConfig.create(Platform.LINUX, AnonymousResource.create("busybox"), Command.createCommand("echo").addArg("Hello, world!")));

        // Act
        OutputMapping mapping = task.addOutputMapping(output, "main");

        // Assert
        assertEquals(1, task.getOutputMapping().size());

        assertEquals("main", task.getOutputMapping().get("repo"));

        assertEquals("repo", mapping.getName());
        assertEquals("main", mapping.getMappedName());
    }

    @Test
    void taskAcross() {
        // Arrange
        Pipeline pipeline = new Pipeline();

        // Act

        // Assert
        JsonElement generated = JsonParser.parseString(pipeline.render());
        JsonElement expected = TestUtils.loadFromAssets("across/task_across.json");

        assertEquals(expected, generated);
    }

}