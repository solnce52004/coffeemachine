package ru.example.coffeemachine.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.wrapper.SendWrapper;
import ru.example.coffeemachine.dto.ResponseMessageDTO;
import ru.example.coffeemachine.service.sender.Sender;
import ru.example.coffeemachine.service.sender.SenderRegister;
import ru.example.coffeemachine.util.LogHelper;

import java.util.List;

@Service
public class SendEventService {
    private final SenderRegister register;
    private final SendWrapper stateMachineWrapper;

    @Autowired
    public SendEventService(
            SenderRegister register,
            List<Sender> senders,
            SendWrapper stateMachineWrapper
    ) {
        this.register = register;
        this.stateMachineWrapper = stateMachineWrapper;
        LogHelper.logSenderList(senders);
    }

    public ResponseMessageDTO send(Events event) {
        return register
                .getSender(event)
                .sendEvent(stateMachineWrapper);
    }
}
