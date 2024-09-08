package com.mpellicc.pokedex.service;

import com.mpellicc.pokedex.dto.PokemonDto;
import com.mpellicc.pokedex.dto.webclient.PokeApiDto;
import com.mpellicc.pokedex.mapper.PokeApiDto2PokemonDtoMapper;
import com.mpellicc.pokedex.webclient.FunTranslationsRestClient;
import com.mpellicc.pokedex.webclient.PokeApiRestClient;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PokedexServiceTest {
    private final PokeApiDto2PokemonDtoMapper pokemonDtoMapper = new PokeApiDto2PokemonDtoMapper();
    @Mock
    private PokeApiRestClient pokeApiRestClient;
    @Mock
    private FunTranslationsRestClient funTranslationsRestClient;
    private PokedexService pokedexService;

    @BeforeEach
    void setUp() {
        pokedexService = new PokedexService(pokeApiRestClient, funTranslationsRestClient, pokemonDtoMapper);
    }

    @Test
    void getPokemon() {
        // given
        PokeApiDto.PokemonHabitat habitat = new PokeApiDto.PokemonHabitat();
        habitat.setName("rare");

        List<PokeApiDto.FlavorText> flavorTexts = IntStream.range(0, 3)
                .mapToObj(i -> new EasyRandom().nextObject(PokeApiDto.FlavorText.class).toBuilder()
                        .language(PokeApiDto.FlavorText.Language.builder().name("en").build())
                        .build())
                .toList();

        PokeApiDto dto = new EasyRandom().nextObject(PokeApiDto.class).toBuilder()
                .habitat(habitat)
                .flavorTextEntries(flavorTexts)
                .build();
        doReturn(dto).when(pokeApiRestClient).getPokemon(anyString());

        // when
        PokemonDto result = pokedexService.getPokemon("test");

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(dto.getName());
        assertThat(result.getHabitat()).isEqualTo(dto.getHabitat().getName());
        assertThat(dto.getFlavorTextEntries().stream().map(PokeApiDto.FlavorText::getFlavorText))
                .contains(result.getDescription());
        assertThat(result.isLegendary()).isEqualTo(dto.isLegendary());
    }

    @Test
    void getTranslatedPokemon_withYodaLanguage_whenHabitatIsCave() {
        // given
        PokeApiDto.PokemonHabitat habitat = new PokeApiDto.PokemonHabitat();
        habitat.setName("cave");

        List<PokeApiDto.FlavorText> flavorTexts = IntStream.range(0, 3)
                .mapToObj(i -> new EasyRandom().nextObject(PokeApiDto.FlavorText.class).toBuilder()
                        .language(PokeApiDto.FlavorText.Language.builder().name("en").build())
                        .build())
                .toList();

        PokeApiDto pokeDto = new EasyRandom().nextObject(PokeApiDto.class).toBuilder()
                .isLegendary(false)
                .habitat(habitat)
                .flavorTextEntries(flavorTexts)
                .build();
        doReturn(pokeDto).when(pokeApiRestClient).getPokemon(anyString());

        doReturn("translated")
                .when(funTranslationsRestClient).translate(anyString(), any(FunTranslationsRestClient.Language.class));

        // when
        PokemonDto result = pokedexService.getTranslatedPokemon("test");

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(pokeDto.getName());
        assertThat(result.getHabitat()).isEqualTo(pokeDto.getHabitat().getName());
        assertThat(result.isLegendary()).isEqualTo(pokeDto.isLegendary());

        verify(funTranslationsRestClient).translate(anyString(), eq(FunTranslationsRestClient.Language.YODA));
    }

    @Test
    void getTranslatedPokemon_withYodaLanguage_whenIsLegendary() {
        // given
        List<PokeApiDto.FlavorText> flavorTexts = IntStream.range(0, 3)
                .mapToObj(i -> new EasyRandom().nextObject(PokeApiDto.FlavorText.class).toBuilder()
                        .language(PokeApiDto.FlavorText.Language.builder().name("en").build())
                        .build())
                .toList();

        PokeApiDto pokeDto = new EasyRandom().nextObject(PokeApiDto.class).toBuilder()
                .isLegendary(true)
                .habitat(null)
                .flavorTextEntries(flavorTexts)
                .build();
        doReturn(pokeDto).when(pokeApiRestClient).getPokemon(anyString());

        doReturn("translated")
                .when(funTranslationsRestClient).translate(anyString(), any(FunTranslationsRestClient.Language.class));

        // when
        PokemonDto result = pokedexService.getTranslatedPokemon("test");

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(pokeDto.getName());
        assertThat(result.getHabitat()).isNull();
        assertThat(result.isLegendary()).isEqualTo(pokeDto.isLegendary());

        verify(funTranslationsRestClient).translate(anyString(), eq(FunTranslationsRestClient.Language.YODA));
    }

    @Test
    void getTranslatedPokemon_withShakespeareLanguage() {
        // given
        PokeApiDto.PokemonHabitat habitat = new PokeApiDto.PokemonHabitat();
        habitat.setName("rare");

        List<PokeApiDto.FlavorText> flavorTexts = IntStream.range(0, 3)
                .mapToObj(i -> new EasyRandom().nextObject(PokeApiDto.FlavorText.class).toBuilder()
                        .language(PokeApiDto.FlavorText.Language.builder().name("en").build())
                        .build())
                .toList();

        PokeApiDto pokeDto = new EasyRandom().nextObject(PokeApiDto.class).toBuilder()
                .isLegendary(false)
                .habitat(habitat)
                .flavorTextEntries(flavorTexts)
                .build();
        doReturn(pokeDto).when(pokeApiRestClient).getPokemon(anyString());

        doReturn("translated")
                .when(funTranslationsRestClient).translate(anyString(), any(FunTranslationsRestClient.Language.class));

        // when
        PokemonDto result = pokedexService.getTranslatedPokemon("test");

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(pokeDto.getName());
        assertThat(result.getHabitat()).isEqualTo(pokeDto.getHabitat().getName());
        assertThat(result.isLegendary()).isEqualTo(pokeDto.isLegendary());

        verify(funTranslationsRestClient).translate(anyString(), eq(FunTranslationsRestClient.Language.SHAKESPEARE));
    }

    @Test
    void getTranslatedPokemon_withDefaultDescription_whenFunTranslationsKo() {
        // given
        PokeApiDto.PokemonHabitat habitat = new PokeApiDto.PokemonHabitat();
        habitat.setName("rare");

        List<PokeApiDto.FlavorText> flavorTexts = IntStream.range(0, 3)
                .mapToObj(i -> new EasyRandom().nextObject(PokeApiDto.FlavorText.class).toBuilder()
                        .language(PokeApiDto.FlavorText.Language.builder().name("en").build())
                        .build())
                .toList();

        PokeApiDto pokeDto = new EasyRandom().nextObject(PokeApiDto.class).toBuilder()
                .isLegendary(false)
                .habitat(habitat)
                .flavorTextEntries(flavorTexts)
                .build();
        doReturn(pokeDto).when(pokeApiRestClient).getPokemon(anyString());

        doThrow(new HttpClientErrorException(HttpStatusCode.valueOf(404)))
                .when(funTranslationsRestClient).translate(anyString(), any(FunTranslationsRestClient.Language.class));

        // when
        PokemonDto result = pokedexService.getTranslatedPokemon("test");

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(pokeDto.getName());
        assertThat(result.getHabitat()).isEqualTo(pokeDto.getHabitat().getName());
        assertThat(result.isLegendary()).isEqualTo(pokeDto.isLegendary());
        assertThat(pokeDto.getFlavorTextEntries().stream().map(PokeApiDto.FlavorText::getFlavorText))
                .contains(result.getDescription());
    }

    @Test
    void getTranslatedPokemon_shouldThrowIllegalArgumentException_whenDescriptionIsBlank() {
        // given
        PokeApiDto pokeDto = new EasyRandom().nextObject(PokeApiDto.class).toBuilder()
                .isLegendary(false)
                .flavorTextEntries(Collections.emptyList())
                .build();
        doReturn(pokeDto).when(pokeApiRestClient).getPokemon(anyString());

        // when
        Executable exec = () -> pokedexService.getTranslatedPokemon("test");

        // then
        assertThrows(IllegalArgumentException.class, exec);
    }

    @Test
    void getTranslatedPokemon_shouldThrowIllegalArgumentException_whenPokemonIsNull() {
        // given
        doReturn(null).when(pokeApiRestClient).getPokemon(anyString());

        // when
        Executable exec = () -> pokedexService.getTranslatedPokemon("test");

        // then
        assertThrows(IllegalArgumentException.class, exec);
    }
}