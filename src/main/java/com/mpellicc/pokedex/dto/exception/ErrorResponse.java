package com.mpellicc.pokedex.dto.exception;

import com.mpellicc.pokedex.enumeration.ErrorMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

    private int status;
    private String message;

    public ErrorResponse(final int status) {
        this.status = status;
        this.message = ErrorMessage.GENERIC.getMessage();
    }

    public ErrorResponse(final int status, final String message) {
        this.status = status;
        this.message = message;
    }
}
