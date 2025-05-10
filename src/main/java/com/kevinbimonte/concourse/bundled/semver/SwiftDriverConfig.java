package com.kevinbimonte.concourse.bundled.semver;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SwiftDriverConfig extends AbstractSemverDriverConfig<SwiftDriverConfig> {

    private final OpenStack openstack;

    private SwiftDriverConfig(OpenStack openstack) {
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
        OpenStack openstack = OpenStack.create(container, itemName, region);

        return new SwiftDriverConfig(openstack);
    }

    /**
     * IdentityEndpoint specifies the HTTP endpoint that is required to work with
     * the Identity API of the appropriate version. While it's ultimately needed by
     * all the identity services, it will often be populated by a provider-level
     * function.
     *
     * @param endpoint Identity API Endpoint
     * @return self
     */
    public SwiftDriverConfig setIdentityEndpoint(String endpoint) {
        this.openstack.setIdentityEndpoint(endpoint);

        return this;
    }

    /**
     * Username is required if using Identity V2 API. Consult with your provider's
     * control panel to discover your account's username. In Identity V3, either
     * UserID or a combination of Username and DomainID or DomainName are needed.
     *
     * @param username The Username
     * @return self
     */
    public SwiftDriverConfig setUsername(String username) {
        this.openstack.setUsername(username);

        return this;
    }

    /**
     * In Identity V3, either UserID or a combination of Username and DomainID or DomainName are needed.
     *
     * @param userId The UserID
     * @return self
     */
    public SwiftDriverConfig setUserId(String userId) {
        this.openstack.setUserID(userId);

        return this;
    }

    /**
     * Exactly one of Password or APIKey is required for the Identity V2 and V3
     * APIs. Consult with your provider's control panel to discover your account's
     * preferred method of authentication.
     *
     * @param password The Password (should use variable templating with secrets manager)
     * @return self
     */
    public SwiftDriverConfig setPassword(String password) {
        if (this.openstack.getApiKey() != null) {
            throw new IllegalArgumentException("Cannot set both Password and API Key");
        }

        this.openstack.setPassword(password);

        return this;
    }

    /**
     * Exactly one of Password or APIKey is required for the Identity V2 and V3
     * APIs. Consult with your provider's control panel to discover your account's
     * preferred method of authentication.
     *
     * @param apiKey The API Key (should use variable templating with secrets manager)
     * @return self
     */
    public SwiftDriverConfig setApiKey(String apiKey) {
        if (this.openstack.getPassword() != null) {
            throw new IllegalArgumentException("Cannot set both Password and API Key");
        }

        this.openstack.setApiKey(apiKey);

        return this;
    }

    /**
     * At most one of DomainID and DomainName must be provided if using Username
     * with Identity V3. Otherwise, either are optional.
     *
     * @param domainId The Domain ID
     * @return self
     */
    public SwiftDriverConfig setDomainId(String domainId) {
        this.openstack.setDomainID(domainId);

        return this;
    }

    /**
     * At most one of DomainID and DomainName must be provided if using Username
     * with Identity V3. Otherwise, either are optional.
     *
     * @param domainName The Domain Name
     * @return self
     */
    public SwiftDriverConfig setDomainName(String domainName) {
        this.openstack.setDomainName(domainName);

        return this;
    }

    /**
     * The TenantID and TenantName fields are optional for the Identity V2 API.
     * Some providers allow you to specify a TenantName instead of the TenantId.
     * Some require both. Your provider's authentication policies will determine
     * how these fields influence authentication.
     *
     * @param tenantId The Tenant ID
     * @return self
     */
    public SwiftDriverConfig setTenantId(String tenantId) {
        this.openstack.setTenantId(tenantId);

        return this;
    }

    /**
     * The TenantID and TenantName fields are optional for the Identity V2 API.
     * Some providers allow you to specify a TenantName instead of the TenantId.
     * Some require both. Your provider's authentication policies will determine
     * how these fields influence authentication.
     *
     * @param tenantName The Tenant Name
     * @return self
     */
    public SwiftDriverConfig setTenantName(String tenantName) {
        this.openstack.setTenantName(tenantName);

        return this;
    }

    /**
     * AllowReauth should be set to true if you grant permission for Gophercloud to
     * cache your credentials in memory, and to allow Gophercloud to attempt to
     * re-authenticate automatically if/when your token expires.  If you set it to
     * false, it will not cache these settings, but re-authentication will not be
     * possible.  This setting defaults to false.
     *
     * @return self
     * @implNote The reauth function will try to re-authenticate endlessly if left unchecked.
     * The way to limit the number of attempts is to provide a custom HTTP client to the provider client
     * and provide a transport that implements the RoundTripper interface and stores the number of failed retries.
     */
    public SwiftDriverConfig allowReAuth() {
        this.openstack.allowReAuth();

        return this;
    }

    /**
     * TokenID allows users to authenticate (possibly as another user) with an
     * authentication token ID.
     *
     * @param tokenId The Token ID
     * @return self
     */
    public SwiftDriverConfig setTokenId(String tokenId) {
        this.openstack.setTokenId(tokenId);

        return this;
    }

    @Override
    protected SwiftDriverConfig getSelf() {
        return this;
    }

    @Setter
    @Getter
    private static class OpenStack {
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

        private OpenStack(String container, String itemName, String region) {
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
        static OpenStack create(String container, String itemName, String region) {
            return new OpenStack(container, itemName, region);
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
