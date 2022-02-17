package ru.example.coffeemachine.service.api;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.Guards;
import ru.example.coffeemachine.config.statemachine.enums.States;
import ru.example.coffeemachine.dto.ResponseMessageDTO;
import ru.example.coffeemachine.entity.CoffeeMachine;
import ru.example.coffeemachine.entity.Resource;
import ru.example.coffeemachine.service.modelservice.CoffeeMachineService;
import ru.example.coffeemachine.service.modelservice.ResourceService;
import ru.example.coffeemachine.util.LogHelper;

@Service
@AllArgsConstructor
@Slf4j
public class ResponseService {
    private static final String SUCCESS_MSG = "The command was executed successfully";
    private static final String FAIL_MSG = "The command was not executed";

    private static final String SUCCESS_DEBUG_MSG = "success";
    private static final String FAIL_DEBUG_MSG = "The preview state has not been changed. A repeatable request has been sent or the event has not been accepted.";

    private final ResourceService resourceService;
    private final CoffeeMachineService machineService;

    public ResponseMessageDTO getResponseMessageDTO(
            StateMachine<States, Events> stateMachine,
            Events event,
            States state
    ) {
        final States curState = stateMachine.getState().getId();
        final String curUUID = stateMachine.getUuid().toString();
        final String curID = stateMachine.getId();
        LogHelper.logAfterSend(event, curState, curUUID, curID);

        //идемпотентность
        final Boolean isRepeatableRequest = isPrevEqualsCurrentState(stateMachine, curState);

        if (!isRepeatableRequest && (curState == state)) {
            checkAndUpdateResource(curState, curUUID, curID);
        }

        return new ResponseMessageDTO()
                .setText(getTextMsg(isRepeatableRequest))
                .setDebug(getDebugMsg(isRepeatableRequest))
                .setEvent(event)
                .setState(curState);
    }

    private Boolean isPrevEqualsCurrentState(
            StateMachine<States, Events> stateMachine,
            States curState
    ) {
        final Object prevState = stateMachine
                .getExtendedState()
                .getVariables()
                .get(Guards.PREV_STATE.name());

        return prevState != null && prevState.toString().equals(curState.name());
    }

    private void checkAndUpdateResource(
            States curState,
            String curUUID,
            String curID
    ) {
        Resource resource = resourceService.findById(
                getLatestResource(curState, curUUID).getId()
        );

        final CoffeeMachine coffeeMachine = new CoffeeMachine()
                .setMachineUUID(curUUID)
                .setMachineId(curID)
                .setState(curState)
                .setResource(resource);

        machineService.persistCoffeeMachine(coffeeMachine);
    }

    private Resource getLatestResource(States state, String uuid) {
        Resource resource;

        switch (state) {
            case DONE:
                resource = resourceService.createEmpty();
                break;
            case CHECKED_RESOURCES:
                resource = machineService.findLatestByUuidOrCreateFull(uuid);
                break;
            default:
                resource = machineService.findLatestByUuidOrCreateEmpty(uuid);
                break;
        }

        return resource;
    }

    private String getTextMsg(Boolean isRepeatRequest) {
        return isRepeatRequest
                ? FAIL_MSG
                : SUCCESS_MSG;
    }

    private String getDebugMsg(Boolean isRepeatRequest) {
        return isRepeatRequest
                ? FAIL_DEBUG_MSG
                : SUCCESS_DEBUG_MSG;
    }
}
