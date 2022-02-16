package ru.example.coffeemachine.config.statemachine.wrapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.StateMachineFactory;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.States;

@Configuration
@Slf4j
public class WrapConfig {

//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean
    public SendWrapper wrapper(StateMachineFactory<States, Events> stateMachineFactory){
        return new SendWrapper(stateMachineFactory.getStateMachine());
    }
}
