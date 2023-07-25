package com.flz.statemachine;

import com.flz.statemachine.common.statemachine.DoorStateMachine;
import com.flz.statemachine.common.statemachine.event.DoorCloseEvent;
import com.flz.statemachine.domain.Door;
import com.flz.statemachine.enums.DoorAction;
import com.flz.statemachine.enums.DoorState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DoorStateMachineTest {

    @Test
    void should_trigger_and_execute_event() {
        Door door = Door.create("8-1-101", DoorState.OPEN);
        DoorCloseEvent doorCloseEvent = spy(DoorCloseEvent.class);
        DoorStateMachine stateMachine = DoorStateMachine.builder()
                .action(DoorAction.PUSH) // 当推门的时候
                .preStates(Set.of(DoorState.OPEN, DoorState.HALF_OPEN)) // 可以从 开启/半开启 状态流转到 关闭
                .nextState(DoorState.CLOSE)
                .event(doorCloseEvent) // 状态流转后，触发门关闭事件
                .build();

        stateMachine.trigger(DoorAction.PUSH, door);

        verify(doorCloseEvent).execute(argThat(it -> {
            assertEquals("8-1-101", it.getCode());
            assertEquals(DoorState.CLOSE, it.getState());
            return true;
        }));
    }
}
