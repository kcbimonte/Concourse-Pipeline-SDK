package org.concourseci.sdk.step.modifier;

import org.concourseci.sdk.step.IStep;

interface IAttemptsModifier<T extends IStep> {
    T setAttempts(Integer attempts);
}
