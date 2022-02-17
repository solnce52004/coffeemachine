package ru.example.coffeemachine.service.modelservice;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.example.coffeemachine.entity.CoffeeMachine;
import ru.example.coffeemachine.entity.Resource;
import ru.example.coffeemachine.repo.CoffeeMachineRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CoffeeMachineService {
    private final ResourceService resourceService;
    private final CoffeeMachineRepository coffeeMachineRepository;

    public CoffeeMachine createNew() {
        final Resource resource = resourceService.createEmpty();
        final CoffeeMachine coffeeMachine = new CoffeeMachine();
        coffeeMachine.setResource(resource);
        return coffeeMachineRepository.save(coffeeMachine);
    }

    public void logNewState(CoffeeMachine coffeeMachine) {
        coffeeMachineRepository.save(coffeeMachine);
    }

    public List<CoffeeMachine> findAllByStateMachineUUID(String uuid) {
        return coffeeMachineRepository.findAllByUUID(uuid);
    }

    public Optional<CoffeeMachine> findLatestByStateMachineUUID(String uuid) {
        return coffeeMachineRepository.findLatestByUUID(uuid);
    }

}