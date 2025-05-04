package com.kevinbimonte.concourse.sdk.step.task;

@SuppressWarnings("LombokGetterMayBeUsed")
public class InputMapping implements ITaskMapping {

    private final String name;
    private final String mappedName;

    InputMapping(String name, String mappedName) {
        this.name = name;
        this.mappedName = mappedName;
    }

    /**
     * The name that is available within the task.
     *
     * @return Input.name
     */
    public String getName() {
        return this.name;
    }

    /**
     * The mapped name from the plan context. This name comes from Get or Output
     *
     * @return The identifier from Get or Output
     */
    public String getMappedName() {
        return this.mappedName;
    }
}
