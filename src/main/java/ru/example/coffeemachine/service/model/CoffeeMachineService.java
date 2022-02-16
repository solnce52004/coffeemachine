package ru.example.coffeemachine.service.model;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.example.coffeemachine.entity.CoffeeMachine;
import ru.example.coffeemachine.repo.CoffeeMachineRepository;

@Service
@AllArgsConstructor
@Slf4j
public class CoffeeMachineService {
    private final CoffeeMachineRepository coffeeMachineRepository;

    public Boolean createNew() {

        final CoffeeMachine coffeeMachine = new CoffeeMachine();

        coffeeMachineRepository.save(coffeeMachine);
        return true;
    }
}