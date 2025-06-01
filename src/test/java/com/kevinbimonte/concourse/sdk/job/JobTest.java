package com.kevinbimonte.concourse.sdk.job;

import com.kevinbimonte.concourse.bundled.mock.MockConfig;
import com.kevinbimonte.concourse.bundled.mock.MockResource;
import com.kevinbimonte.concourse.sdk.resource.AnonymousResource;
import com.kevinbimonte.concourse.sdk.step.task.Task;
import com.kevinbimonte.concourse.sdk.step.task.config.Command;
import com.kevinbimonte.concourse.sdk.step.task.config.Platform;
import com.kevinbimonte.concourse.sdk.step.task.config.TaskConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class JobTest {

    private static Stream<Arguments> validNamePairings() {
        return Stream.of(
                Arguments.of("job", "new_job"),
                Arguments.of("my_job", "new_my_job"),
                Arguments.of("the-job", "new_the-job"),
                Arguments.of("job_123", "new_job_123"),
                Arguments.of("job.12", "new_job.12")
        );
    }

    private static Stream<Arguments> invalidNamePairings() {
        return Stream.of(
                Arguments.of("job", "123_job"),
                Arguments.of("my_job", "((my_var))-job"),
                Arguments.of("the-job", "MY_JOB")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"job", "my_job", "the-job", "job_123", "job.12"})
    void validJobName(String jobName) {
        // Arrange

        // Act
        Job job = assertDoesNotThrow(() -> new Job(jobName));

        // Assert
        assertEquals(jobName, job.getName());
    }

    @ParameterizedTest
    @MethodSource("validNamePairings")
    void validNewName(String initialName, String newName) {
        // Arrange

        // Act
        Job job = assertDoesNotThrow(() -> new Job(initialName));

        // Assert
        assertEquals(initialName, job.getName());

        // Act
        job.changeName(newName);

        // Assert
        assertEquals(newName, job.getName());
        assertEquals(initialName, job.getOldName());
    }

    @ParameterizedTest
    @MethodSource("invalidNamePairings")
    void invalidNewName(String initialName, String newName) {
        // Arrange

        // Act
        Job job = assertDoesNotThrow(() -> new Job(initialName));

        // Assert
        assertEquals(initialName, job.getName());
        assertThrows(IllegalArgumentException.class, () -> job.changeName(newName));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123_job", "((my_var))-job", "MY_JOB"})
    void invalidJobName(String jobName) {
        assertThrows(RuntimeException.class, () -> new Job(jobName));
    }

    @ParameterizedTest
    @ValueSource(strings = {"group", "my_group", "the-group", "group_123", "group.12"})
    void validSerialGroup(String groupName) {
        // Arrange
        Job job = new Job("job");

        // Act
        job.addSerialGroup(groupName);

        // Assert
        assertEquals(1, job.getSerialGroups().size());
        assertNull(job.getMaxInFlight());
        assertTrue(job.getSerialGroups().contains(groupName));
        assertTrue(job.getIsSerial());
    }

    @ParameterizedTest
    @ValueSource(strings = {"123_group", "((my_var))-group", "MY_GROUP"})
    void invalidSerialGroup(String groupName) {
        Job job = new Job("job");

        assertThrows(RuntimeException.class, () -> job.addSerialGroup(groupName));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 50})
    void validMaxInFlight(Integer maxInFlight) {
        // Arrange
        Job job = new Job("job");

        // Act
        job.setMaxInFlight(maxInFlight);

        // Assert
        assertEquals(maxInFlight, job.getMaxInFlight());
        assertNull(job.getSerialGroups());
        assertNull(job.getIsSerial());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -100, -500})
    void invalidMaxInFlight(Integer maxInFlight) {
        Job job = new Job("job");

        assertThrows(RuntimeException.class, () -> job.setMaxInFlight(maxInFlight));
    }

    @Test
    void publicJob() {
        // Arrange
        Job job = new Job("name");

        // Act
        job.markPublic();

        // Assert
        assertTrue(job.getIsPublic());
    }

    @Test
    void serialJob() {
        // Arrange
        Job job = new Job("name");

        // Act
        job.markSerial();

        // Assert
        assertTrue(job.getIsSerial());
        assertNull(job.getMaxInFlight());
    }

    @Test
    @DisplayName("Chaining Serial and Max In Flight sets Max in Flight")
    void chainingSerialGroupMaxInFlight() {
        // Arrange
        Job job = new Job("name");

        // Act
        job.addSerialGroup("group").setMaxInFlight(4);

        // Assert
        assertNull(job.getSerialGroups());
        assertNull(job.getIsSerial());
        assertEquals(4, job.getMaxInFlight());
    }

    @Test
    @DisplayName("Chaining Max In Flight and Serial sets Serial")
    void chainingMaxInFlightSerialGroup() {
        // Arrange
        Job job = new Job("name");

        // Act
        job.setMaxInFlight(4).addSerialGroup("group");

        // Assert
        assertEquals(1, job.getSerialGroups().size());
        assertTrue(job.getSerialGroups().contains("group"));
        assertTrue(job.getIsSerial());
        assertNull(job.getMaxInFlight());
    }

    @Test
    void jobsAreEqual() {
        // Arrange
        Job job = new Job("job");
        Job job2 = new Job("job");

        // Act
        boolean equality = job.equals(job2);

        // Assert
        assertTrue(equality);
    }

    @Test
    void jobsAreNotEqual() {
        // Arrange
        Job job = new Job("job");
        Job job2 = new Job("job1");

        // Act
        boolean equality = job.equals(job2);

        // Assert
        assertFalse(equality);
    }

    @Test
    void nullIsNotEqual() {
        // Arrange
        Job job = new Job("job");

        // Act
        boolean equality = job.equals(null);

        // Assert
        assertFalse(equality);
    }

    @Test
    void otherObjIsNotEqual() {
        // Arrange
        Job job = new Job("job");
        MockResource resource = MockResource.create("mock", MockConfig.create());

        // Act
        boolean equality = job.equals(resource);

        // Assert
        assertFalse(equality);
    }

    @Test
    void buildLogRetention() {
        // Arrange
        Job job = new Job("job");
        BuildLogRetentionPolicy policy = BuildLogRetentionPolicy.create().setDays(1);

        // Act
        job.setBuildLogRetention(policy);

        // Assert
        assertEquals(1, job.getBuildLogRetentionPolicy().getDays());
        assertNull(job.getBuildLogRetentionPolicy().getBuilds());
        assertNull(job.getBuildLogRetentionPolicy().getMinimumSucceededBuilds());
    }

    @Test
    @Deprecated
    void legacyBuildLogRetention() {
        // Arrange
        Job job = new Job("job");

        // Act
        job.setBuildLogsToRetain(1);

        // Assert
        assertEquals(1, job.getBuildLogsToRetain());
    }

    @Test
    @Deprecated
    void invalidLegacyBuildLogRetention() {
        // Arrange
        Job job = new Job("job");

        // Act / Assert
        assertThrows(IllegalArgumentException.class, () -> job.setBuildLogsToRetain(-1));
    }

    @Test
    void disableManualTrigger() {
        // Arrange
        Job job = new Job("job");

        // Act
        job.disableManualTrigger();

        // Assert
        assertTrue(job.getIsManualTriggerDisabled());
    }

    @Test
    void markInterruptable() {
        // Arrange
        Job job = new Job("job");

        // Act
        job.markInterruptable();

        // Assert
        assertTrue(job.getIsInterruptible());
    }

    @Test
    void addStep() {
        // Arrange
        Job job = new Job("name");
        Task task = Task.create("my_task", TaskConfig.create(Platform.LINUX, AnonymousResource.create("busybox"), Command.createCommand("echo").addArg("Hello, World!")));

        // Act
        job.addStep(task);

        // Assert
        assertEquals(1, job.getPlan().size());

        assertInstanceOf(Task.class, job.getPlan().getFirst());

        Task myTask = (Task) job.getPlan().getFirst();

        assertNotNull(myTask);
        assertEquals("my_task", myTask.getTask());
    }
}