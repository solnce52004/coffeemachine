package ru.example.coffeemachine.service.modelservice;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.example.coffeemachine.config.statemachine.enums.States;
import ru.example.coffeemachine.entity.CoffeeMachine;
import ru.example.coffeemachine.entity.Resource;
import ru.example.coffeemachine.repo.CoffeeMachineRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CoffeeMachineService {
    private final ResourceService resourceService;
    private final CoffeeMachineRepository coffeeMachineRepository;

    public void persistCoffeeMachine(CoffeeMachine coffeeMachine) {
        coffeeMachineRepository.saveAndFlush(coffeeMachine);
    }

    public Optional<CoffeeMachine> findLatestByStateMachineUUID(String uuid) {
        return coffeeMachineRepository.findLatestByUUID(uuid);
    }

    public Resource findLatestByUuidOrCreateEmpty(String uuid) {
        return findLatestByStateMachineUUID(uuid)
                .map(CoffeeMachine::getResource)
                .orElseGet(resourceService::createEmpty);
    }

    public Resource findLatestByUuidOrCreateFull(String uuid) {
        return findLatestByStateMachineUUID(uuid)
                .map(CoffeeMachine::getResource)
                .filter(r -> r.getWater() == 100)
                .filter(r -> r.getCoffee() == 100)
                .orElseGet(resourceService::createFull);
    }

    public void checkAndUpdateResource(
            States curState,
            String curUUID,
            String curID,
            Long latestResourceId
    ) {
        Resource resource = resourceService.findById(latestResourceId);

        final CoffeeMachine coffeeMachine = new CoffeeMachine()
                .setMachineUUID(curUUID)
                .setMachineId(curID)
                .setState(curState)
                .setResource(resource);

        persistCoffeeMachine(coffeeMachine);
    }

    public void resetResource(
            States curState,
            String curUUID,
            String curID
    ) {
        checkAndUpdateResource(
                curState, curUUID, curID,
                resourceService.createEmpty().getId());
    }
}
