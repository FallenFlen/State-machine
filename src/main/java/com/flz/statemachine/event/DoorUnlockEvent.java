package com.flz.statemachine.event;

import com.flz.statemachine.StateEvent;

public class DoorUnlockEvent implements StateEvent {
    @Override
    public void execute() {
        System.out.println("门解锁了");
    }
}
