package com.mpellicc.pokedex.exception;

import com.mpellicc.pokedex.enumeration.ErrorMessage;

public class FunTranslationsException extends RuntimeException {

    public FunTranslationsException() {
        super(ErrorMessage.FUNTRANSLATION_ERROR.getMessage());
    }

}
