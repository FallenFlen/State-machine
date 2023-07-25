package com.flz.statemachine;

import com.flz.statemachine.exception.StateMachineException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 状态机包含四大要素
 * 当前状态
 * 被允许流转的状态(确保当前状态的正确性)
 * 触发状态流转的动作
 * 下一个状态
 * 流转到下一个状态后触发的事件
 */
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractStateMachine<T extends Enum, A extends Enum> {
    protected T state;
    protected T nextState;
    protected Set<T> availableState;
    protected List<StateEvent> stateEvents;

    public final void trigger(A action, DomainStateAggregateRoot<T, AbstractStateMachine<T, A>> aggregateRoot) {
        // 非空校验
        Objects.requireNonNull(state);
        Objects.requireNonNull(nextState);
        Objects.requireNonNull(aggregateRoot.getState());

        // 只有domain对象的state和状态机的state相同，才能用该domain对象执行状态机的逻辑，否则抛出异常
        if (aggregateRoot.getState() != state) {
            throw new StateMachineException("state of state machine is different from state of domain object");
        }
        // 如果domain对象的状态不在允许执行的范围内，则抛出异常
        if (CollectionUtils.isNotEmpty(availableState) && !availableState.contains(aggregateRoot.getState())) {
            String availableStateStr = availableState.stream()
                    .map(Enum::name)
                    .collect(Collectors.joining(","));
            throw new StateMachineException("current state is incorrect:" + state + ", available states are:" + availableStateStr);
        }

        // 改变状态
        aggregateRoot.setState(nextState);

        // 触发event
        if (CollectionUtils.isNotEmpty(stateEvents)) {
            stateEvents.forEach(StateEvent::execute);
        }
    }
}
