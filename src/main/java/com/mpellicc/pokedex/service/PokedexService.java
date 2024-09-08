package com.mpellicc.pokedex.service;

import com.mpellicc.pokedex.dto.PokemonDto;
import com.mpellicc.pokedex.enumeration.ErrorMessage;
import com.mpellicc.pokedex.exception.FunTranslationsException;
import com.mpellicc.pokedex.mapper.PokeApiDto2PokemonDtoMapper;
import com.mpellicc.pokedex.webclient.FunTranslationsRestClient;
import com.mpellicc.pokedex.webclient.PokeApiRestClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PokedexService {

    private final PokeApiRestClient pokeApiRestClient;
    private final FunTranslationsRestClient funTranslationsRestClient;
    private final PokeApiDto2PokemonDtoMapper pokemonDtoMapper;

    public PokedexService(PokeApiRestClient pokeApiRestClient, FunTranslationsRestClient funTranslationsRestClient,
                          PokeApiDto2PokemonDtoMapper pokemonDtoMapper) {
        this.pokeApiRestClient = pokeApiRestClient;
        this.funTranslationsRestClient = funTranslationsRestClient;
        this.pokemonDtoMapper = pokemonDtoMapper;
    }

    public PokemonDto getPokemon(String name) {
        return pokemonDtoMapper.apply(pokeApiRestClient.getPokemon(name));
    }

    public PokemonDto getTranslatedPokemon(String name) {
        PokemonDto pokemon = getPokemon(name);
        pokemon.setDescription(translateDescription(pokemon));

        return pokemon;
    }

    private String translateDescription(PokemonDto pokemon) {
        if (pokemon == null || StringUtils.isBlank(pokemon.getDescription())) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_POKEMON.getMessage());
        }

        try {
            /*
             * isLegendary flag is checked first since, if a Pokémon is legendary, the habitat information is not
             * needed. Also, some legendary Pokémon have null habitat. In this way, we can also skip null checking
             * the habitat.
             */
            if (pokemon.isLegendary() || "cave".equals(pokemon.getHabitat())) {
                return funTranslationsRestClient.translate(pokemon.getDescription(),
                        FunTranslationsRestClient.Language.YODA);
            }

            return funTranslationsRestClient.translate(pokemon.getDescription(),
                    FunTranslationsRestClient.Language.SHAKESPEARE);
        } catch (Exception ex) {
            log.warn("Could not translate description, using default one.", ex);
            return pokemon.getDescription();
        }
    }
}
