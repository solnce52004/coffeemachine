package ru.example.coffeemachine.domain.commnd;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.example.coffeemachine.config.statemachine.enums.Events;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class CommandRegister {
    private final Map<Events, Command> map = new HashMap<>();

    public void register(Events event, Command commandAction){
        map.put(event, commandAction);
    }

    public Command getCommand(Events event){
        return getMap().get(event);
    }
}
