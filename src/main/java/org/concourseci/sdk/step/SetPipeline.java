package org.concourseci.sdk.step;

import com.google.gson.annotations.SerializedName;
import org.concourseci.sdk.resource.Get;
import org.concourseci.sdk.util.Validator;

public class SetPipeline implements IStep {

    @SerializedName("set_pipeline")
    private final String name;

    private final String file;

    private String team = null;

    private SetPipeline(String name, String file) {
        Validator.validateIdentifier(name);

        this.name = name;
        this.file = file;
    }

    public static SetPipeline create(String name, Get repo, String path) {
        Validator.validateIdentifier(name);

        if (path != null && path.startsWith("/")) {
            path = path.trim().substring(1);
        }

        String file = String.format("%s/%s", repo.getIdentifier(), path);

        return new SetPipeline(name, file);
    }

    public SetPipeline setTeam(String team) {
        Validator.validateIdentifier(team);

        this.team = team;

        return this;
    }
}
