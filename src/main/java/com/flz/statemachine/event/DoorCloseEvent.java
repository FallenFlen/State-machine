package com.flz.statemachine.event;

import com.flz.statemachine.StateEvent;

public class DoorCloseEvent implements StateEvent {
    @Override
    public void execute() {
        System.out.println("门关闭了");
    }
}
