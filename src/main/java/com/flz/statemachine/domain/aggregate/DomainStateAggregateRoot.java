package com.flz.statemachine.domain.aggregate;

import com.flz.statemachine.common.statemachine.AbstractStateMachine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public abstract class DomainStateAggregateRoot<T extends Enum, A extends Enum, S extends AbstractStateMachine> {
    protected T state;

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
