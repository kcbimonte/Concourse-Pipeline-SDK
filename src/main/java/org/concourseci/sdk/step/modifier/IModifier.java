package org.concourseci.sdk.step.modifier;

import org.concourseci.sdk.step.IStep;

public interface IModifier<T extends IStep> extends IAcrossModifier<T>, IAttemptsModifier<T>, ITagsModifier<T>, ITimeoutModifier<T> {
}
