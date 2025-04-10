package com.kevinbimonte.concourse.sdk.step;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.sdk.resource.get.Get;
import com.kevinbimonte.concourse.sdk.util.Validator;

import java.util.LinkedHashSet;
import java.util.Set;

public class SetPipeline extends AbstractStep<SetPipeline> implements IStep {

    @SerializedName("set_pipeline")
    private final String name;

    private final String file;

    private String team;

    @SerializedName("var_files")
    private Set<String> varFiles;

    private JsonObject vars;

    @SerializedName("instance_vars")
    private JsonObject instanceVars;

    private SetPipeline(String name, String file) {
        this.name = name;
        this.file = file;
    }

    public static SetPipeline create(String name, String path) {
        Validator.validateIdentifier(name);

        if (path != null && path.startsWith("/")) {
            path = path.trim().substring(1);
        }

        String file = String.format("%s", path);

        return new SetPipeline(name, file);
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

    public SetPipeline addVarFile(Get repo, String path) {
        if (varFiles == null) {
            varFiles = new LinkedHashSet<>();
        }

        if (path != null && path.startsWith("/")) {
            path = path.trim().substring(1);
        }

        String file = String.format("%s/%s", repo.getIdentifier(), path);

        this.varFiles.add(file);

        return this;
    }

    public SetPipeline addVar(String key, String value) {
        if (vars == null) vars = new JsonObject();

        vars.addProperty(key, value);

        return this;
    }

    public SetPipeline addVar(String key, JsonObject value) {
        if (vars == null) vars = new JsonObject();

        vars.add(key, value);

        return this;
    }

    public SetPipeline addInstanceVar(String key, String value) {
        if (instanceVars == null) instanceVars = new JsonObject();

        instanceVars.addProperty(key, value);

        return this;
    }

    public SetPipeline addInstanceVar(String key, JsonObject value) {
        if (instanceVars == null) instanceVars = new JsonObject();

        instanceVars.add(key, value);

        return this;
    }

    @Override
    protected SetPipeline getSelf() {
        return this;
    }
}
