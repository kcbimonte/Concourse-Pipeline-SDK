package com.kevinbimonte.concourse.sdk.resource.get;

import com.kevinbimonte.concourse.sdk.job.Job;
import com.kevinbimonte.concourse.sdk.resource.Resource;

public class NoOpGet extends Get {
    public NoOpGet(Resource resource) {
        super(resource);
    }

    public NoOpGet(Resource resource, String name) {
        super(resource, name);
    }

    @Override
    public Get addPassedRequirement(Job job) {
        throw new UnsupportedOperationException("No-Op Get cannot have passed requirements");
    }

    @Override
    public Get enableTrigger() {
        throw new UnsupportedOperationException("No-Op Get cannot have a trigger");
    }
}
