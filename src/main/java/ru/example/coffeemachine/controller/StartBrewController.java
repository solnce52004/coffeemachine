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
import ru.example.coffeemachine.service.api.StartBrewService;

@RestController
@RequestMapping(
        path ="/api/v1",
        produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "start brew", value = "StartBrewController")
@AllArgsConstructor
public class StartBrewController {
    private final StartBrewService startBrewService;

    @ApiOperation(value = "Нажать кнопку НАЧАТЬ ГОТОВИТЬ КОФЕ")
    @GetMapping("/start")
    public ResponseEntity<ResponseMessageDTO> pushStart() {
        final ResponseMessageDTO msg = startBrewService.startBrew();

        if (msg.getState() != States.DONE) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_MODIFIED);
        }

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
