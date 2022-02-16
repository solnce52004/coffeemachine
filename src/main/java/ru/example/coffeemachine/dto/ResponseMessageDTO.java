package ru.example.coffeemachine.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.example.coffeemachine.config.statemachine.enums.States;

@NoArgsConstructor
@Accessors(chain = true)
@Setter
@Getter
public class ResponseMessageDTO {
    String text;
    States state;
}
