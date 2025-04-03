package com.kevinbimonte.concourse.bundled.bosh.stemcell;

import com.kevinbimonte.concourse.sdk.resource.Resource;

public class BoshStemcellResource extends Resource {
    protected BoshStemcellResource(String name, BoshStemcellResourceType type, BoshStemcellConfig config) {
        super(name, type, config);
    }

    public static BoshStemcellResource createResource(String name, BoshStemcellConfig config) {
        BoshStemcellResourceType type = BoshStemcellResourceType.getInstance();

        return new BoshStemcellResource(name, type, config);
    }


}
