package org.concourseci.sdk.step.task;

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


}