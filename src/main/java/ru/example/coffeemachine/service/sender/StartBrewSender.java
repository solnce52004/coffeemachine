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
public class StartBrewSender implements Sender {
    private final Events event = Events.PUSH_START_BREW;
    private final States state = States.DONE;

    private static final String SUCCESS_MSG = "Ваш кофе готов! Приятного кофепития! Для повторного приготовления пополните ресурсы!";
    private static final String FAIL_MSG = "Процесс варки не может быть выполнен. Возможно Вы не проверили наличие воды и кофе? Либо пытаетесь запустить процесс варки повторно? Уточните команду!";

    private static final String SUCCESS_DEBUG_MSG = "success start brew";
    private static final String FAIL_DEBUG_MSG = "Start brew failed! The preview state has not been changed. A repeatable request has been sent or the event has not been accepted.";

    private final CoffeeMachineService machineService;

    @Autowired
    public StartBrewSender(CoffeeMachineService machineService) {
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
        //нажали кнопу - передаются внутренние команды датчикам
        wrapper.sendMonoEvent(Events.PUSH_START_BREW);
        //автоматически - начался процесс варки
        wrapper.sendMonoEvent(Events.BREW);

        return getResponseMessageDTO(stateMachine);
    }

    @Override
    @Transactional
    public void checkAndUpdateResource(States curState, String curUUID, String curID) {
        machineService.resetResource(curState, curUUID, curID);
    }
}
