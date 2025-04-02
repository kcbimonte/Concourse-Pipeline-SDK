package org.concourseci.sdk.step.task;

import com.google.gson.JsonObject;
import org.concourseci.bundled.git.GitResource;
import org.concourseci.bundled.git.GitResourceConfig;
import org.concourseci.bundled.git.get.GitGet;
import org.concourseci.bundled.registry.RegistryImageConfig;
import org.concourseci.sdk.resource.AnonymousResource;
import org.concourseci.sdk.step.task.config.Command;
import org.concourseci.sdk.step.task.config.Platform;
import org.concourseci.sdk.step.task.config.TaskConfig;
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
}