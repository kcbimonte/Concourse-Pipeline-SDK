package com.kevinbimonte.concourse.sdk.varsource.dummy;

import com.google.gson.JsonObject;
import com.kevinbimonte.concourse.sdk.varsource.VarSourceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DummyVarSourceTest {
    @Test
    void basicTest() {
        // Arrange
        DummyVarSource varSource = DummyVarSource.create("ssm");

        // Act
        varSource.getConfig().addVar("key", "value");

        // Assert
        Assertions.assertEquals(VarSourceType.DUMMY, varSource.getType());

        assertTrue(varSource.getConfig().getVars().has("key"));
        assertEquals("value", varSource.getConfig().getVars().getAsJsonPrimitive("key").getAsString());
    }

    @Test
    void addMultipleVars() {
        // Arrange
        DummyVarSource varSource = DummyVarSource.create("ssm");

        // Act
        JsonObject object = new JsonObject();
        object.addProperty("key", "value");
        varSource.getConfig().addVar("key", "value").addVar("new", object);

        // Assert
        assertEquals(VarSourceType.DUMMY, varSource.getType());

        assertTrue(varSource.getConfig().getVars().has("key"));
        assertEquals("value", varSource.getConfig().getVars().getAsJsonPrimitive("key").getAsString());
        assertEquals("value", varSource.getConfig().getVars().getAsJsonObject("new").getAsJsonPrimitive("key").getAsString());
    }

}