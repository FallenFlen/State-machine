package com.flz.statemachine.common.statemachine.event;

import com.flz.statemachine.domain.Door;

public class DoorOpenEvent implements StateEvent<Door> {
    @Override
    public void execute(Door door) {
        System.out.printf("门[%s]打开了\n", door.getCode());
    }
}
