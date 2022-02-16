package ru.example.coffeemachine.service.api;

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

        log.info(stateMachine.getState().getId().toString());

        wrapper.sendMonoEvent(Events.CHECK_RESOURCES);
        final States curState = stateMachine.getState().getId();

        log.info(this.stateMachine.getUuid().toString());
        log.info("checkResources");

        //проверить в бд привязанные к ид см ресурсы
        //есть ли?
        //сколько?
        //по умолчанию в кофеварке 0/0
        //пополнить, если надо 100/100
        //изменить статус - сохранить его в базе
        //сделать связь ид см с ресурсами

        return new ResponseMessageDTO()
                .setText("State on checkResources is " + curState)
                .setState(curState);
    }
}