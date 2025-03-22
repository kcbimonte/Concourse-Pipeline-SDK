package org.concourseci.sdk.step.var;

import com.google.gson.annotations.SerializedName;
import org.concourseci.sdk.resource.get.Get;
import org.concourseci.sdk.step.IStep;
import org.concourseci.sdk.util.Validator;

import java.util.List;

public class LoadVar implements IStep {

    @SerializedName("load_var")
    private final String variableName;

    @SerializedName("file-path")
    private final String filePath;

    private final VarFormat format;

    private final Boolean reveal;


    public LoadVar(String variableName, Get get, String path, VarFormat format) {
        this(variableName, get, path, format, false);
    }

    public LoadVar(String variableName, Get get, String path, VarFormat format, Boolean reveal) {
        Validator.validateIdentifier(variableName);

        this.variableName = variableName;

        if (path != null && path.startsWith("/")) {
            path = path.trim().substring(1);
        }

        this.filePath = String.format("%s/%s", get.getIdentifier(), path);
        this.format = format;
        this.reveal = reveal;
    }

    public String getLocalVariable() {
        return String.format("((.:%s))", this.variableName);
    }

    public String getNestedLocalVariable(String ... variable) {
        if (!List.of(VarFormat.YAML, VarFormat.YML, VarFormat.JSON).contains(this.format))
            throw new RuntimeException("Nested Variables can only be traversed when format is YAML, YML, or JSON");

        String argPath = String.format(".%s".repeat(variable.length), (Object[]) variable);

        return String.format("((.:%s%s))", this.variableName, argPath);
    }
}
