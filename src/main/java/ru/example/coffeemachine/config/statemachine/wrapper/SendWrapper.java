package ru.example.coffeemachine.config.statemachine.wrapper;

import org.springframework.statemachine.StateMachine;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.States;

public class SendWrapper implements StateMachineEventSender {
    private final StateMachine<States, Events> stateMachine;

    public SendWrapper(StateMachine<States, Events> stateMachine) {
        this.stateMachine = stateMachine;
    }

    @Override
    public StateMachine<States, Events> getStateMachine() {
        return this.stateMachine;
    }
}
