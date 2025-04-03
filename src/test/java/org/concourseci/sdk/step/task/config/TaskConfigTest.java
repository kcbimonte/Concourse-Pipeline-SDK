package org.concourseci.sdk.step.task.config;

import org.concourseci.sdk.resource.AnonymousResource;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertNull;

class TaskConfigTest {

    @ParameterizedTest
    @EnumSource(value = Platform.class, names = {"DARWIN", "WINDOWS"})
    void platformIsNotLinux(Platform platform) {
        // Arrange

        // Act
        TaskConfig config = TaskConfig.create(platform, AnonymousResource.create("busybox"), Command.createCommand("sh").addArg("hello"));

        // Assert
        assertNull(config.getResource());
    }
}