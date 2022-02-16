package ru.example.coffeemachine.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.coffeemachine.config.statemachine.enums.States;
import ru.example.coffeemachine.dto.ResponseMessageDTO;
import ru.example.coffeemachine.service.api.TurnOnOffService;

@RestController
@RequestMapping(
        path ="/api/v1/turn",
        produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "turn on/off", value = "TurnOnOffController")
@AllArgsConstructor
public class TurnOnOffController {
    private final TurnOnOffService turnOnOffService;

    @ApiOperation(value = "Нажать кнопку ВКЛЮЧИТЬ")
    @GetMapping( "/on")
    public ResponseEntity<ResponseMessageDTO> pushTurnOn() {
        final ResponseMessageDTO msg = turnOnOffService.turnOn();

        if (msg.getState() != States.TURNED_ON) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_MODIFIED);
        }

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @ApiOperation(value = "Нажать кнопку ВЫКЛЮЧИТЬ")
    @GetMapping("/off")
    public ResponseEntity<ResponseMessageDTO> pushTurnOff() {
        final ResponseMessageDTO msg = turnOnOffService.turnOff();

        if (msg.getState() != States.TURNED_OFF) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_MODIFIED);
        }

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}

