package ru.example.coffeemachine.config.statemachine.wrapper;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import reactor.core.publisher.Mono;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.States;

public interface StateMachineEventSender {
    StateMachine<States, Events> getStateMachine();

    default void sendMonoEvent(Events event) {
        sendEvent(MessageBuilder.withPayload(event).build());
    }

    default void sendEvent(Message<Events> message) {
        getStateMachine()
                .sendEvent(Mono.just(message))
                .subscribe();
    }
}
