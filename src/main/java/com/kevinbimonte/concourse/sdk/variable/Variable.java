package com.kevinbimonte.concourse.sdk.variable;

public class Variable {
    public static String referenceVariable(String... variable) {
        String argPath = String.format(".%s".repeat(variable.length), (Object[]) variable);

        return String.format("((%s))", argPath.substring(1));
    }
}
