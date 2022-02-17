package ru.example.coffeemachine.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.example.coffeemachine.entity.CoffeeMachine;

import java.util.Optional;

@Repository
public interface CoffeeMachineRepository extends JpaRepository<CoffeeMachine, Long> {

    @Query("select distinct m1 from CoffeeMachine m1 where m1.machineUUID = :uuid and not exists (select m2 from CoffeeMachine m2 where m2.createdAt > m1.createdAt)")
    Optional<CoffeeMachine> findLatestByUUID(@Param("uuid") String uuid);
}
