package com.flz.statemachine.common.statemachine.event;

public class DoorCloseEvent implements StateEvent {
    @Override
    public void execute() {
        System.out.println("门关闭了");
    }
}
