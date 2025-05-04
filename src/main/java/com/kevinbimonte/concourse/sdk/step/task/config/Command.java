package com.kevinbimonte.concourse.sdk.step.task.config;

import com.kevinbimonte.concourse.sdk.resource.get.Get;

import java.util.ArrayList;
import java.util.List;

public class Command {
    private final String path;

    private List<String> args;

    private String dir;

    private String user;

    private Command(String path) {
        this.path = path;
    }

    public Command addArg(String arg) {
        if (this.args == null) {
            this.args = new ArrayList<>();
        }

        this.args.add(arg);

        return this;
    }

    public static Command createCommand(String path) {
        return new Command(path);
    }

    public static Command createCommand(Get get, String path) {
        if (path != null && path.startsWith("/")) {
            path = path.trim().substring(1);
        }

        path = String.format("%s/%s", get.getIdentifier(), path);

        return new Command(path);
    }

    public Command setUser(String user) {
        this.user = user;

        return this;
    }

    public Command setWorkingDirectory(String directory) {
        this.dir = directory;

        return this;
    }
}
