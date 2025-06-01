package com.kevinbimonte.concourse.bundled.bosh.stemcell;

import com.kevinbimonte.concourse.sdk.resource.Resource;

public class BoshStemcellResource extends Resource {
    protected BoshStemcellResource(String name, BoshStemcellResourceType type, BoshStemcellConfig config) {
        super(name, type, config);
    }

    public static BoshStemcellResource create(String name, BoshStemcellConfig config) {
        BoshStemcellResourceType type = BoshStemcellResourceType.create();

        return new BoshStemcellResource(name, type, config);
    }

    public static BoshStemcellResource create(String name, BoshStemcellResourceType type, BoshStemcellConfig config) {
        return new BoshStemcellResource(name, type, config);
    }
}
