package com.flz.statemachine;

import com.flz.statemachine.domain.Door;
import com.flz.statemachine.enums.DoorState;
import com.flz.statemachine.exception.StateMachineException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DoorTest {

    @Test
    void should_lock_door_and_trigger() {
        Door door = Door.create("8-1-101", DoorState.CLOSE);
        door.lock();
        assertEquals(DoorState.LOCKED, door.getState());
    }

    @Test
    void should_unlock_door_and_trigger() {
        Door door = Door.create("8-1-102", DoorState.LOCKED);
        door.unlock();
        assertEquals(DoorState.CLOSE, door.getState());
    }

    @Test
    void should_pull_door_and_trigger() {
        Door door = Door.create("8-1-103", DoorState.CLOSE);
        door.pull();
        assertEquals(DoorState.OPEN, door.getState());
    }

    @EnumSource(value = DoorState.class, names = {"OPEN", "HALF_OPEN"})
    @ParameterizedTest
    void should_push_door_and_trigger(DoorState state) {
        Door door = Door.create("8-1-104", state);
        door.push();
        assertEquals(DoorState.CLOSE, door.getState());
    }

    @EnumSource(value = DoorState.class, names = {"CLOSE", "LOCKED"})
    @ParameterizedTest
    void should_throw_exception_when_state_is_incorrect(DoorState state) {
        Door door = Door.create("8-1-105", state);
        StateMachineException stateMachineException = assertThrows(StateMachineException.class, door::push);
        assertEquals("state of state machine is different from state of domain object", stateMachineException.getMessage());
    }
}
