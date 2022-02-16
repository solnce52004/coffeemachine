package ru.example.coffeemachine.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.coffeemachine.entity.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
}