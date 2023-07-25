package com.flz.statemachine.common.statemachine.event;

public class DoorUnlockEvent implements StateEvent {
    @Override
    public void execute() {
        System.out.println("门解锁了");
    }
}
