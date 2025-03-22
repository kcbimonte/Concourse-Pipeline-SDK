package org.concourseci.bundled.git.get;

import com.google.gson.annotations.SerializedName;
import org.concourseci.sdk.resource.get.IGetConfig;

import java.util.List;

public class GitGetConfig implements IGetConfig {
    private Integer depth;

    @SerializedName("fetch_tags")
    private Boolean fetchTags;

    // submodules

    @SerializedName("submodule_recursive")
    private Boolean submoduleRecursive;

    @SerializedName("submodule_remote")
    private Boolean submoduleRemote;

    @SerializedName("disable_git_lfs")
    private Boolean disableGitLFS;

    @SerializedName("clean_tags")
    private Boolean cleanTags;

    @SerializedName("short_ref_format")
    private String shortRefFormat;

    @SerializedName("timestamp_format")
    private String timestampFormat;

    @SerializedName("describe_ref_options")
    private List<String> describeRefOptions;
}
