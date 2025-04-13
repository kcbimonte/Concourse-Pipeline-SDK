package com.kevinbimonte.concourse.sdk.varsource.vault;

import com.kevinbimonte.concourse.sdk.varsource.VarSourceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VaultVarSourceTest {
    @Test
    void createValidVarSource() {
        // Arrange

        // Act
        VaultVarSource source = VaultVarSource.create("vault", "https://vault.concourse-ci.org");

        // Assert
        assertEquals("vault.concourse-ci.org", source.getConfig().getUrl().getHost());
        assertEquals("vault", source.getName());
        assertEquals(VarSourceType.VAULT, source.getType());
    }

    @Test
    void createInvalidVarSource() {
        // Assert
        IllegalArgumentException vault = assertThrows(IllegalArgumentException.class, () -> VaultVarSource.create("vault", null));

        assertEquals("URL cannot be null", vault.getMessage());
    }

    @Test
    void createInvalidNamedVarSource() {
        // Assert
        IllegalArgumentException vault = assertThrows(IllegalArgumentException.class, () -> VaultVarSource.create("VAULT", null));

        assertEquals("Not valid identifier: VAULT", vault.getMessage());
    }
}