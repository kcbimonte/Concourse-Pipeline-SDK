package com.kevinbimonte.concourse.bundled.semver;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SwiftDriverConfig extends AbstractSemverDriverConfig<SwiftDriverConfig> {

    private final Openstack openstack;

    private SwiftDriverConfig(Openstack openstack) {
        super(SemVerDriver.SWIFT);

        this.openstack = openstack;
    }

    /**
     * Creates Swift Driver Configuration
     *
     * @param container The name of the container
     * @param itemName  The item name to use for the object in the container tracking the version
     * @param region    The region the container is in
     * @return The SwiftDriverConfig
     */
    public static SwiftDriverConfig create(String container, String itemName, String region) {
        Openstack openstack = Openstack.create(container, itemName, region);

        return new SwiftDriverConfig(openstack);
    }


    @Override
    protected SwiftDriverConfig getSelf() {
        return this;
    }

    @Setter
    @Getter
    private static class Openstack {
        private final String container;

        @SerializedName("item_name")
        private final String itemName;

        private final String region;

        @SerializedName("identity_endpoint")
        private String identityEndpoint;

        @SerializedName("username")
        private String username;

        @SerializedName("user_id")
        private String userID;

        @SerializedName("password")
        private String password;

        @SerializedName("api_key")
        private String apiKey;

        @SerializedName("domain_id")
        private String domainID;

        @SerializedName("domain_name")
        private String domainName;

        @SerializedName("tenant_id")
        private String tenantId;

        @SerializedName("tenant_name")
        private String tenantName;

        @Setter(AccessLevel.NONE)
        @SerializedName("allow_reauth")
        private Boolean allowReAuth;

        @SerializedName("token_id")
        private String tokenId;

        private Openstack(String container, String itemName, String region) {
            this.container = container;
            this.itemName = itemName;
            this.region = region;
        }

        /**
         * Creates the OpenStack configuration given the required values
         *
         * @param container The name of the container
         * @param itemName  The item name to use for the object in the container tracking the version
         * @param region    The region the container is in
         * @return The OpenStack base configuration
         */
        static Openstack create(String container, String itemName, String region) {
            return new Openstack(container, itemName, region);
        }

        /**
         * AllowReauth should be set to true if you grant permission for Gophercloud to
         * cache your credentials in memory, and to allow Gophercloud to attempt to
         * re-authenticate automatically if/when your token expires.  If you set it to
         * false, it will not cache these settings, but re-authentication will not be
         * possible.  This setting defaults to false.
         *
         * @implNote The reauth function will try to re-authenticate endlessly if left unchecked.
         * The way to limit the number of attempts is to provide a custom HTTP client to the provider client
         * and provide a transport that implements the RoundTripper interface and stores the number of failed retries.
         */
        public void allowReAuth() {
            this.allowReAuth = true;
        }
    }
}
