package org.concourseci.sdk.step.modifier;

import org.concourseci.sdk.step.IStep;

interface ITimeoutModifier<T extends IStep> {
    T setTimeout(String duration);
}
