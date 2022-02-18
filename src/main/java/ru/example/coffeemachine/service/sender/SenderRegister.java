package ru.example.coffeemachine.service.sender;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.example.coffeemachine.config.statemachine.enums.Events;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class SenderRegister {
    private final Map<Events, Sender> map = new HashMap<>();

    public void register(Events event, Sender sender){
        map.put(event, sender);
    }

    public Sender getSender(Events event){
        return getMap().get(event);
    }
}
