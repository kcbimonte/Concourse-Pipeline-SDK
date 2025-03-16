package org.concourseci.bundled;

import lombok.Getter;

@Getter
public enum Bundled {
    BOSH_IO_RELEASE("bosh-io-release"),
    BOSH_IO_STEMCELL("bosh-io-stemcell"),
    DOCKER_IMAGE("docker-image"),
    GIT("git"),
    GITHUB_RELEASE("github-release"),
    HG("hg"),
    MOCK("mock"),
    POOL("pool"),
    REGISTRY_IMAGE("registry-image"),
    S3("s3"),
    SEMVER("semver"),
    TIME("time"),
    @Deprecated
    TRACKER("tracker");

    private final String typeName;

    Bundled(String typeName) {
        this.typeName = typeName;
    }
}
