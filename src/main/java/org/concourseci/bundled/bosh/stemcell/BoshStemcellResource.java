package org.concourseci.bundled.bosh.stemcell;

import org.concourseci.sdk.resource.Resource;

public class BoshStemcellResource extends Resource {
    protected BoshStemcellResource(String name, BoshStemcellResourceType type, BoshStemcellConfig config) {
        super(name, type, config);
    }

    public static BoshStemcellResource createResource(String name, BoshStemcellConfig config) {
        BoshStemcellResourceType type = BoshStemcellResourceType.getInstance();

        return new BoshStemcellResource(name, type, config);
    }
}
