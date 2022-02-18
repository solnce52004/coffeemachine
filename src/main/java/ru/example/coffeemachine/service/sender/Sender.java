package ru.example.coffeemachine.service.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.statemachine.StateMachine;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.Guards;
import ru.example.coffeemachine.config.statemachine.enums.States;
import ru.example.coffeemachine.config.statemachine.wrapper.SendWrapper;
import ru.example.coffeemachine.dto.ResponseMessageDTO;
import ru.example.coffeemachine.util.LogHelper;

public interface Sender {
    Events getEvent();

    States getState();

    String getTextMsg(Boolean isRepeatRequest);

    String getDebugMsg(Boolean isRepeatRequest);

    ResponseMessageDTO sendEvent(SendWrapper wrapper);

    void checkAndUpdateResource(States curState, String curUUID, String curID);

    @Autowired
    default void registerMySelf(SenderRegister register) {
        register.register(getEvent(), this);
    }

    default HttpStatus getHttpStatus(States curState) {
        return curState != getState()
                ? HttpStatus.METHOD_NOT_ALLOWED
                : HttpStatus.OK;
    }

    default void putPrevStateInContext(StateMachine<States, Events> stateMachine) {
        stateMachine
                .getExtendedState()
                .getVariables()
                .put(
                        Guards.PREV_STATE.name(),
                        stateMachine.getState().getId()
                );
    }

    default ResponseMessageDTO getResponseMessageDTO(StateMachine<States, Events> stateMachine) {

        final States curState = stateMachine.getState().getId();
        final String curUUID = stateMachine.getUuid().toString();
        final String curID = stateMachine.getId();
        LogHelper.logAfterSend(getEvent(), curState, curUUID, curID);

        //идемпотентность
        final Boolean isRepeatableRequest = isPrevEqualsCurrentState(stateMachine, curState);

        if (!isRepeatableRequest && (curState == getState())) {
            checkAndUpdateResource(curState, curUUID, curID);
        }

        return new ResponseMessageDTO()
                .setText(getTextMsg(isRepeatableRequest))
                .setDebug(getDebugMsg(isRepeatableRequest))
                .setEvent(getEvent())
                .setState(curState)
                .setHttpStatus(getHttpStatus(curState));
    }

    default Boolean isPrevEqualsCurrentState(
            StateMachine<States, Events> stateMachine,
            States curState
    ) {
        final Object prevState = stateMachine
                .getExtendedState()
                .getVariables()
                .get(Guards.PREV_STATE.name());

        return prevState != null && prevState.toString().equals(curState.name());
    }
}
