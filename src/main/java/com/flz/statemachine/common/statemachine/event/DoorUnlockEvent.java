package com.flz.statemachine.common.statemachine.event;

import com.flz.statemachine.domain.Door;

public class DoorUnlockEvent implements StateEvent<Door> {
    @Override
    public void execute(Door door) {
        System.out.printf("门[%s]解锁了\n", door.getCode());
    }
}
