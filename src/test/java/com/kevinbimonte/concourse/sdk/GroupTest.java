package com.kevinbimonte.concourse.sdk;

import com.kevinbimonte.concourse.sdk.job.Job;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {

    @ParameterizedTest
    @ValueSource(strings = {"group", "my_group", "the-group", "group_123", "group.12", "123_group"})
    void validGroupName(String groupName) {
        // Arrange

        // Act
        Group group = assertDoesNotThrow(() -> new Group(groupName));

        // Assert
        assertEquals(groupName, group.getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "((my_var))-group", "MY_GROUP"})
    void invalidGroupName(String groupName) {
        assertThrows(RuntimeException.class, () -> new Group(groupName));
    }

    @Test
    void jobFilter() {
        // Arrange
        String filterA = "*-build";
        String filterB = "owasp-*";
        Group group = new Group("name");

        // Act
        group.addJobFilter(filterA).addJobFilter(filterB);

        // Assert
        assertEquals(2, group.getJobs().size());
        assertTrue(group.getJobs().contains("owasp-*"));
    }

    @Test
    void filterBasedOnJob() {
        // Arrange
        Job job1 = new Job("my_job");
        Job job2 = new Job("my_second_job");
        Group group = new Group("my_group");

        // Act
        group.addJob(job1).addJob(job2);

        // Assert
        assertEquals(2, group.getJobs().size());
        assertTrue(group.getJobs().contains("my_job"));
        assertTrue(group.getJobs().contains("my_second_job"));
    }
}