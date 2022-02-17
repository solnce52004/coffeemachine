package ru.example.coffeemachine.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.example.coffeemachine.config.statemachine.enums.States;

import java.io.Serializable;

@NoArgsConstructor
@Accessors(chain = true)
@Data
public class ResponseMessageDTO implements Serializable {
    String text;
    States state;
}
