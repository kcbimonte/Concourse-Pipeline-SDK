package org.concourseci.bundled.time;

import org.concourseci.bundled.Bundled;
import org.concourseci.sdk.resource.ResourceType;

public class TimeResourceType extends ResourceType {
    private static TimeResourceType type = null;

    private TimeResourceType(String name) {
        super(name);
    }


    public static TimeResourceType getInstance() {
        if (type == null) {
            type = new TimeResourceType(Bundled.TIME.getTypeName());
        }

        return type;
    }
}
