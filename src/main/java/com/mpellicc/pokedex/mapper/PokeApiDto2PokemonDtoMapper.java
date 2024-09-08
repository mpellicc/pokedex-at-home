package com.mpellicc.pokedex.mapper;

import com.mpellicc.pokedex.dto.PokemonDto;
import com.mpellicc.pokedex.dto.webclient.PokeApiDto;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

@Service
public class PokeApiDto2PokemonDtoMapper implements Function<PokeApiDto, PokemonDto> {
    private static final Random random = new Random();

    @Override
    public PokemonDto apply(PokeApiDto pokeApiDto) {
        if (pokeApiDto == null) {
            return null;
        }

        return PokemonDto.builder()
                .name(pokeApiDto.getName())
                .description(getDescription(pokeApiDto.getFlavorTextEntries()))
                .habitat(pokeApiDto.getHabitat() == null ?
                        null
                        : pokeApiDto.getHabitat().getName())
                .isLegendary(pokeApiDto.isLegendary())
                .isMythical(pokeApiDto.isMythical())
                .build();
    }

    private String getDescription(List<PokeApiDto.FlavorText> flavorTextEntries) {
        if (CollectionUtils.isEmpty(flavorTextEntries)) {
            return null;
        }

        List<String> flavorTexts = flavorTextEntries.stream()
                .filter(fte -> "en".equalsIgnoreCase(fte.getLanguage().getName()))
                .map(PokeApiDto.FlavorText::getFlavorText)
                .toList();

        return flavorTexts.get(random.nextInt(flavorTexts.size())).replaceAll("[\\t\\n\\r]+", " ");
    }
}
