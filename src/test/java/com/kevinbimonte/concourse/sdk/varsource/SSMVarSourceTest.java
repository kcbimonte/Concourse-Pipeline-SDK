package com.kevinbimonte.concourse.sdk.varsource;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SSMVarSourceTest {
    @Test
    void basicTest() {
        // Arrange

        // Act
        SSMVarSource varSource = SSMVarSource.create("ssm", "us-east-1");

        // Assert
        assertEquals(VarSourceType.SSM, varSource.getType());
        assertEquals("us-east-1", varSource.getConfig().region());
    }

    @Test
    void nullRegion() {
        assertThrows(IllegalArgumentException.class, () -> SSMVarSource.create("ssm", null));
    }
}