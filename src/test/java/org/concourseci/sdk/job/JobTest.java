package org.concourseci.sdk.job;

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
    }

    @ParameterizedTest
    @ValueSource(strings = {"123_job", "((my_var))-job", "MY_JOB"})
    void invalidJobName(String jobName) {
        assertThrows(RuntimeException.class, () -> new Job(jobName));
    }
}