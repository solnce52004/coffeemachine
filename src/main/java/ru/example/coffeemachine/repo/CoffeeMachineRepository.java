package ru.example.coffeemachine.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.coffeemachine.entity.CoffeeMachine;

@Repository
public interface CoffeeMachineRepository extends JpaRepository<CoffeeMachine, Long> {
}
