package it.albx79.nickbottom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.Getter;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class AbstractWiremockTest {

    private static final WireMockServer WIRE_MOCK_SERVER = new WireMockServer(wireMockConfig().dynamicPort());
    public static int getWiremockPort() {
        return WIRE_MOCK_SERVER.port();
    };

    @Getter
    private final OkHttpClient httpClient = new OkHttpClient();
    @Getter
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void startWiremock() {
        WIRE_MOCK_SERVER.start();
    }

    @AfterAll
    static void stopWiremock() {
        WIRE_MOCK_SERVER.stop();
    }

}
