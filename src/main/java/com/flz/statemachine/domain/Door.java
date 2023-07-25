package com.flz.statemachine.domain;

import com.flz.statemachine.DomainStateAggregateRoot;
import com.flz.statemachine.DoorStateMachine;
import com.flz.statemachine.enums.DoorAction;
import com.flz.statemachine.enums.DoorState;
import com.flz.statemachine.event.DoorCloseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Door extends DomainStateAggregateRoot<DoorState, DoorAction, DoorStateMachine> {


    public void lock() {

    }

    public void unlock() {

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
                    .event(new DoorCloseEvent()) // 状态流转后，触发门开启事件
                    .build());
            add(DoorStateMachine.builder()
                    .action(DoorAction.LOCK) // 当反锁的时候
                    .preStates(Set.of(DoorState.CLOSE)) // 可以从 关闭 状态流转到 锁定
                    .nextState(DoorState.LOCKED)
                    .event(new DoorCloseEvent()) // 状态流转后，触发门锁定事件
                    .build());
            add(DoorStateMachine.builder()
                    .action(DoorAction.UNLOCK) // 当反锁的时候
                    .preStates(Set.of(DoorState.LOCKED)) // 可以从 锁定 状态流转到 关闭
                    .nextState(DoorState.CLOSE)
                    .event(new DoorCloseEvent()) // 状态流转后，触发门解锁事件
                    .build());
        }};
    }
}
