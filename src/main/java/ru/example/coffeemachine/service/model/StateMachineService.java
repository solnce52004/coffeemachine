package ru.example.coffeemachine.service.model;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.example.coffeemachine.repo.StateMachineRepository;

@Service
@AllArgsConstructor
@Slf4j
public class StateMachineService {
    private final StateMachineRepository stateMachineRepository;

}