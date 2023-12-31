package com.flz.statemachine.common.statemachine;

import com.flz.statemachine.common.statemachine.event.StateEvent;
import com.flz.statemachine.domain.aggregate.DomainStateAggregateRoot;
import com.flz.statemachine.exception.StateMachineException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 状态机包含四大要素
 * 1.前置状态
 * 2.下一个状态
 * 3.触发状态流转的动作
 * 4.状态流转后触发的事件
 */
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractStateMachine<T extends Enum, A extends Enum> {
    protected Set<T> prevStates;
    protected T nextState;
    protected StateEvent event;
    protected A action;

    public final void trigger(A action, DomainStateAggregateRoot aggregateRoot) {
        // 非空校验
        Objects.requireNonNull(prevStates);
        Objects.requireNonNull(nextState);
        Objects.requireNonNull(aggregateRoot.getState());

        // 只有domain对象的action和状态机的action相同，才能用该domain对象执行状态机的逻辑，否则抛出异常
        if (this.action != action) {
            throw new StateMachineException("action of state machine is different from action of domain object");
        }
        // 如果domain对象的状态不在允许执行的范围内，则抛出异常
        if (CollectionUtils.isNotEmpty(prevStates) && !prevStates.contains(aggregateRoot.getState())) {
            String availableStateStr = prevStates.stream()
                    .map(Enum::name)
                    .collect(Collectors.joining(","));
            throw new StateMachineException("current state is incorrect:" + aggregateRoot.getState() + ", available states are:" + availableStateStr);
        }

        // 改变状态
        aggregateRoot.setState(nextState);

        // 触发event
        Optional.ofNullable(event)
                .ifPresent(it -> it.execute(aggregateRoot));
    }
}
