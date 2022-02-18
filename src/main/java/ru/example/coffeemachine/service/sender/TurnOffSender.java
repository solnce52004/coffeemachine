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
public class TurnOffSender implements Sender {
    private final Events event = Events.PUSH_TURN_OFF;
    private final States state = States.TURNED_OFF;

    private static final String SUCCESS_MSG = "Кофемашина выключена! До свидания!";
    private static final String FAIL_MSG = "Запуск процесса выключения кофемашины не доступен. Возможно Вы пытаетесь выключить ее повторно? Уточните команду!";

    private static final String SUCCESS_DEBUG_MSG = "success turn off";
    private static final String FAIL_DEBUG_MSG = "The preview state has not been changed. A repeatable request has been sent or the event has not been accepted.";

    private final CoffeeMachineService machineService;

    @Autowired
    public TurnOffSender(CoffeeMachineService machineService) {
        this.machineService = machineService;
    }

    @Override
    public ResponseMessageDTO sendEvent(SendWrapper wrapper) {
        StateMachine<States, Events> stateMachine = wrapper.getStateMachine();

        putPrevStateInContext(stateMachine);
        wrapper.sendMonoEvent(getEvent());

        return getResponseMessageDTO(stateMachine);
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
    @Transactional
    public void checkAndUpdateResource(States curState, String curUUID, String curID) {
        machineService.checkAndUpdateResource(
                curState, curUUID, curID,
                machineService.findLatestByUuidOrCreateEmpty(curUUID).getId()
        );
    }
}