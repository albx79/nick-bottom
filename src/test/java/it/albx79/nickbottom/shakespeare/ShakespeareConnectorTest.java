package it.albx79.nickbottom.shakespeare;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.albx79.nickbottom.AbstractWiremockTest;
import okhttp3.OkHttpClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ShakespeareConnectorTest extends AbstractWiremockTest {

    ShakespeareConnector shakespeare;

    @BeforeEach
    void setup() {
        shakespeare = new ShakespeareConnector("http://localhost:" + getWiremockPort(), getHttpClient(), getObjectMapper());
    }

    @Test
    void translate_charizard_flavor_text() {
        String translated = shakespeare.shakespearify("CHARIZARD flies around the sky in\nsearch of powerful opponents.");

        assertThat(translated).isEqualTo("Charizard flies 'round the sky in search of powerful opponents.");
    }
}