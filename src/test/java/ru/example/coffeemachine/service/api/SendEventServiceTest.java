package ru.example.coffeemachine.service.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.States;
import ru.example.coffeemachine.config.statemachine.wrapper.SendWrapper;
import ru.example.coffeemachine.service.sender.Sender;
import ru.example.coffeemachine.service.sender.SenderRegister;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Предварительно выставляем состояние "Выключено"
 * Выполняем по 2 одинаковых запроса для проверки идемпотентности
 */
@SpringBootTest
class SendEventServiceTest {

    @Autowired
    List<Sender> senders;
    @Autowired
    private SenderRegister register;
    @Autowired
    private SendWrapper stateMachineWrapper;

    @Autowired
    private SendEventService sendEventService;

    @BeforeEach
    void setUp() {
        sendEventService.send(Events.PUSH_TURN_OFF);
    }

    @Test
    void sendPushTurnOn() {
        assertThat(sendEventService.send(Events.PUSH_TURN_ON))
                .isNotNull();
        assertThat(sendEventService.send(Events.PUSH_TURN_ON).getState())
                .isEqualTo(States.TURNED_ON);
    }

    @Test
    void sendCheckResources() {
        sendEventService.send(Events.PUSH_TURN_ON);

        assertThat(sendEventService.send(Events.CHECK_RESOURCES))
                .isNotNull();
        assertThat(sendEventService.send(Events.CHECK_RESOURCES).getState())
                .isEqualTo(States.CHECKED_RESOURCES);
    }

    @Test
    void sendPushStartBrew() {
        sendEventService.send(Events.PUSH_TURN_ON);
        sendEventService.send(Events.CHECK_RESOURCES);

        assertThat(sendEventService.send(Events.PUSH_START_BREW))
                .isNotNull();
        assertThat(sendEventService.send(Events.PUSH_START_BREW).getState())
                .isEqualTo(States.DONE);
    }

    @Test
    void sendPushTurnOff() {
        sendEventService.send(Events.PUSH_TURN_ON);
        sendEventService.send(Events.CHECK_RESOURCES);
        sendEventService.send(Events.PUSH_START_BREW);

        assertThat(sendEventService.send(Events.PUSH_TURN_OFF))
                .isNotNull();
        assertThat(sendEventService.send(Events.PUSH_TURN_OFF).getState())
                .isEqualTo(States.TURNED_OFF);
    }

    @Test
    void testDoubleMakeCoffeeWithOldResources() {
        sendEventService.send(Events.PUSH_TURN_ON);
        sendEventService.send(Events.CHECK_RESOURCES);
        sendEventService.send(Events.PUSH_TURN_OFF);

        sendEventService.send(Events.PUSH_TURN_ON);
        sendEventService.send(Events.CHECK_RESOURCES);
        sendEventService.send(Events.PUSH_START_BREW);

        assertThat(sendEventService.send(Events.PUSH_TURN_OFF).getState())
                .isEqualTo(States.TURNED_OFF);
        assertThat(sendEventService.send(Events.PUSH_TURN_OFF).getHttpStatus())
                .isEqualTo(HttpStatus.OK);
    }

    @Test
    void testLostCheckResourcesAndGetHttpStatus405() {
        sendEventService.send(Events.PUSH_TURN_ON);

        assertThat(sendEventService.send(Events.PUSH_START_BREW))
                .isNotNull();
        assertThat(sendEventService.send(Events.PUSH_START_BREW).getState())
                .isEqualTo(States.TURNED_ON);
        assertThat(sendEventService.send(Events.PUSH_START_BREW).getHttpStatus())
                .isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Test
    void testBacRequestTurnOnAndGetHttpStatus405() {
        sendEventService.send(Events.PUSH_TURN_ON);
        sendEventService.send(Events.CHECK_RESOURCES);
        sendEventService.send(Events.PUSH_START_BREW);

        assertThat(sendEventService.send(Events.PUSH_TURN_ON))
                .isNotNull();
        assertThat(sendEventService.send(Events.PUSH_TURN_ON).getState())
                .isEqualTo(States.DONE);
        assertThat(sendEventService.send(Events.PUSH_TURN_ON).getHttpStatus())
                .isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Test
    void testTurnOffAnyWereAndGetHttpStatus200() {
        sendEventService.send(Events.PUSH_TURN_ON);
        sendEventService.send(Events.CHECK_RESOURCES);

        assertThat(sendEventService.send(Events.PUSH_TURN_OFF))
                .isNotNull();
        assertThat(sendEventService.send(Events.PUSH_TURN_OFF).getState())
                .isEqualTo(States.TURNED_OFF);
        assertThat(sendEventService.send(Events.PUSH_TURN_OFF).getHttpStatus())
                .isEqualTo(HttpStatus.OK);
    }
}