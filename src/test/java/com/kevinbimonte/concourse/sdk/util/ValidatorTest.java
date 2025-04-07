package com.kevinbimonte.concourse.sdk.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"group", "my_group", "the-group", "group_123", "group.12"})
    void validIdentifier(String identifier) {
        // Assert
        assertDoesNotThrow(() -> Validator.validateIdentifier(identifier));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123_job", "((my_var))-job", "MY_JOB"})
    void invalidIdentifier(String identifier) {
        assertThrows(IllegalArgumentException.class, () -> Validator.validateIdentifier(identifier));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1ms", "1s", "1m", "1h", "1d", "1w", "1y", "1y1m100s"})
    void validDuration(String duration) {
        // Assert
        assertDoesNotThrow(() -> Validator.validateDuration(duration));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123_job", "1m1y", "1d1w"})
    void invalidDuration(String duration) {
        assertThrows(IllegalArgumentException.class, () -> Validator.validateDuration(duration));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1.0.0", "0.0.1", "1.0.0-develop"})
    void validSemver(String semver) {
        // Assert
        assertDoesNotThrow(() -> Validator.validateSemver(semver));
    }

    @ParameterizedTest
    @ValueSource(strings = {"develop", "v1.0.0"})
    void invalidSemver(String semver) {
        assertThrows(IllegalArgumentException.class, () -> Validator.validateSemver(semver));
    }
}