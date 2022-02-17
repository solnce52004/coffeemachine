package ru.example.coffeemachine.service.api;

import lombok.AllArgsConstructor;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.States;
import ru.example.coffeemachine.dto.ResponseMessageDTO;
import ru.example.coffeemachine.entity.CoffeeMachine;
import ru.example.coffeemachine.entity.Resource;
import ru.example.coffeemachine.service.modelservice.CoffeeMachineService;
import ru.example.coffeemachine.service.modelservice.ResourceService;
import ru.example.coffeemachine.util.LogHelper;

@Service
@AllArgsConstructor
public class ResponseService {
    private final ResourceService resourceService;
    private final CoffeeMachineService machineService;

    public ResponseMessageDTO getResponseMessageDTO(
            StateMachine<States, Events> stateMachine,
            Events event,
            States state,
            Boolean resetResources
    ) {
        final States curState = stateMachine.getState().getId();
        final String curUUID = stateMachine.getUuid().toString();
        final String curID = stateMachine.getId();
        LogHelper.logAfterSend(event, curState, curUUID, curID);

        if (curState == state) {
            final Resource resource = resetResources
                    ? resourceService.createFull()
                    : machineService.findLatestResourceByUUID(curUUID);

            final CoffeeMachine coffeeMachine = new CoffeeMachine()
                    .setMachineUUID(curUUID)
                    .setMachineId(curID)
                    .setState(curState)
                    .setResource(resource);

            machineService.persistCoffeeMachine(coffeeMachine);
        }

        return new ResponseMessageDTO()
                .setText(String.format("After event %S state is %s", event, curState))
                .setState(curState);
    }
}
