package ru.example.coffeemachine.service.modelservice;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
        coffeeMachineRepository.save(coffeeMachine);
    }

    public Optional<CoffeeMachine> findLatestByStateMachineUUID(String uuid) {
        return coffeeMachineRepository.findLatestByUUID(uuid);
    }

    public Resource findLatestResourceByUUID(String uuid) {
        return findLatestByStateMachineUUID(uuid)
                .map(CoffeeMachine::getResource)
                .orElseGet(resourceService::createEmpty);
    }
}