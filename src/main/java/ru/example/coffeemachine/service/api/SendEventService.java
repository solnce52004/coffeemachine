package ru.example.coffeemachine.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.States;
import ru.example.coffeemachine.config.statemachine.wrapper.SendWrapper;
import ru.example.coffeemachine.dto.ResponseMessageDTO;

@Service
public class SendEventService {
    private final ResponseService responseService;
    private final StateMachine<States, Events> stateMachine;
    private final SendWrapper wrapper;

    @Autowired
    public SendEventService(
            ResponseService responseService,
            SendWrapper wrapper
    ) {
        this.responseService = responseService;
        this.wrapper = wrapper;
        this.stateMachine = wrapper.getStateMachine();
    }

    public ResponseMessageDTO turnOn() {
        wrapper.sendMonoEvent(Events.PUSH_TURN_ON);

        return responseService.getResponseMessageDTO(
                stateMachine,
                Events.PUSH_TURN_ON,
                States.TURNED_ON
        );
    }

    public ResponseMessageDTO checkResources() {
        wrapper.sendMonoEvent(Events.CHECK_RESOURCES);

        return responseService.getResponseMessageDTO(
                stateMachine,
                Events.CHECK_RESOURCES,
                States.CHECKED_RESOURCES
        );
    }

    public ResponseMessageDTO startBrew() {
        //нажали кнопу - передаются внутренние команды датчикам
        wrapper.sendMonoEvent(Events.PUSH_START_BREW);
        //автоматически - начался процесс варки
        wrapper.sendMonoEvent(Events.BREW);

        return responseService.getResponseMessageDTO(
                stateMachine,
                Events.PUSH_START_BREW,
                States.DONE
        );
    }

    public ResponseMessageDTO turnOff() {
        wrapper.sendMonoEvent(Events.PUSH_TURN_OFF);

        return responseService.getResponseMessageDTO(
                stateMachine,
                Events.PUSH_TURN_OFF,
                States.TURNED_OFF
        );
    }
}
