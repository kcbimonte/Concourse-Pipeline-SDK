package org.concourseci.sdk.resource;

import com.google.gson.annotations.SerializedName;
import org.concourseci.bundled.registry.RegistryImageConfig;
import org.concourseci.bundled.registry.RegistryImageResourceType;

/**
 * An Anonymous Resource Type is used for specifying container images for running tasks
 * through the {@link org.concourseci.sdk.step.task.config.TaskConfig}
 */
public class AnonymousResource {
    @SerializedName("type")
    private final String resourceType;

    @SerializedName("source")
    private final IResourceConfig config;

    /**
     * Creates an Anonymous Resource given the Resource Type and corresponding Resource Configuration
     *
     * @param type Resource Type. Note: If not a bundled resource type, then the type needs to be added to the Pipeline
     *             Resource Types array.
     * @param config Configuration for the Resource Type
     */
    private AnonymousResource(ResourceType type, IResourceConfig config) {
        this.resourceType = type.getName();
        this.config = config;
    }

    /**
     * Creates an Anonymous Resource of type Registry Image given the repository.
     *
     * @param repository Repository of the Registry Image
     * @return An Anonymous Resource of type Registry Image targeting the specified repository
     */
    public static AnonymousResource create(String repository) {
        return AnonymousResource.create(repository, null);
    }

    /**
     * Creates an Anonymous Resource of type Registry Image given the repository and tag.
     *
     * @param repository Repository of the Registry Image
     * @param tag Tag of the Registry Image
     * @return An Anonymous Resource of type Registry Image targeting the specified repository and tag
     */
    public static AnonymousResource create(String repository, String tag) {
        return AnonymousResource.create(RegistryImageResourceType.getInstance(), RegistryImageConfig.create(repository, tag));
    }

    /**
     * Creates an Anonymous Resource of type Registry Image given the Registry Image Config.
     * <p>
     * Used to have greater control of the configuration of the Registry Image Anonymous Resource.
     *
     * @param config Registry Image Configuration
     * @return An Anonymous Resource of type Registry Image targeting the Registry Image Configuration
     */
    public static AnonymousResource create(RegistryImageConfig config) {
        return AnonymousResource.create(RegistryImageResourceType.getInstance(), config);
    }

    /**
     * Creates an Anonymous Resource given the Resource Type and corresponding Resource Configuration
     *
     * @param type Resource Type. Note: If not a bundled resource type, then the type needs to be added to the Pipeline
     *             Resource Types array.
     * @param config Configuration for the Resource Type
     * @return An Anonymous Resource of specified type and config
     */
    public static AnonymousResource create(ResourceType type, IResourceConfig config) {
        return new AnonymousResource(type, config);
    }
}
