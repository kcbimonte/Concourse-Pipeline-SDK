package org.concourseci.sdk;

import org.concourseci.sdk.job.Job;
import org.concourseci.sdk.util.Validator;

import java.util.HashSet;
import java.util.Set;

/**
 * Groups have no functional effect on your pipeline. They are purely for making it easier to grok large pipelines
 * in the web UI.
 * <p>
 * Note: once you have added groups to your pipeline, all {@link Job}s must be in a group.
 */
public class Group {
    private final String name;

    private final Set<String> jobs = new HashSet<>();

    /**
     * Creates a new group given a valid name.
     *
     * @see Validator#validateIdentifier(String)
     * @param name A unique name for the group. This should be short and simple as
     *             it will be used as the tab name for navigation.
     */
    public Group(String name) {
        Validator.validateIdentifier(name);

        this.name = name;
    }

    /**
     * Adds a singular Job to the group.
     * <p>
     * Note: A Job may appear in multiple Groups.
     *
     * @param job A singular Job to add to the Group
     * @return Itself to support chaining
     */
    public Group addJob(Job job) {
        this.jobs.add(job.getName());

        return this;
    }

    /**
     * Adds a valid glob pattern in order to represent several jobs.
     * <p>
     * Note: A Job may appear in multiple Groups.
     *
     * @param jobFilter A Valid Glob pattern
     * @return Itself to support chaining
     */
    public Group addJobFilter(String jobFilter) {
        this.jobs.add(jobFilter);

        return this;
    }
}
