package ru.example.coffeemachine.domain.commnd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.action.Action;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.States;

public interface Command extends Action<States, Events> {
    Events getEvent();

    @Autowired
    default void registerMySelf(CommandRegister commandRegister){
        commandRegister.register(getEvent(), this);
    }
}
