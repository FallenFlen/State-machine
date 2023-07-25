package com.flz.statemachine;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class DomainStateAggregateRoot<T extends Enum, A extends Enum, S extends AbstractStateMachine> {
    protected T state;

    public void setState(T state) {
        this.state = state;
    }

    public T getState() {
        return state;
    }

    protected abstract List<S> getStateMachines();

    public final void executeStateMachine(A action) {
        // 根据action去重
        Set<Enum> actions = new HashSet<>();
        getStateMachines().stream()
                .filter(it -> actions.add(it.getAction()))
                .filter(it -> it.getAction() == action)
                .forEach(it -> it.trigger(action, this));
    }
}
