package ru.example.coffeemachine.domain.commnd;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.Guards;
import ru.example.coffeemachine.config.statemachine.enums.States;
import ru.example.coffeemachine.domain.CoffeeMachineImpl;

@Component
@RequiredArgsConstructor
@Slf4j
public class CheckResourcesCommand implements Command {
    private final CoffeeMachineImpl machine;

    @Override
    public Events getEvent() {
        return Events.CHECK_RESOURCES;
    }

    @Override
    public void execute(StateContext<States, Events> context) {
        machine.checkResources();

        context.getExtendedState()
                .getVariables()
                .put(Guards.IS_CHECKED_RESOURCES.name(), true);

        log.info("Event: {}", context.getEvent());
    }
}