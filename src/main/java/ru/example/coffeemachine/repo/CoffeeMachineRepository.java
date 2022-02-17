package ru.example.coffeemachine.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.example.coffeemachine.entity.CoffeeMachine;

import java.util.Optional;

@Repository
public interface CoffeeMachineRepository extends JpaRepository<CoffeeMachine, Long> {

    @Query("select distinct m1 from coffee_mashines m1 " +
            "where not exists (select m2 from coffee_mashines m2 where m2.created_at > m1.created_at)" +
            "and m1.uuid = :uuid ")
    Optional<CoffeeMachine> findLatestByUUID(String uuid);
}
