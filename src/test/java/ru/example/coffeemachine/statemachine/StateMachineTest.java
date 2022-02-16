package ru.example.coffeemachine.statemachine;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.test.StateMachineTestPlan;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.States;

@SpringBootTest
public class StateMachineTest {

    @Autowired
    private StateMachineFactory<States, Events> stateMachineFactory;
    private StateMachine<States, Events> stateMachine;

    @BeforeEach
    void setUp() {
        stateMachine = stateMachineFactory.getStateMachine();
    }

    @Test
    void turnInit() {
        Assertions.assertThat(stateMachine).isNotNull();
    }

    @Test
    public void testGreenWay() throws Exception {
        StateMachineTestPlan<States, Events> plan =
                StateMachineTestPlanBuilder
                        .<States, Events>builder()
                        .defaultAwaitTime(2)
                        .stateMachine(stateMachine)

                        .step()
                        .expectStates(States.TURNED_OFF)
                        .expectStateChanged(0)

                        .and()
                        .step()
                        .sendEvent(Events.PUSH_TURN_ON)
                        .expectState(States.TURNED_ON)
                        .expectStateChanged(1)

                        .and()
                        .step()
                        .sendEvent(Events.CHECK_RESOURCES)
                        .expectState(States.CHECKED_RESOURCES)
                        .expectStateChanged(1)

                        .and()
                        .step()
                        .sendEvent(Events.PUSH_START_BREW)
                        .expectState(States.STARTED_BREW)
                        .expectStateChanged(1)

                        .and()
                        .step()
                        .sendEvent(Events.BREW)
                        .expectState(States.DONE)
                        .expectStateChanged(1)

                        .and()
                        .step()
                        .sendEvent(Events.PUSH_TURN_OFF)
                        .expectState(States.TURNED_OFF)
                        .expectStateChanged(1)

                        .and()
                        .build();
        plan.test();
    }

    @Test
    public void testMissingCheckResources() throws Exception {
        StateMachineTestPlan<States, Events> plan =
                StateMachineTestPlanBuilder
                        .<States, Events>builder()
                        .defaultAwaitTime(2)
                        .stateMachine(stateMachine)

                        .step()
                        .expectStates(States.TURNED_OFF)
                        .expectStateChanged(0)

                        .and()
                        .step()
                        .sendEvent(Events.PUSH_TURN_ON)
                        .expectState(States.TURNED_ON)
                        .expectStateChanged(1)

                        //missing CHECK_RESOURCES
                        .and()
                        .step()
                        .sendEvent(Events.PUSH_START_BREW)
                        .expectState(States.TURNED_ON)
                        .expectEventNotAccepted(1)

                        .and()
                        .build();
        plan.test();
    }
}
