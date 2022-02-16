package ru.example.coffeemachine.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.wrapper.SendWrapper;
import ru.example.coffeemachine.config.statemachine.enums.States;
import ru.example.coffeemachine.dto.ResponseMessageDTO;

@Service
@Slf4j
public class StartBrewService {
    private final StateMachine<States, Events> stateMachine;
    private final SendWrapper wrapper;

    @Autowired
    public StartBrewService(SendWrapper wrapper) {
        this.wrapper = wrapper;
        this.stateMachine = wrapper.getStateMachine();
    }

    public ResponseMessageDTO startBrew() {
        //нажали кнопу - передаются внутренние команды датчикам
        wrapper.sendMonoEvent(Events.PUSH_START_BREW);
        //автоматически - начался процесс варки
        wrapper.sendMonoEvent(Events.BREW);

        final States curState = stateMachine.getState().getId();

        log.info(this.stateMachine.getUuid().toString());
        log.info("pushed startBrew");

        return new ResponseMessageDTO()
                .setText("State on PUSH_START and BREW is " + curState)
                .setState(curState);
    }
}
