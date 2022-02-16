package ru.example.coffeemachine.config.statemachine.persister;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.States;

import java.util.UUID;

@Configuration
@EnableJpaRepositories("org.springframework.statemachine.data.jpa")
@EntityScan("org.springframework.statemachine.data.jpa")
public class PersisterConfig {

    @Bean
    public StateMachineRuntimePersister<States, Events, UUID> stateMachineRuntimePersister(
            JpaStateMachineRepository jpaStateMachineRepository) {
        return new JpaPersistingStateMachineInterceptor<>(jpaStateMachineRepository);
    }

    @Bean
    public StateMachinePersister<States, Events, UUID> persister(
            StateMachinePersist<States, Events, UUID> defaultPersist
    ) {
        return new DefaultStateMachinePersister<>(defaultPersist);
    }
}