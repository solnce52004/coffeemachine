package ru.example.coffeemachine.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.coffeemachine.entity.StateMachine;

@Repository
public interface StateMachineRepository extends JpaRepository<StateMachine, Long> {
}