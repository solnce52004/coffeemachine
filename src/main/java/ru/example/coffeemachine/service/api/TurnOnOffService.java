package ru.example.coffeemachine.service.api;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.States;
import ru.example.coffeemachine.config.statemachine.wrapper.SendWrapper;
import ru.example.coffeemachine.dto.ResponseMessageDTO;

@Service
@Getter
@Slf4j
public class TurnOnOffService {
    private final StateMachine<States, Events> stateMachine;
    private final SendWrapper wrapper;

    @Autowired
    public TurnOnOffService(SendWrapper wrapper) {
        this.wrapper = wrapper;
        this.stateMachine = wrapper.getStateMachine();
    }

    public ResponseMessageDTO turnOn() {
        wrapper.sendMonoEvent(Events.PUSH_TURN_ON);

        log.info(this.stateMachine.getUuid().toString());
        log.info("pushed turnOn");

        final States curState = stateMachine.getState().getId();
        log.info("pushed turnOn curState {}", curState);
        //save in db
        //выдать ид см (процессу)
        //сохранить под этим ид стейт

        return new ResponseMessageDTO()
                .setText("State on PUSH_TURN_ON is " + curState)
                .setState(curState);
    }

    public ResponseMessageDTO turnOff() {
        wrapper.sendMonoEvent(Events.PUSH_TURN_OFF);

        final States curState = stateMachine.getState().getId();
        final String curUUID = this.stateMachine.getUuid().toString();
        final String curID = this.stateMachine.getId();
        log.info("stateAfter  PUSH_TURN_OFF {}", curState);
        log.info("uuidAfter PUSH_TURN_OFF {}", curUUID);
        log.info("idAfter PUSH_TURN_OFF {}", curID);

        //меняем статус in db

        return new ResponseMessageDTO()
                .setText("State on PUSH_TURN_OFF is " + curState)
                .setState(curState);
    }
}
