package com.flz.statemachine.domain;

import com.flz.statemachine.common.statemachine.DoorStateMachine;
import com.flz.statemachine.common.statemachine.event.DoorCloseEvent;
import com.flz.statemachine.common.statemachine.event.DoorLockEvent;
import com.flz.statemachine.common.statemachine.event.DoorOpenEvent;
import com.flz.statemachine.common.statemachine.event.DoorUnlockEvent;
import com.flz.statemachine.domain.aggregate.DomainStateAggregateRoot;
import com.flz.statemachine.enums.DoorAction;
import com.flz.statemachine.enums.DoorState;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(value = AccessLevel.PROTECTED)
@SuperBuilder
public class Door extends DomainStateAggregateRoot<DoorState, DoorAction, DoorStateMachine> {
    private String code;

    public void lock() {
        executeStateMachine(DoorAction.LOCK);
    }

    public void unlock() {
        executeStateMachine(DoorAction.UNLOCK);
    }

    public void open() {
        executeStateMachine(DoorAction.PULL);
    }

    public void close() {
        executeStateMachine(DoorAction.PUSH);
    }

    public static Door create(String code, DoorState state) {
        return Door.builder()
                .code(code)
                .state(state)
                .build();
    }

    @Override
    protected List<DoorStateMachine> getStateMachines() {
        return new ArrayList<>() {{
            add(DoorStateMachine.builder()
                    .action(DoorAction.PUSH) // 当推门的时候
                    .preStates(Set.of(DoorState.OPEN, DoorState.HALF_OPEN)) // 可以从 开启/半开启 状态流转到 关闭
                    .nextState(DoorState.CLOSE)
                    .event(new DoorCloseEvent()) // 状态流转后，触发门关闭事件
                    .build());
            add(DoorStateMachine.builder()
                    .action(DoorAction.PULL) // 当拉门的时候
                    .preStates(Set.of(DoorState.CLOSE)) // 可以从 关闭 状态流转到 开启
                    .nextState(DoorState.OPEN)
                    .event(new DoorOpenEvent()) // 状态流转后，触发门开启事件
                    .build());
            add(DoorStateMachine.builder()
                    .action(DoorAction.LOCK) // 当反锁的时候
                    .preStates(Set.of(DoorState.CLOSE)) // 可以从 关闭 状态流转到 锁定
                    .nextState(DoorState.LOCKED)
                    .event(new DoorLockEvent()) // 状态流转后，触发门锁定事件
                    .build());
            add(DoorStateMachine.builder()
                    .action(DoorAction.UNLOCK) // 当反锁的时候
                    .preStates(Set.of(DoorState.LOCKED)) // 可以从 锁定 状态流转到 关闭
                    .nextState(DoorState.CLOSE)
                    .event(new DoorUnlockEvent()) // 状态流转后，触发门解锁事件
                    .build());
        }};
    }
}
