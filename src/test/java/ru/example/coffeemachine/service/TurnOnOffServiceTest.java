package ru.example.coffeemachine.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.States;
import ru.example.coffeemachine.config.statemachine.wrapper.SendWrapper;
import ru.example.coffeemachine.dto.ResponseMessageDTO;
import ru.example.coffeemachine.service.api.TurnOnOffService;

@SpringBootTest
class TurnOnOffServiceTest {

    @Autowired
    private StateMachineFactory<States, Events> stateMachineFactory;
    private StateMachine<States, Events> stateMachine;
    @Autowired
    private TurnOnOffService service;


    @BeforeEach
    void setUp() {
        stateMachine = stateMachineFactory.getStateMachine();
        service = new TurnOnOffService(new SendWrapper(stateMachine));
    }

    @Test
    void turnInit() {
        Assertions.assertThat(service.getStateMachine())
                .isNotNull();
    }

    @Test
    void turnOn() {
        final ResponseMessageDTO messageDTO = service.turnOn();
        System.out.println(stateMachine.getId());
        final ResponseMessageDTO messageDTO2 = service.turnOn();
        System.out.println(stateMachine.getId());

        Assertions.assertThat(messageDTO.getState())
                .isEqualTo(States.TURNED_ON);
        Assertions.assertThat(messageDTO2.getState())
                .isEqualTo(States.TURNED_ON);
    }

    @Test
    void turnOff() {
        final ResponseMessageDTO messageDTO = service.turnOff();
        System.out.println(stateMachine.getId());

        Assertions.assertThat(messageDTO.getState())
                .isEqualTo(States.TURNED_OFF);
    }

//    @Test
//    void turnOnOff() {
//        Assertions.assertThat(service.turnOn().getState())
//                .isEqualTo(States.TURNED_ON);
//        Assertions.assertThat(service.turnOff().getState())
//                .isEqualTo(States.TURNED_OFF);
//    }
}