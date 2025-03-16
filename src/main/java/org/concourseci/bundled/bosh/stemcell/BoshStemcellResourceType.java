package org.concourseci.bundled.bosh.stemcell;

import org.concourseci.bundled.Bundled;
import org.concourseci.sdk.resource.ResourceType;

public class BoshStemcellResourceType extends ResourceType {
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
}
