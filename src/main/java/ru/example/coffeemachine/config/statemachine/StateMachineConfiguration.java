package ru.example.coffeemachine.config.statemachine;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.Message;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.Guards;
import ru.example.coffeemachine.config.statemachine.enums.States;
import ru.example.coffeemachine.domain.commnd.Command;
import ru.example.coffeemachine.domain.commnd.RegisterCommand;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
@EnableStateMachineFactory
@AllArgsConstructor
@Slf4j
public class StateMachineConfiguration extends EnumStateMachineConfigurerAdapter<States, Events> {

    private final StateMachineRuntimePersister<States, Events, UUID> stateMachineRuntimePersister;

    private final RegisterCommand register;
    private final List<Command> commandList;

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config
                .withPersistence()
                .runtimePersister(stateMachineRuntimePersister)
                .and()
                .withConfiguration()
                .machineId(randomUUID())
                .listener(listener())
                .autoStartup(true);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public String randomUUID() {
        //TODO: id - on once run, uuid - on each autowiring?
        return UUID.randomUUID().toString();
    }

    @Bean
    @Profile("prod")
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<>() {
            @Override
            public void eventNotAccepted(Message<Events> msgEvent) {
                log.error("Event not accepted: {}", msgEvent.getPayload());
            }
        };
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states
                .withStates()
                .initial(States.TURNED_OFF)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        logCommandList();

        transitions
                .withExternal()
                .source(States.TURNED_OFF)
                .target(States.TURNED_ON)
                .event(Events.PUSH_TURN_ON)
                .action(register.getCommand(Events.PUSH_TURN_ON))

                .and()
                .withExternal()
                .source(States.TURNED_ON)
                .target(States.CHECKED_RESOURCES)
                .event(Events.CHECK_RESOURCES)
                .action(register.getCommand(Events.CHECK_RESOURCES))

                .and()
                .withExternal()
                .source(States.TURNED_ON)
                .target(States.TURNED_OFF)
                .event(Events.PUSH_TURN_OFF)
                .action(register.getCommand(Events.PUSH_TURN_OFF))

                .and()
                .withExternal()
                .source(States.CHECKED_RESOURCES)
                .target(States.STARTED_BREW)
                .event(Events.PUSH_START_BREW)
                .action(register.getCommand(Events.PUSH_START_BREW))
                .guard(isCheckedResourcesGuard())

                .and()
                .withExternal()
                .source(States.CHECKED_RESOURCES)
                .target(States.TURNED_OFF)
                .event(Events.PUSH_TURN_OFF)
                .action(register.getCommand(Events.PUSH_TURN_OFF))

                .and()
                .withExternal()
                .source(States.STARTED_BREW)
                .target(States.DONE)
                .event(Events.BREW)
                .action(register.getCommand(Events.BREW))

                .and()
                .withExternal()
                .source(States.DONE)
                .target(States.CHECKED_RESOURCES)
                .event(Events.CHECK_RESOURCES)
                .action(register.getCommand(Events.CHECK_RESOURCES))

                .and()
                .withExternal()
                .source(States.DONE)
                .target(States.TURNED_OFF)
                .event(Events.PUSH_TURN_OFF)
                .action(register.getCommand(Events.PUSH_TURN_OFF));
    }

    private Guard<States, Events> isCheckedResourcesGuard() {
        return context -> (Boolean) context
                .getExtendedState()
                .getVariables()
                .get(Guards.IS_CHECKED_RESOURCES.name());
    }

    private void logCommandList() {
        final List<String> list = commandList.stream()
                .map(Command::getEvent)
                .map(Enum::name)
                .peek(log::debug)
                .collect(Collectors.toList());
        log.debug("Count of commands implements Action: {}", list.size());
    }
}
