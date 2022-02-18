package ru.example.coffeemachine.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.States;

import java.io.Serializable;

@NoArgsConstructor
@Accessors(chain = true)
@Data
public class ResponseMessageDTO implements Serializable {
    private String text;
    private String debug;
    private Events event;
    private States state;
    private HttpStatus httpStatus;
}
