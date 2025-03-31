package org.concourseci.sdk.job;

import org.concourseci.bundled.mock.MockConfig;
import org.concourseci.bundled.mock.MockResource;
import org.concourseci.sdk.resource.AnonymousResource;
import org.concourseci.sdk.step.task.Task;
import org.concourseci.sdk.step.task.config.Command;
import org.concourseci.sdk.step.task.config.Platform;
import org.concourseci.sdk.step.task.config.TaskConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class JobTest {

    @ParameterizedTest
    @ValueSource(strings = {"job", "my_job", "the-job", "job_123", "job.12"})
    void validJobName(String jobName) {
        // Arrange

        // Act
        Job job = assertDoesNotThrow(() -> new Job(jobName));

        // Assert
        assertEquals(jobName, job.getName());
        assertFalse(job.getIsPublic());
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
        assertEquals(1, job.getMaxInFlight());
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
        assertEquals(0, job.getSerialGroups().size());
        assertFalse(job.getIsSerial());
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
        assertEquals(1, job.getMaxInFlight());
    }

    @Test
    @DisplayName("Chaining Serial and Max In Flight sets Max in Flight")
    void chainingSerialGroupMaxInFlight() {
        // Arrange
        Job job = new Job("name");

        // Act
        job.addSerialGroup("group").setMaxInFlight(4);

        // Assert
        assertEquals(0, job.getSerialGroups().size());
        assertFalse(job.getIsSerial());
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
        assertEquals(1, job.getMaxInFlight());
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
        MockResource resource = MockResource.createResource("mock", new MockConfig());

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
    void addStep() {
        // Arrange
        Job job = new Job("name");
        Task task = new Task("my_task", TaskConfig.create(Platform.LINUX, AnonymousResource.create("busybox"), Command.createCommand("echo").addArg("Hello, World!")));

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