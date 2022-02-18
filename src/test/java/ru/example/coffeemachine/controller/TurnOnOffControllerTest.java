package ru.example.coffeemachine.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.States;
import ru.example.coffeemachine.dto.ResponseMessageDTO;
import ru.example.coffeemachine.repo.CoffeeMachineRepository;
import ru.example.coffeemachine.repo.ResourceRepository;
import ru.example.coffeemachine.service.api.SendEventService;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {TurnOnOffController.class})
class TurnOnOffControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SendEventService sendEventService;
    @MockBean
    private ResourceRepository resourceRepository;
    @MockBean
    private CoffeeMachineRepository coffeeMachineRepository;

    @Test
    public void pushTurnOn() throws Exception {
        ResponseMessageDTO msg = new ResponseMessageDTO()
                .setText("success")
                .setDebug("debug")
                .setEvent(Events.PUSH_TURN_ON)
                .setState(States.TURNED_ON)
                .setHttpStatus(HttpStatus.OK);

        Mockito.when(sendEventService.send(Events.PUSH_TURN_ON))
                .thenReturn(msg);

        mvc.perform(get("/api/v1/turn-on")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is("success")))
                .andExpect(jsonPath("$.debug", is("debug")))
                .andExpect(jsonPath("$.event", is(Events.PUSH_TURN_ON.name())))
                .andExpect(jsonPath("$.state", is(States.TURNED_ON.name())))
                .andExpect(jsonPath("$.httpStatus", is(HttpStatus.OK.name())));
    }

    @Test
    void pushTurnOff() throws Exception {
        ResponseMessageDTO msg = new ResponseMessageDTO()
                .setText("success")
                .setDebug("debug")
                .setEvent(Events.PUSH_TURN_OFF)
                .setState(States.TURNED_OFF)
                .setHttpStatus(HttpStatus.OK);

        Mockito.when(sendEventService.send(Events.PUSH_TURN_OFF))
                .thenReturn(msg);

        mvc.perform(get("/api/v1/turn-off")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is("success")))
                .andExpect(jsonPath("$.debug", is("debug")))
                .andExpect(jsonPath("$.event", is(Events.PUSH_TURN_OFF.name())))
                .andExpect(jsonPath("$.state", is(States.TURNED_OFF.name())))
                .andExpect(jsonPath("$.httpStatus", is(HttpStatus.OK.name())));
    }
}