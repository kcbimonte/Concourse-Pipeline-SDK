package org.concourseci.sdk.step.task;

import org.concourseci.sdk.resource.Get;

import java.util.ArrayList;
import java.util.List;

public class Command {
    private final String path;

    private final List<String> args = new ArrayList<>();

    private Command(String path) {
        this.path = path;
    }

    public Command addArg(String arg) {
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
}
