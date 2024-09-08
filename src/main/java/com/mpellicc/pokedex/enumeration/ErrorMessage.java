package com.mpellicc.pokedex.enumeration;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    GENERIC("Something went wrong."),
    GENERIC_FORMATTED("Something went wrong: %s."),
    INVALID_POKEMON("Invalid Pokémon object."),
    POKEMON_NAME_BLANK("Pokémon name cannot be blank."),
    POKEMON_NOT_FOUND("[%s]: Invalid name or Pokémon does not exist."),
    FUNTRANSLATION_ERROR("Could not get a valid translation.")
    ;

    private final String message;

    ErrorMessage(final String message) {
        this.message = message;
    }
}
