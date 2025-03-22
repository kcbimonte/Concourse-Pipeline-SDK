package org.concourseci.sdk;

import org.concourseci.sdk.job.Job;
import org.concourseci.sdk.util.Validator;

import java.util.HashSet;
import java.util.Set;

public class Group {
    private final String name;

    private final Set<String> jobs = new HashSet<>();

    public Group(String name) {
        Validator.validateIdentifier(name);

        this.name = name;
    }

    public Group addJob(Job job) {
        this.jobs.add(job.getName());

        return this;
    }

    public Group addJobFilter(String jobFilter) {
        this.jobs.add(jobFilter);

        return this;
    }
}
