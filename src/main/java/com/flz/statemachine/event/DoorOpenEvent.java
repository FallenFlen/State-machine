package com.flz.statemachine.event;

import com.flz.statemachine.StateEvent;

public class DoorOpenEvent implements StateEvent {
    @Override
    public void execute() {
        System.out.println("门打开了");
    }
}
