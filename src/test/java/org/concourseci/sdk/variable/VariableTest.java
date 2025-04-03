package org.concourseci.sdk.variable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VariableTest {
    @Test
    void singleVariable() {
        // Arrange

        // Act
        String var = Variable.referenceVariable("my_var");

        // Assert
        assertEquals("((my_var))", var);
    }

    @Test
    void subVariable() {
        // Arrange

        // Act
        String var = Variable.referenceVariable("my_var", "sub_var", "sub_sub_var");

        // Assert
        assertEquals("((my_var.sub_var.sub_sub_var))", var);
    }
}