package ru.example.coffeemachine.service.sender;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.States;
import ru.example.coffeemachine.config.statemachine.wrapper.SendWrapper;
import ru.example.coffeemachine.dto.ResponseMessageDTO;
import ru.example.coffeemachine.service.modelservice.CoffeeMachineService;

@Service
@Getter
public class CheckResourceSender implements Sender {
    private final Events event = Events.CHECK_RESOURCES;
    private final States state = States.CHECKED_RESOURCES;

    private static final String SUCCESS_MSG = "Автоматическая проверка/пополнение воды и кофе прошла успешно! Можете запустить процесс варки кофе!";
    private static final String FAIL_MSG = "Автоматическая проверка/пополнение воды и кофе не доступна. Возможно Вы пытаетесь запустить ее повторно? Либо не включили кофемашину? Уточните команду!";

    private static final String SUCCESS_DEBUG_MSG = "success check resources";
    private static final String FAIL_DEBUG_MSG = "The preview state has not been changed. A repeatable request has been sent or the event has not been accepted.";

    private final CoffeeMachineService machineService;

    @Autowired
    public CheckResourceSender(CoffeeMachineService machineService) {
        this.machineService = machineService;
    }

    @Override
    public String getTextMsg(Boolean isRepeatRequest) {
        return isRepeatRequest ? FAIL_MSG : SUCCESS_MSG;
    }

    @Override
    public String getDebugMsg(Boolean isRepeatRequest) {
        return isRepeatRequest ? FAIL_DEBUG_MSG : SUCCESS_DEBUG_MSG;
    }

    @Override
    public ResponseMessageDTO sendEvent(SendWrapper wrapper) {
        StateMachine<States, Events> stateMachine = wrapper.getStateMachine();

        putPrevStateInContext(stateMachine);
        wrapper.sendMonoEvent(getEvent());

        return getResponseMessageDTO(stateMachine);
    }

    @Override
    @Transactional
    public void checkAndUpdateResource(States curState, String curUUID, String curID) {
        machineService.checkAndUpdateResource(
                curState, curUUID, curID,
                machineService.findLatestByUuidOrCreateFull(curUUID).getId()
        );
    }
}