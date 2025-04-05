package com.kevinbimonte.concourse.sdk.step.task;

import lombok.Getter;

@Getter
public class InputMapping implements ITaskMapping {


    private final String name;
    private final String mappedName;

    InputMapping(String name, String mappedName) {
        this.name = name;
        this.mappedName = mappedName;
    }

}
