package com.kevinbimonte.concourse.sdk.util;

import com.kevinbimonte.concourse.sdk.step.AbstractAcrossStep;
import com.kevinbimonte.concourse.sdk.step.IStep;
import com.kevinbimonte.concourse.sdk.step.SetPipeline;
import com.kevinbimonte.concourse.sdk.step.task.Task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static void validateIdentifier(String identifier) throws RuntimeException {
        Pattern pattern = Pattern.compile("^[\\p{Ll}\\p{Lt}\\p{Lm}\\p{Lo}][\\p{Ll}\\p{Lt}\\p{Lm}\\p{Lo}\\d\\-_.]*");
        Matcher matcher = pattern.matcher(identifier);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Not valid identifier: " + identifier);
        }
    }

    public static void validateIdentifier(String identifier, AbstractAcrossStep<?> a) {
        if ((a instanceof SetPipeline || a instanceof Task) && a.getAcross() != null && !a.getAcross().isEmpty()) {
            return;
        }
        Validator.validateIdentifier(identifier);
    }

    public static void validateDuration(String duration) throws RuntimeException {
        Pattern pattern = Pattern.compile("^(?:(\\d+)y)?(?:(\\d+)w)?(?:(\\d+)d)?(?:(\\d+)h)?(?:(\\d+)m)?(?:(\\d+)s)?(?:(\\d+)ms)?$");
        Matcher matcher = pattern.matcher(duration);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid duration format: " + duration);
        }
    }

    public static void validateSemver(String semver) throws RuntimeException {
        Pattern pattern = Pattern.compile("^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)(?:-((?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\\.(?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?(?:\\+([0-9a-zA-Z-]+(?:\\.[0-9a-zA-Z-]+)*))?$");
        Matcher matcher = pattern.matcher(semver);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid semver format: " + semver);
        }
    }

}
