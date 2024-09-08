package com.mpellicc.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PokemonDto {
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("habitat")
    private String habitat;

    @JsonProperty("isLegendary")
    private boolean isLegendary;

    @JsonProperty("isMythical")
    private boolean isMythical;
}
