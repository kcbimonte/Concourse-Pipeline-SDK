package com.kevinbimonte.concourse.sdk.varsource.vault;

import com.kevinbimonte.concourse.sdk.variable.Variable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class VaultVarSourceTest {

    @Test
    void setCaCert() {
        // Arrange
        VaultVarConfig config = VaultVarConfig.create("https://vault.company.com");

        // Act
        config.setCaCert(Variable.referenceVariable("vault", "ca_cert"));

        // Assert
        assertEquals("((vault.ca_cert))", config.getCaCert());
    }

    @Test
    void setPathPrefix() {
        // Arrange
        VaultVarConfig config = VaultVarConfig.create("https://vault.company.com");

        // Act
        config.setPathPrefix("/my_prefix");

        // Assert
        assertEquals("/my_prefix", config.getPathPrefix());
    }

    @Test
    void addLookupPaths() {
        // Arrange
        VaultVarConfig config = VaultVarConfig.create("https://vault.company.com");

        // Act
        config.addLookupTemplate("/{{.Team}}/concourse/{{.Pipeline}}/{{.Secret}}")
                .addLookupTemplate("/{{.Team}}/concourse/{{.Secret}}");

        // Assert
        assertEquals(2, config.getLookupTemplates().size());
        assertTrue(config.getLookupTemplates().contains("/{{.Team}}/concourse/{{.Secret}}"));
    }

    @Test
    void setSharedPath() {
        // Arrange
        VaultVarConfig config = VaultVarConfig.create("https://vault.company.com");

        // Act
        config.setSharedPath("/common");

        // Assert
        assertEquals("/common", config.getSharedPath());
    }

    @Test
    void setNamespace() {
        // Arrange
        VaultVarConfig config = VaultVarConfig.create("https://vault.company.com");

        // Act
        config.setNamespace("namespace");

        // Assert
        assertEquals("namespace", config.getNamespace());
    }

    @Test
    void setClientCert() {
        // Arrange
        VaultVarConfig config = VaultVarConfig.create("https://vault.company.com");

        // Act
        config.setClientCert(Variable.referenceVariable("vault", "client_cert"));

        // Assert
        assertEquals("((vault.client_cert))", config.getClientCert());
    }

    @Test
    void setClientKey() {
        // Arrange
        VaultVarConfig config = VaultVarConfig.create("https://vault.company.com");

        // Act
        config.setClientKey(Variable.referenceVariable("vault", "client_key"));

        // Assert
        assertEquals("((vault.client_key))", config.getClientKey());
    }

    @Test
    void setServerName() {
        // Arrange
        VaultVarConfig config = VaultVarConfig.create("https://vault.company.com");

        // Act
        config.setServerName("the_vault");

        // Assert
        assertEquals("the_vault", config.getServerName());
    }

    @Test
    void markInsecure() {
        // Arrange
        VaultVarConfig config = VaultVarConfig.create("https://vault.company.com");

        // Act
        config.markInsecure();

        // Assert
        assertTrue(config.getInsecureSkipVerify());
    }

    @Test
    void setClientToken() {
        // Arrange
        VaultVarConfig config = VaultVarConfig.create("https://vault.company.com");

        // Act
        config.setClientToken(Variable.referenceVariable("vault", "client_token"));

        // Assert
        assertEquals("((vault.client_token))", config.getClientToken());
    }

    @Test
    void setAuthBackend() {
        // Arrange
        VaultVarConfig config = VaultVarConfig.create("https://vault.company.com");

        // Act
        config.setAuthBackend("approle");

        // Assert
        assertEquals("approle", config.getAuthBackend());
    }

    @Test
    void addAuthParams() {
        // Arrange
        VaultVarConfig config = VaultVarConfig.create("https://vault.company.com");

        // Act
        config.addAuthParam("role_id", "my_id").addAuthParam("secret_id", "my_secret_id");

        // Assert
        assertEquals(2, config.getAuthParams().size());
        assertTrue(config.getAuthParams().containsKey("role_id"));
        assertTrue(config.getAuthParams().containsKey("secret_id"));
        assertTrue(config.getAuthParams().containsValue("my_secret_id"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1ms", "1s", "1m", "1h", "1d", "1w", "1y", "1y1m100s"})
    void validAuthDurations(String duration) {
        // Arrange
        VaultVarConfig config;

        // Act
        config = assertDoesNotThrow(() -> VaultVarConfig.create("https://vault.company.com")
                .setAuthMaxTTL(duration)
                .setAuthRetryMax(duration)
                .setAuthRetryInitial(duration)
        );
        // Assert
        assertEquals(duration, config.getAuthMaxTTL());
        assertEquals(duration, config.getAuthRetryMax());
        assertEquals(duration, config.getAuthRetryInitial());
    }

    @ParameterizedTest
    @ValueSource(strings = {"123_job", "1m1y", "1d1w"})
    void invalidAuthMaxTTL(String duration) {
        // Arrange
        VaultVarConfig config = VaultVarConfig.create("https://vault.company.com");

        assertThrows(IllegalArgumentException.class, () -> config.setAuthMaxTTL(duration));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123_job", "1m1y", "1d1w"})
    void invalidAuthRetryMax(String duration) {
        // Arrange
        VaultVarConfig config = VaultVarConfig.create("https://vault.company.com");

        assertThrows(IllegalArgumentException.class, () -> config.setAuthRetryMax(duration));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123_job", "1m1y", "1d1w"})
    void invalidAuthRetryInitial(String duration) {
        // Arrange
        VaultVarConfig config = VaultVarConfig.create("https://vault.company.com");

        assertThrows(IllegalArgumentException.class, () -> config.setAuthRetryInitial(duration));
    }
}