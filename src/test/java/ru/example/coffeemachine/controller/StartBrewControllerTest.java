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

@WebMvcTest(controllers = {StartBrewController.class})
class StartBrewControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SendEventService sendEventService;
    @MockBean
    private ResourceRepository resourceRepository;
    @MockBean
    private CoffeeMachineRepository coffeeMachineRepository;

    @Test
    void pushStart() throws Exception {
        ResponseMessageDTO msg = new ResponseMessageDTO()
                .setText("success")
                .setDebug("debug")
                .setEvent(Events.PUSH_START_BREW)
                .setState(States.DONE)
                .setHttpStatus(HttpStatus.OK);

        Mockito.when(sendEventService.send(Events.PUSH_START_BREW))
                .thenReturn(msg);

        mvc.perform(get("/api/v1/start-brew")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is("success")))
                .andExpect(jsonPath("$.debug", is("debug")))
                .andExpect(jsonPath("$.event", is(Events.PUSH_START_BREW.name())))
                .andExpect(jsonPath("$.state", is(States.DONE.name())))
                .andExpect(jsonPath("$.httpStatus", is(HttpStatus.OK.name())));
    }
}