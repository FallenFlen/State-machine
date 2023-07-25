package com.flz.statemachine.common.statemachine;


import com.flz.statemachine.enums.DoorAction;
import com.flz.statemachine.enums.DoorState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class DoorStateMachine extends AbstractStateMachine<DoorState, DoorAction> {


}
