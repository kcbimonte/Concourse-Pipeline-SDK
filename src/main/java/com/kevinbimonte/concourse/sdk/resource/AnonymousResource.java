package com.kevinbimonte.concourse.sdk.resource;

import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageConfig;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageResourceType;
import com.kevinbimonte.concourse.sdk.step.task.config.TaskConfig;
import lombok.Getter;

/**
 * An Anonymous Resource Type is used for specifying container images for running tasks
 * through the {@link TaskConfig}
 */
@Getter
public class AnonymousResource<T extends IResourceConfig> {
    @SerializedName("type")
    private final String resourceType;

    @SerializedName("source")
    private final T config;

    /**
     * Creates an Anonymous Resource given the Resource Type and corresponding Resource Configuration
     *
     * @param type   Resource Type. Note: If not a bundled resource type, then the type needs to be added to the Pipeline
     *               Resource Types array.
     * @param config Configuration for the Resource Type
     */
    private AnonymousResource(ResourceType type, T config) {
        this.resourceType = type.getName();
        this.config = config;
    }

    /**
     * Creates an Anonymous Resource of type Registry Image given the repository.
     *
     * @param repository Repository of the Registry Image
     * @return An Anonymous Resource of type Registry Image targeting the specified repository
     */
    public static AnonymousResource<RegistryImageConfig> create(String repository) {
        return AnonymousResource.create(repository, null);
    }

    /**
     * Creates an Anonymous Resource of type Registry Image given the repository and tag.
     *
     * @param repository Repository of the Registry Image
     * @param tag        Tag of the Registry Image
     * @return An Anonymous Resource of type Registry Image targeting the specified repository and tag
     */
    public static AnonymousResource<RegistryImageConfig> create(String repository, String tag) {
        return AnonymousResource.create(RegistryImageConfig.create(repository, tag));
    }

    /**
     * Creates an Anonymous Resource of type Registry Image given the Registry Image Config.
     * <p>
     * Used to have greater control of the configuration of the Registry Image Anonymous Resource.
     *
     * @param config Registry Image Configuration
     * @return An Anonymous Resource of type Registry Image targeting the Registry Image Configuration
     */
    public static AnonymousResource<RegistryImageConfig> create(RegistryImageConfig config) {
        return AnonymousResource.create(RegistryImageResourceType.getInstance(), config);
    }

    /**
     * Creates an Anonymous Resource given the Resource Type and corresponding Resource Configuration
     *
     * @param type   Resource Type. Note: If not a bundled resource type, then the type needs to be added to the Pipeline
     *               Resource Types array.
     * @param config Configuration for the Resource Type
     * @return An Anonymous Resource of specified type and config
     */
    public static <T extends IResourceConfig> AnonymousResource<T> create(ResourceType type, T config) {
        return new AnonymousResource<>(type, config);
    }
}
