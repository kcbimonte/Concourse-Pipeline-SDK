package com.kevinbimonte.concourse.bundled.tracker;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

@Deprecated
public class TrackerResourceType extends ResourceType {
    private static TrackerResourceType type = null;

    private TrackerResourceType(String name) {
        super(name);
    }


    public static TrackerResourceType getInstance() {
        if (type == null) {
            type = new TrackerResourceType(Bundled.TRACKER.getTypeName());
        }

        return type;
    }
}
