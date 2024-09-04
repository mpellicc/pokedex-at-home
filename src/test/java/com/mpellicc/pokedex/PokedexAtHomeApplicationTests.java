package com.mpellicc.pokedex;

import com.mpellicc.pokedex.controller.PokedexController;
import com.mpellicc.pokedex.service.PokedexService;
import com.mpellicc.pokedex.webclient.FunTranslationsRestClient;
import com.mpellicc.pokedex.webclient.PokeApiRestClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PokedexAtHomeApplicationTests {

    @Autowired
    private PokedexController pokedexController;

    @Autowired
    private PokedexService pokedexService;

    @Autowired
    private PokeApiRestClient pokeApiRestClient;

    @Autowired
    private FunTranslationsRestClient funTranslationsRestClient;

    @Test
    void contextLoads() {
        assertThat(pokedexController).isNotNull();
        assertThat(pokedexService).isNotNull();
        assertThat(pokeApiRestClient).isNotNull();
        assertThat(funTranslationsRestClient).isNotNull();
    }

}
