package ru.example.coffeemachine.config.statemachine.wrapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.StateMachineFactory;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.States;

@Configuration
public class WrapConfig {

    @Bean
    public SendWrapper wrapper(StateMachineFactory<States, Events> stateMachineFactory){
        return new SendWrapper(stateMachineFactory.getStateMachine());
    }
}
