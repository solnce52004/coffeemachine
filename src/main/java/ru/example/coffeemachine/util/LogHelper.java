package ru.example.coffeemachine.util;

import lombok.extern.slf4j.Slf4j;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.States;

@Slf4j
public class LogHelper {

    public static void logAfterSend(
            Events event,
            States curState,
            String curUUID,
            String curID
    ) {
        log.info("After event {} state is {}", event, curState);
        log.info("After event {} uuid is {}", event, curUUID);
        log.info("After event {} id is {}", event, curID);
    }
}
