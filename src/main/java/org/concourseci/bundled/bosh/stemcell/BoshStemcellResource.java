package org.concourseci.bundled.bosh.stemcell;

import org.concourseci.sdk.resource.Resource;
import org.concourseci.sdk.resource.get.Get;
import org.concourseci.sdk.resource.put.Put;

public class BoshStemcellResource extends Resource {
    protected BoshStemcellResource(String name, BoshStemcellResourceType type, BoshStemcellConfig config) {
        super(name, type, config);
    }

    public static BoshStemcellResource createResource(String name, BoshStemcellConfig config) {
        BoshStemcellResourceType type = BoshStemcellResourceType.getInstance();

        return new BoshStemcellResource(name, type, config);
    }

    @Override
    public Get createGetDefinition() {
        return null;
    }

    @Override
    public Get createGetDefinition(String identifier) {
        return null;
    }

    @Override
    public Put createPutDefinition() {
        return null;
    }

    @Override
    public Put createPutDefinition(String identifier) {
        return null;
    }
}
