package ru.example.coffeemachine.config.statemachine.listener;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.States;

import java.util.Optional;

@Configuration
//@Profile("prod")
@Profile("dev")
@Slf4j
public class StateMachineListenerDevConfig {

    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<>() {
            private final Marker marker = MarkerFactory.getMarker("DEV_MARKER");

            @Override
            public void transition(Transition<States, Events> transition) {
                log.info(marker, "Transition from:{} to:{}",
                        ofNullableState(transition.getSource()),
                        ofNullableState(transition.getTarget()));
            }

            @Override
            public void stateMachineError(StateMachine<States, Events> stateMachine, Exception exception) {
                log.error(marker, "Exception: ", exception);
                super.stateMachineError(stateMachine, exception);
            }

            @Override
            public void eventNotAccepted(Message<Events> event) {
                log.error(marker, "Event not accepted: {}", event);
            }

            private Object ofNullableState(State<States, Events> s) {
                return Optional.ofNullable(s)
                        .map(State::getId)
                        .orElse(null);
            }
        };
    }
}
