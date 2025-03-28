package org.concourseci.sdk.step.modifier;

import org.concourseci.sdk.step.IStep;

interface ITagsModifier<T extends IStep> {
    T addWorkerTag(String workerTag);
}
