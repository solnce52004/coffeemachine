package ru.example.coffeemachine.service.modelservice;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.example.coffeemachine.entity.Resource;
import ru.example.coffeemachine.repo.ResourceRepository;

@Service
@AllArgsConstructor
@Slf4j
public class ResourceService {
    private final ResourceRepository resourceRepository;

    public Resource createFull() {
        final Resource resource = new Resource();
        resource.setWater(100);
        resource.setCoffee(100);
        return resourceRepository.save(resource);
    }

    public Resource createEmpty() {
        return resourceRepository.save(new Resource());
    }
}