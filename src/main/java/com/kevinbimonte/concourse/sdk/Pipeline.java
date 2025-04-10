package com.kevinbimonte.concourse.sdk;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kevinbimonte.concourse.sdk.job.Job;
import com.kevinbimonte.concourse.sdk.resource.Resource;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;
import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A pipeline is the result of configuring {@link Job}s and {@link Resource}s together. When you configure a pipeline,
 * it takes on a life of its own, to continuously detect resource versions and automatically queue new builds
 * for jobs as they have new available inputs.
 */
@Getter
public class Pipeline {

    private Set<ResourceType> resourceTypes;
    private Set<Resource> resources;
    private Set<Group> groups;
    private Set<Job> jobs;

    /**
     * Adds a Job to the list of Pipeline Jobs. Each Job must have a unique name.
     * <p>
     * At least one job is required for a pipeline to be valid.
     *
     * @param job {@link Job} to add to the pipeline
     * @return itself to support chaining
     */
    public Pipeline addJob(Job job) {
        if (this.jobs == null) {
            this.jobs = new LinkedHashSet<>();
        }

        jobs.add(job);

        return this;
    }

    /**
     * Adds a new Resource Type to the list of Pipeline Resource Types.
     * <p>
     * Resource Types found in {@link com.kevinbimonte.concourse.bundled} are included by default
     *
     * @param resourceType {@link ResourceType} to add to the pipeline
     * @return itself to support chaining
     */
    public Pipeline addResourceType(ResourceType resourceType) {
        if (this.resourceTypes == null) {
            this.resourceTypes = new LinkedHashSet<>();
        }

        resourceTypes.add(resourceType);

        return this;
    }

    /**
     * Adds a new Resource for the pipeline to continuously check to the list of Pipeline Resources.
     * <p>
     * If the resource is not part of the bundled resources, the type must be added as well.
     *
     * @param resource {@link Resource} to add to the pipeline
     * @return itself to support chaining
     */
    public Pipeline addResource(Resource resource) {
        if (this.resources == null) {
            this.resources = new LinkedHashSet<>();
        }

        resources.add(resource);

        return this;
    }

    /**
     * Adds a new Group to the list of Pipeline Groups.
     * <p>
     * Groups have no functional effect on your pipeline. They are purely for making it easier to grok large pipelines in the web UI.
     * <p>
     * Note: once you have added groups to your pipeline, all jobs must be in a group.
     *
     * @param group {@link Group} to add to the pipeline
     * @return itself to support chaining
     */
    public Pipeline addGroup(Group group) {
        if (this.groups == null) {
            this.groups = new LinkedHashSet<>();
        }

        groups.add(group);

        return this;
    }

    /**
     * Renders the pipeline to JSON.
     *
     * @return rendered JSON Pipeline
     */
    public String render() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(this);
    }
}
