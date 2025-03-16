package org.concourseci.sdk;

import lombok.Getter;
import org.concourseci.sdk.resource.Resource;
import org.concourseci.sdk.resource.ResourceType;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Pipeline {

    private final Set<ResourceType> resourceTypes = new HashSet<>();
    private final Set<Resource> resources = new HashSet<>();
    private final Set<Group> groups = new HashSet<>();
    private final Set<Job> jobs = new HashSet<>();

    public Pipeline addJob(Job job) {
        jobs.add(job);

        return this;
    }

    public Pipeline addResourceType(ResourceType resourceType) {
        resourceTypes.add(resourceType);

        return this;
    }

    public Pipeline addResource(Resource resource) {
        resources.add(resource);

        return this;
    }

    public Pipeline addGroup(Group group) {
        groups.add(group);

        return this;
    }
}
