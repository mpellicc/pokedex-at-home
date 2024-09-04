package com.mpellicc.pokedex.dto.webclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PokeApiDto {
    @JsonProperty("name")
    private String name;

    @JsonProperty("is_legendary")
    private boolean isLegendary;

    @JsonProperty("flavor_text_entries")
    @Builder.Default
    private List<FlavorText> flavorTextEntries = new ArrayList<>();

    @JsonProperty("habitat")
    private PokemonHabitat habitat;

    @Getter
    @Setter
    public static class PokemonHabitat {
        @JsonProperty("name")
        private String name;
    }

    @Getter
    @Setter
    @Builder(toBuilder = true)
    public static class FlavorText {
        @JsonProperty("flavor_text")
        private String flavorText;

        @JsonProperty("language")
        private Language language;

        @Getter
        @Setter
        @Builder(toBuilder = true)
        public static class Language {
            @JsonProperty("name")
            private String name;
        }
    }
}




