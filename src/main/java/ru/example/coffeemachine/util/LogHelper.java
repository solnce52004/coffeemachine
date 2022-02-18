package ru.example.coffeemachine.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.States;
import ru.example.coffeemachine.domain.commnd.Command;
import ru.example.coffeemachine.service.sender.Sender;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public final class LogHelper {
    public static void logCommandList(List<Command> commandList) {
        final List<String> list = commandList.stream()
                .map(Command::getEvent)
                .map(Enum::name)
                .peek(log::info)
                .collect(Collectors.toList());
        log.info("Count of commands implements Action: {}", list.size());
    }

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

    public static void logSenderList(List<Sender> senders) {
        final List<String> list = senders.stream()
                .map(Sender::getEvent)
                .map(Enum::name)
                .peek(log::info)
                .collect(Collectors.toList());
        log.info("Count of Senders: {}", list.size());
    }
}
