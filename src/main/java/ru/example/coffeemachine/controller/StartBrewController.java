package ru.example.coffeemachine.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.dto.ResponseMessageDTO;
import ru.example.coffeemachine.service.api.SendEventService;

@RestController
@RequestMapping(
        path = "/api/v1",
        produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "start brew", value = "StartBrewController")
@AllArgsConstructor
public class StartBrewController {
    private final SendEventService sendEventService;

    @ApiOperation(value = "Нажать кнопку НАЧАТЬ ГОТОВИТЬ КОФЕ")
    @GetMapping("/start-brew")
    public ResponseEntity<ResponseMessageDTO> pushStart() {

        final ResponseMessageDTO msg = sendEventService.send(Events.PUSH_START_BREW);
        return new ResponseEntity<>(msg, msg.getHttpStatus());
    }
}
