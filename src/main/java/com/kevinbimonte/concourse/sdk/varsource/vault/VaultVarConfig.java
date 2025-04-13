package com.kevinbimonte.concourse.sdk.varsource.vault;

import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.sdk.util.Validator;
import com.kevinbimonte.concourse.sdk.varsource.IVarSourceConfig;
import lombok.Getter;

import java.net.URI;
import java.util.*;

@Getter
public class VaultVarConfig implements IVarSourceConfig {
    private final URI url;

    @SerializedName("ca_cert")
    private String caCert;

    @SerializedName("path_prefix")
    private String pathPrefix;

    @SerializedName("lookup_templates")
    private Set<String> lookupTemplates;

    @SerializedName("shared_path")
    private String sharedPath;

    private String namespace;

    @SerializedName("client_cert")
    private String clientCert;

    @SerializedName("client_key")
    private String clientKey;

    @SerializedName("server_name")
    private String serverName;

    @SerializedName("insecure_skip_verify")
    private Boolean insecureSkipVerify;

    @SerializedName("client_token")
    private String clientToken;

    @SerializedName("auth_backend")
    private String authBackend;

    @SerializedName("auth_params")
    private Map<String, String> authParams;

    @SerializedName("auth_max_ttl")
    private String authMaxTTL;

    @SerializedName("auth_retry_max")
    private String authRetryMax;

    @SerializedName("auth_retry_initial")
    private String authRetryInitial;

    private VaultVarConfig(URI url) {
        this.url = url;
    }

    public static VaultVarConfig create(String url) {
        return new VaultVarConfig(URI.create(url));
    }

    public VaultVarConfig setCaCert(String caCert) {
        this.caCert = caCert;

        return this;
    }

    public VaultVarConfig setPathPrefix(String pathPrefix) {
        this.pathPrefix = pathPrefix;

        return this;
    }

    public VaultVarConfig addLookupTemplate(String lookupTemplate) {
        if (this.lookupTemplates == null) {
            this.lookupTemplates = new LinkedHashSet<>();
        }

        this.lookupTemplates.add(lookupTemplate);

        return this;
    }

    public VaultVarConfig setSharedPath(String sharedPath) {
        this.sharedPath = sharedPath;

        return this;
    }

    public VaultVarConfig setNamespace(String namespace) {
        this.namespace = namespace;

        return this;
    }

    public VaultVarConfig setClientCert(String clientCert) {
        this.clientCert = clientCert;

        return this;
    }

    public VaultVarConfig setClientKey(String clientKey) {
        this.clientKey = clientKey;

        return this;
    }

    public VaultVarConfig setServerName(String serverName) {
        this.serverName = serverName;

        return this;
    }

    public VaultVarConfig markInsecure() {
        this.insecureSkipVerify = true;

        return this;
    }

    public VaultVarConfig setClientToken(String clientToken) {
        this.clientToken = clientToken;

        return this;
    }

    public VaultVarConfig setAuthBackend(String authBackend) {
        this.authBackend = authBackend;

        return this;
    }

    public VaultVarConfig addAuthParam(String key, String value) {
        if (this.authParams == null) {
            this.authParams = new HashMap<>();
        }

        this.authParams.put(key, value);

        return this;
    }

    public VaultVarConfig setAuthMaxTTL(String duration) {
        Validator.validateDuration(duration);

        this.authMaxTTL = duration;

        return this;
    }

    public VaultVarConfig setAuthRetryMax(String duration) {
        Validator.validateDuration(duration);

        this.authRetryMax = duration;

        return this;
    }

    public VaultVarConfig setAuthRetryInitial(String duration) {
        Validator.validateDuration(duration);

        this.authRetryInitial = duration;

        return this;
    }
}
