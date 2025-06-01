package com.kevinbimonte.concourse.bundled.bosh.stemcell;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageConfig;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageResourceType;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

public class BoshStemcellResourceType extends ResourceType<BoshStemcellResourceType, BoshStemcellConfig> {

    private BoshStemcellResourceType(String name) {
        super(name);
    }

    public BoshStemcellResourceType(String name, RegistryImageConfig config) {
        super(name, RegistryImageResourceType.create(), config);
    }

    public static BoshStemcellResourceType create() {
        return new BoshStemcellResourceType(Bundled.BOSH_IO_STEMCELL.getTypeName());
    }

    public static BoshStemcellResourceType create(RegistryImageConfig config) {
        return new BoshStemcellResourceType(Bundled.BOSH_IO_STEMCELL.getTypeName(), config);
    }

    @Override
    protected BoshStemcellResourceType getSelf() {
        return this;
    }
}
