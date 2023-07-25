package com.flz.statemachine.common.statemachine.event;

public class DoorOpenEvent implements StateEvent {
    @Override
    public void execute() {
        System.out.println("门打开了");
    }
}
