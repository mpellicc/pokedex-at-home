package com.mpellicc.pokedex.webclient;

import com.mpellicc.pokedex.dto.webclient.PokeApiDto;
import com.mpellicc.pokedex.enumeration.ErrorMessage;
import com.mpellicc.pokedex.exception.PokemonNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;

@Service
public class PokeApiRestClient {
    private static final String POKEMON_SPECIES_API = "/pokemon-species/{name}";

    private final RestClient restClient;

    public PokeApiRestClient(@Value("${webclient.pokeapi.base-url}") String baseUrl) {
        this.restClient = RestClient.create(baseUrl);
    }

    public PokeApiDto getPokemon(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException(ErrorMessage.POKEMON_NAME_BLANK.getMessage());
        }

        try {
            return restClient.get().uri(POKEMON_SPECIES_API, name).retrieve().body(PokeApiDto.class);
        } catch (HttpStatusCodeException ex) {

            if (HttpStatus.NOT_FOUND.equals(ex.getStatusCode())) {
                throw new PokemonNotFoundException(name);
            }

            throw ex;
        }

    }
}
