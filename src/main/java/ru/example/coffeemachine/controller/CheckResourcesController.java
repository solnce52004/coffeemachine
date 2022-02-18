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

@Api(tags = "Check resources", value = "CheckResourcesController")
@RestController
@RequestMapping(
        path = "/api/v1",
        produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CheckResourcesController {
    private final SendEventService sendEventService;

    @Operation(method = "GET",
            description = "Проверка/пополнение ресурсов (вода, кофе)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Проверка/пополнение ресурсов успешно выполнена",
                            content = {@Content(schema = @Schema(
                                    implementation = ResponseMessageDTO.class))}),
                    @ApiResponse(
                            responseCode = "405",
                            description = "Проверка/пополнение ресурсов не доступен",
                            content = {@Content(schema = @Schema(
                                    implementation = ResponseMessageDTO.class))})})
    @GetMapping("/check-resources")
    public ResponseEntity<ResponseMessageDTO> check() {

        final ResponseMessageDTO msg = sendEventService.send(Events.CHECK_RESOURCES);
        return new ResponseEntity<>(msg, msg.getHttpStatus());
    }
}