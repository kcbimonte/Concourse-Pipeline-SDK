package com.kevinbimonte.concourse.bundled.bosh.stemcell;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

public class BoshStemcellResourceType extends ResourceType<BoshStemcellResourceType, BoshStemcellConfig> {
    private static BoshStemcellResourceType type = null;

    private BoshStemcellResourceType(String name) {
        super(name);
    }

    public static BoshStemcellResourceType getInstance() {
        if (type == null) {
            type = new BoshStemcellResourceType(Bundled.BOSH_IO_STEMCELL.getTypeName());
        }

        return type;
    }

    @Override
    protected BoshStemcellResourceType getSelf() {
        return this;
    }
}
