package ru.example.coffeemachine.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.dto.ResponseMessageDTO;
import ru.example.coffeemachine.service.api.SendEventService;

@Api(tags = "Activate/deactivate coffee machine - Turn on/off",
        value = "TurnOnOffController")
@RestController
@RequestMapping(
        path = "/api/v1",
        produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class TurnOnOffController {
    private final SendEventService sendEventService;

    @Operation(method = "GET",
            description = "Нажать кнопку ВКЛЮЧИТЬ",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Кофемашина включена",
                            content = {@Content(schema = @Schema(
                                    implementation = ResponseMessageDTO.class))}),
                    @ApiResponse(
                            responseCode = "405",
                            description = "Запуск процесса включения кофемашины не доступен",
                            content = {@Content(schema = @Schema(
                                    implementation = ResponseMessageDTO.class))})})
    @GetMapping("/turn-on")
    public ResponseEntity<ResponseMessageDTO> pushTurnOn() {

        final ResponseMessageDTO msg = sendEventService.send(Events.PUSH_TURN_ON);
        return new ResponseEntity<>(msg, msg.getHttpStatus());
    }

    @Operation(method = "GET",
            description = "Нажать кнопку ВЫКЛЮЧИТЬ",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Кофемашина выключена",
                            content = {@Content(schema = @Schema(
                                    implementation = ResponseMessageDTO.class))}),
                    @ApiResponse(
                            responseCode = "405",
                            description = "Запуск процесса выключения кофемашины не доступен",
                            content = {@Content(schema = @Schema(
                                    implementation = ResponseMessageDTO.class))})})
    @GetMapping("/turn-off")
    public ResponseEntity<ResponseMessageDTO> pushTurnOff() {

        final ResponseMessageDTO msg = sendEventService.send(Events.PUSH_TURN_OFF);
        return new ResponseEntity<>(msg, msg.getHttpStatus());
    }
}

