package com.flz.statemachine.domain;


import com.flz.statemachine.common.statemachine.AbstractStateMachine;
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
