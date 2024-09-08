package com.mpellicc.pokedex.webclient;

import com.mpellicc.pokedex.exception.FunTranslationsException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpStatusCodeException;

import static org.junit.jupiter.api.Assertions.*;

class FunTranslationsRestClientTest {

    private MockWebServer mockWebServer;
    private FunTranslationsRestClient funTranslationsRestClient;

    @BeforeEach
    void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        String baseUrl = mockWebServer.url("/").toString(); // Mock server base URL
        funTranslationsRestClient = new FunTranslationsRestClient(baseUrl);
    }

    @AfterEach
    void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    /*
     * Probably can change it into ParameterizedTest with FunTranslationsDto.Language values
     */
    @Test
    void translate_ok() {
        //region YODA
        // given
        String mockYodaResponse = "{ \"contents\": { \"translated\": \"yoda translated text\" } }";
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(mockYodaResponse)
                .addHeader("Content-Type", "application/json"));

        // when
        String yodaResponse =
                funTranslationsRestClient.translate("some text", FunTranslationsRestClient.Language.YODA);

        // then
        assertNotNull(yodaResponse);
        assertEquals("yoda translated text", yodaResponse);
        //endregion

        //region SHAKESPEARE
        // given
        String mockShakespeareResponse = "{ \"contents\": { \"translated\": \"shakespeare translated text\" } }";
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(mockShakespeareResponse)
                .addHeader("Content-Type", "application/json"));

        // when
        String shakespeareResponse =
                funTranslationsRestClient.translate("some text", FunTranslationsRestClient.Language.SHAKESPEARE);

        // then
        assertNotNull(shakespeareResponse);
        assertEquals("shakespeare translated text", shakespeareResponse);
        //endregion
    }

    @Test
    void translate_shouldThrowFunTranslationException_whenResponseIsEmpty() {
        // given
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"contents\": {}}")
                .addHeader("Content-Type", "application/json"));

        // when & then
        assertThrows(FunTranslationsException.class,
                () -> funTranslationsRestClient.translate("Hello", FunTranslationsRestClient.Language.SHAKESPEARE));

        // given
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{}")
                .addHeader("Content-Type", "application/json"));

        // when & then
        assertThrows(FunTranslationsException.class,
                () -> funTranslationsRestClient.translate("Hello", FunTranslationsRestClient.Language.YODA));
    }

    @Test
    void translate_shouldThrowFunTranslationException_whenResponseIsNull() {
        // given
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("")
                .addHeader("Content-Type", "application/json"));

        // when & then
        assertThrows(FunTranslationsException.class,
                () -> funTranslationsRestClient.translate("Hello", FunTranslationsRestClient.Language.YODA));
    }

    @Test
    void translate_ko() {
        // given
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody("{\"message\": \"Internal Server Error\"}"));

        // when & then
        assertThrows(HttpStatusCodeException.class,
                () -> funTranslationsRestClient.translate("some text", FunTranslationsRestClient.Language.YODA));
    }
}
