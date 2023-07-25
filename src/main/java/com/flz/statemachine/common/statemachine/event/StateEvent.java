package com.flz.statemachine.common.statemachine.event;

import com.flz.statemachine.domain.aggregate.DomainStateAggregateRoot;

public interface StateEvent<T extends DomainStateAggregateRoot> {
    void execute(T domainObject);
}
