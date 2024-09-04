package com.mpellicc.pokedex.controller;

import com.mpellicc.pokedex.dto.PokemonDto;
import com.mpellicc.pokedex.service.PokedexService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PokedexController.class)
class PokedexControllerTest {
    private static final String BASE_URL = "/pokemon";
    private static final String GET_POKEMON_API = BASE_URL + "/{name}";
    private static final String GET_TRANSLATED_POKEMON_API = BASE_URL + "/translated/{name}";

    @MockBean
    private PokedexService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPokemon() throws Exception {
        PokemonDto expected = new EasyRandom().nextObject(PokemonDto.class);
        doReturn(expected).when(service).getPokemon(anyString());

        mockMvc.perform(get(GET_POKEMON_API, "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.description").value(expected.getDescription()))
                .andExpect(jsonPath("$.habitat").value(expected.getHabitat()))
                .andExpect(jsonPath("$.isLegendary").value(expected.isLegendary()));
    }

    @Test
    void getTranslatedPokemon() throws Exception {
        PokemonDto expected = new EasyRandom().nextObject(PokemonDto.class);
        doReturn(expected).when(service).getTranslatedPokemon(anyString());

        mockMvc.perform(get(GET_TRANSLATED_POKEMON_API, "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.description").value(expected.getDescription()))
                .andExpect(jsonPath("$.habitat").value(expected.getHabitat()))
                .andExpect(jsonPath("$.isLegendary").value(expected.isLegendary()));
    }
}