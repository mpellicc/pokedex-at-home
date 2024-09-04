package com.mpellicc.pokedex.webclient;

import com.mpellicc.pokedex.dto.webclient.FunTranslationsDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class FunTranslationsRestClient {
    private final RestClient restClient;

    public FunTranslationsRestClient(@Value("${webclient.fun-translations.base-url}") String baseUrl) {
        this.restClient = RestClient.create(baseUrl);
    }

    public String translate(String text, Language lang) {
        FunTranslationsDto response = restClient.get()
                .uri(lang.api + "?text={text}", text)
                .retrieve()
                .body(FunTranslationsDto.class);

        if (response == null || response.getContents() == null) {
            return null;
        }

        return response.getContents().getTranslated();
    }

    public enum Language {
        YODA("/yoda"),
        SHAKESPEARE("/shakespeare");

        private final String api;

        Language(String api) {
            this.api = api;
        }
    }
}
