package com.kevinbimonte.concourse.sdk.job;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BuildLogRetentionPolicyTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 10, 20, 255})
        // six numbers
    void setValidDays(int days) {
        // Arrange
        BuildLogRetentionPolicy policy = BuildLogRetentionPolicy.create();

        // Act
        policy.setDays(days);

        // Assert
        assertEquals(days, policy.getDays());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -3, -5, -10, -20, -255})
        // six numbers
    void setInvalidDays(int days) {
        // Arrange
        BuildLogRetentionPolicy policy = BuildLogRetentionPolicy.create();

        assertThrows(RuntimeException.class, () -> policy.setDays(days));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 10, 20, 255})
        // six numbers
    void setValidBuilds(int builds) {
        // Arrange
        BuildLogRetentionPolicy policy = BuildLogRetentionPolicy.create();

        // Act
        policy.setBuilds(builds);

        // Assert
        assertEquals(builds, policy.getBuilds());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -3, -5, -10, -20, -255})
        // six numbers
    void setInvalidBuilds(int builds) {
        // Arrange
        BuildLogRetentionPolicy policy = BuildLogRetentionPolicy.create();

        assertThrows(RuntimeException.class, () -> policy.setBuilds(builds));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 10, 20, 255})
        // six numbers
    void setValidMinimumSucceeded(int minimumSucceeded) {
        // Arrange
        BuildLogRetentionPolicy policy = BuildLogRetentionPolicy.create();

        // Act
        policy.setMinimumSucceeded(minimumSucceeded);

        // Assert
        assertEquals(minimumSucceeded, policy.getMinimumSucceededBuilds());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -3, -5, -10, -20, -255})
        // six numbers
    void setInvalidMinimumSucceeded(int minimumSucceeded) {
        // Arrange
        BuildLogRetentionPolicy policy = BuildLogRetentionPolicy.create();

        assertThrows(RuntimeException.class, () -> policy.setMinimumSucceeded(minimumSucceeded));
    }

    @Test
    void methodsAreChainable() {
        // Arrange
        BuildLogRetentionPolicy policy = BuildLogRetentionPolicy.create();

        // Act
        policy.setMinimumSucceeded(1)
                .setDays(1)
                .setBuilds(3);

        // Assert
        assertEquals(1, policy.getMinimumSucceededBuilds());
        assertEquals(1, policy.getDays());
        assertEquals(3, policy.getBuilds());
    }
}