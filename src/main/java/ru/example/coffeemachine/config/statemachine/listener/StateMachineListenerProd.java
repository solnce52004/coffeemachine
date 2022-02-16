package ru.example.coffeemachine.config.statemachine.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.States;

@Configuration
//@Profile("dev")
//@Profile("prod")
@Slf4j
public class StateMachineListenerProd implements StateMachineListener<States, Events> {
    @Override
    public void stateChanged(State<States, Events> from, State<States, Events> to) {
        if (from.getId() != null) {
            System.out.println("Переход из статуса " + from.getId() + " в статус " + to.getId());
        }
    }

    @Override
    public void stateEntered(State<States, Events> state) {

    }

    @Override
    public void stateExited(State<States, Events> state) {

    }

    @Override
    public void eventNotAccepted(Message<Events> event) {
        System.out.println("Евент не принят " + event);
    }

    @Override
    public void transition(Transition<States, Events> transition) {

    }

    @Override
    public void transitionStarted(Transition<States, Events> transition) {

    }

    @Override
    public void transitionEnded(Transition<States, Events> transition) {

    }

    @Override
    public void stateMachineStarted(StateMachine<States, Events> stateMachine) {
        System.out.println("Machine started");
    }

    @Override
    public void stateMachineStopped(StateMachine<States, Events> stateMachine) {

    }

    @Override
    public void stateMachineError(StateMachine<States, Events> stateMachine, Exception exception) {
    }

    @Override
    public void extendedStateChanged(Object key, Object value) {

    }

    @Override
    public void stateContext(StateContext<States, Events> stateContext) {

    }
}