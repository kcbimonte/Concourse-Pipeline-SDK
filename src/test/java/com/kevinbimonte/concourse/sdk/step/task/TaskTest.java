package com.kevinbimonte.concourse.sdk.step.task;

import com.google.gson.JsonObject;
import com.kevinbimonte.concourse.bundled.git.GitResource;
import com.kevinbimonte.concourse.bundled.git.GitResourceConfig;
import com.kevinbimonte.concourse.bundled.git.get.GitGet;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageConfig;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageResource;
import com.kevinbimonte.concourse.sdk.resource.AnonymousResource;
import com.kevinbimonte.concourse.sdk.step.task.config.*;
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
}