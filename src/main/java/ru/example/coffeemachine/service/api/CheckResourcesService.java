package ru.example.coffeemachine.service.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.States;
import ru.example.coffeemachine.config.statemachine.wrapper.SendWrapper;
import ru.example.coffeemachine.dto.ResponseMessageDTO;
import ru.example.coffeemachine.entity.CoffeeMachine;
import ru.example.coffeemachine.service.modelservice.CoffeeMachineService;
import ru.example.coffeemachine.service.modelservice.ResourceService;

@Service
@Slf4j
public class CheckResourcesService {
    private final ResourceService resourceService;
    private final CoffeeMachineService machineService;

    private final StateMachine<States, Events> stateMachine;
    private final SendWrapper wrapper;

    @Autowired
    public CheckResourcesService(
            ResourceService resourceService,
            CoffeeMachineService machineService,
            SendWrapper wrapper
    ) {
        this.resourceService = resourceService;
        this.machineService = machineService;
        this.wrapper = wrapper;
        this.stateMachine = wrapper.getStateMachine();
    }

    public ResponseMessageDTO checkResources() {
        wrapper.sendMonoEvent(Events.CHECK_RESOURCES);

        final States curState = stateMachine.getState().getId();
        final String curUUID = this.stateMachine.getUuid().toString();
        final String curID = this.stateMachine.getId();
        logAfter(curState, curUUID, curID);

        // проверяем, что переход прошел (по логике см это допустимо)
        // обновляем статус в бд и цепляем пополненные ресурсы
        if (curState == States.CHECKED_RESOURCES) {
            final CoffeeMachine coffeeMachine = new CoffeeMachine()
                    .setMachineUUID(curUUID)
                    .setMachineId(curID)
                    .setState(curState)
                    .setResource(resourceService.createFull());

            machineService.logNewState(coffeeMachine);
        }

        return new ResponseMessageDTO()
                .setText("State on checkResources is " + curState)
                .setState(curState);
    }

    private void logAfter(States curState, String curUUID, String curID) {
        log.info("stateAfterCheck {}", curState);
        log.info("uuidAfterCheck {}", curUUID);
        log.info("idAfterCheck {}", curID);
    }
}