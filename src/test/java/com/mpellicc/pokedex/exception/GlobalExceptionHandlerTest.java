package com.mpellicc.pokedex.exception;

import com.mpellicc.pokedex.controller.PokedexController;
import com.mpellicc.pokedex.enumeration.ErrorMessage;
import com.mpellicc.pokedex.service.PokedexService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.HttpClientErrorException;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PokedexController.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PokedexService pokedexService;

    @Test
    void handlePokemonNotFoundException() throws Exception {
        String name = "TEST";
        doThrow(new PokemonNotFoundException(name))
                .when(pokedexService).getPokemon(name);

        ResultActions result = mockMvc.perform(get("/pokemon/" + name));

        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.message",
                        is(String.format(ErrorMessage.POKEMON_NOT_FOUND.getMessage(), name))));
    }

    @Test
    void handleHttpStatusCodeException() throws Exception {
        doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad Request"))
                .when(pokedexService).getPokemon(any());

        ResultActions result = mockMvc.perform(get("/pokemon/TEST"));

        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.message",
                        is(String.format(ErrorMessage.GENERIC_FORMATTED.getMessage(), "400 Bad Request"))));
    }

    @Test
    void handleIllegalArgumentException() throws Exception {
        doThrow(new IllegalArgumentException(ErrorMessage.POKEMON_NAME_BLANK.getMessage()))
                .when(pokedexService).getPokemon(any());

        ResultActions result = mockMvc.perform(get("/pokemon/" + null));

        result.andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status", is(HttpStatus.INTERNAL_SERVER_ERROR.value())))
                .andExpect(jsonPath("$.message",
                        is(String.format(
                                ErrorMessage.GENERIC_FORMATTED.getMessage(),
                                ErrorMessage.POKEMON_NAME_BLANK.getMessage())
                        )));
    }

    @Test
    void handleException() throws Exception {
        doAnswer(i -> new Exception("Generic exception"))
                .when(pokedexService).getPokemon(any());

        ResultActions result = mockMvc.perform(get("/pokemon/TEST"));

        result.andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status", is(HttpStatus.INTERNAL_SERVER_ERROR.value())))
                .andExpect(jsonPath("$.message", is(ErrorMessage.GENERIC.getMessage())));
    }
}
