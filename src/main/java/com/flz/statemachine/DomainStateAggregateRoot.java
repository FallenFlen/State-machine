package com.flz.statemachine;

public abstract class DomainStateAggregateRoot<T extends Enum, S extends AbstractStateMachine> {
    protected T state;
    protected S stateMachine;

    public void setState(T state) {
        this.state = state;
    }

    public T getState() {
        return state;
    }
}
