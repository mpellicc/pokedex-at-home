package com.mpellicc.pokedex.webclient;

import com.mpellicc.pokedex.dto.webclient.PokeApiDto;
import com.mpellicc.pokedex.exception.PokemonNotFoundException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpStatusCodeException;

import static org.junit.jupiter.api.Assertions.*;

class PokeApiRestClientTest {

    private MockWebServer mockWebServer;
    private PokeApiRestClient pokeApiRestClient;

    @BeforeEach
    void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        String baseUrl = mockWebServer.url("/").toString(); // Mock server base URL
        pokeApiRestClient = new PokeApiRestClient(baseUrl);
    }

    @AfterEach
    void shutdown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    void getPokemon_ok() throws Exception {
        // given
        String mockResponseBody = "{ \"name\": \"pikachu\" }";
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        // when
        PokeApiDto response = pokeApiRestClient.getPokemon("pikachu");

        // then
        assertNotNull(response);
        assertEquals("pikachu", response.getName());
    }

    @Test
    void getPokemon_shouldThrowPokemonNotFoundException_whenPokeApi404() {
        // given
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody("{\"message\": \"Not Found\"}"));

        // when & then
        assertThrows(PokemonNotFoundException.class, () -> pokeApiRestClient.getPokemon("missingPokemon"));
    }

    @Test
    void getPokemon_shouldThrowIllegalArgumentException_whenPokemonNameIsBlank() {
        // when & then
        assertThrows(IllegalArgumentException.class, () -> pokeApiRestClient.getPokemon(""));
        assertThrows(IllegalArgumentException.class, () -> pokeApiRestClient.getPokemon("   "));
    }

    @Test
    void getPokemon_ko() {
        // given
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody("{\"message\": \"Internal Server Error\"}"));

        // when & then
        assertThrows(HttpStatusCodeException.class, () -> pokeApiRestClient.getPokemon("errorPokemon"));
    }
}
