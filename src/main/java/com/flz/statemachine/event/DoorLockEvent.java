package com.flz.statemachine.event;

import com.flz.statemachine.StateEvent;

public class DoorLockEvent implements StateEvent {
    @Override
    public void execute() {
        System.out.println("门反锁了");
    }
}
