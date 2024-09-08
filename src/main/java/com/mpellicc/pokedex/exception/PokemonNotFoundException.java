package com.mpellicc.pokedex.exception;

import com.mpellicc.pokedex.enumeration.ErrorMessage;

public class PokemonNotFoundException extends RuntimeException {

    public PokemonNotFoundException(String name) {
        super(String.format(ErrorMessage.POKEMON_NOT_FOUND.getMessage(), name));
    }
}
