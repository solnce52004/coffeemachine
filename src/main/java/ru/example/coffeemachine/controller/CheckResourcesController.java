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
import ru.example.coffeemachine.service.CheckResourcesService;

@RestController
@RequestMapping(
        path ="/api/v1",
        produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "check resources", value = "CheckResourcesController")
@AllArgsConstructor
public class CheckResourcesController {
    private final CheckResourcesService checkResourcesService;

    @ApiOperation(value = "Проверка/пополнение ресурсов (вода, кофе)")
    @GetMapping("/check")
    public ResponseEntity<ResponseMessageDTO> check() {
        final ResponseMessageDTO msg = checkResourcesService.checkResources();

        if (msg.getState() != States.CHECKED_RESOURCES) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_MODIFIED);
        }

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}