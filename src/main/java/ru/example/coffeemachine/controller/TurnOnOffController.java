package ru.example.coffeemachine.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.coffeemachine.config.statemachine.enums.States;
import ru.example.coffeemachine.dto.ResponseMessageDTO;
import ru.example.coffeemachine.service.api.SendEventService;

@RestController
@RequestMapping(
        path = "/api/v1",
        produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "turn on/off", value = "TurnOnOffController")
@AllArgsConstructor
@Slf4j
public class TurnOnOffController {
    private final SendEventService sendEventService;

    @ApiOperation(value = "Нажать кнопку ВКЛЮЧИТЬ")
    @GetMapping("/turn-on")
    public ResponseEntity<ResponseMessageDTO> pushTurnOn() {
        final ResponseMessageDTO msg = sendEventService.turnOn();

        HttpStatus httpStatus = msg.getState() != States.TURNED_ON
                ? HttpStatus.METHOD_NOT_ALLOWED
                : HttpStatus.OK;

        return new ResponseEntity<>(msg, httpStatus);
    }

    @ApiOperation(value = "Нажать кнопку ВЫКЛЮЧИТЬ")
    @GetMapping("/turn-off")
    public ResponseEntity<ResponseMessageDTO> pushTurnOff() {
        final ResponseMessageDTO msg = sendEventService.turnOff();

        log.info("TurnOnOffController pushTurnOff msg {}", msg);

        HttpStatus httpStatus = msg.getState() != States.TURNED_OFF
                ? HttpStatus.METHOD_NOT_ALLOWED
                : HttpStatus.OK;

        return new ResponseEntity<>(msg, httpStatus);
    }
}

