package com.mpellicc.pokedex.webclient;

import com.mpellicc.pokedex.dto.webclient.FunTranslationsDto;
import com.mpellicc.pokedex.exception.FunTranslationsException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
public class FunTranslationsRestClient {
    private final RestClient restClient;

    public FunTranslationsRestClient(@Value("${webclient.fun-translations.base-url}") String baseUrl) {
        this.restClient = RestClient.create(baseUrl);
    }

    public String translate(String text, Language lang) {
        log.info("Calling funtranslations with language {}", lang.name().toLowerCase());

        FunTranslationsDto response = restClient.get()
                .uri(lang.getApi() + "?text={text}", text)
                .retrieve()
                .body(FunTranslationsDto.class);

        if (response == null || FunTranslationsDto.Content.isNull(response.getContents())) {
            throw new FunTranslationsException();
        }

        return response.getContents().getTranslated();
    }

    @Getter
    public enum Language {
        YODA("/yoda"),
        SHAKESPEARE("/shakespeare");

        private final String api;

        Language(String api) {
            this.api = api;
        }
    }
}
