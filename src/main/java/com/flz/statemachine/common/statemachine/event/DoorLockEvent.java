package com.flz.statemachine.common.statemachine.event;

public class DoorLockEvent implements StateEvent {
    @Override
    public void execute() {
        System.out.println("门反锁了");
    }
}
