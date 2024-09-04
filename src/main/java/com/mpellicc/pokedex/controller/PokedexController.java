package com.mpellicc.pokedex.controller;

import com.mpellicc.pokedex.dto.PokemonDto;
import com.mpellicc.pokedex.service.PokedexService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemon")
public class PokedexController {

    private final PokedexService pokedexService;

    public PokedexController(PokedexService pokedexService) {
        this.pokedexService = pokedexService;
    }

    @GetMapping("/{name}")
    public PokemonDto getPokemon(@PathVariable("name") String name) {
        return pokedexService.getPokemon(name);
    }

    @GetMapping("/translated/{name}")
    public PokemonDto getTranslatedPokemon(@PathVariable("name") String name) {
        return pokedexService.getTranslatedPokemon(name);
    }
}
