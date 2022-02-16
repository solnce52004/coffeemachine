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
public class CheckResourcesService {
    private final StateMachine<States, Events> stateMachine;
    private final SendWrapper wrapper;

    @Autowired
    public CheckResourcesService(SendWrapper wrapper) {
        this.wrapper = wrapper;
        this.stateMachine = wrapper.getStateMachine();
    }

    public ResponseMessageDTO checkResources() {
        wrapper.sendMonoEvent(Events.PUSH_START_BREW);
        final States curState = stateMachine.getState().getId();

        log.info(this.stateMachine.getUuid().toString());
        log.info("checkResources");

        return new ResponseMessageDTO()
                .setText("State on checkResources is " + curState)
                .setState(curState);
    }
}