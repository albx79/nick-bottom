package it.albx79.nickbottom.pokemon;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import okhttp3.OkHttpClient;
import okhttp3.Request;

@RequiredArgsConstructor
public class PokemonConnector {

    private final String pokeApiUrl;
    private final OkHttpClient http;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public PokemonSpecies getSpecies(String name) {
        Request request = new Request.Builder().get().url(pokeApiUrl + "/api/v2/pokemon-species/" + name).build();
        try (
                val response = http.newCall(request).execute();
                val body = response.body().byteStream()
        ) {
            if (response.code() == 404) {
                return null;
            }
            return objectMapper.readValue(body, PokemonSpecies.class);
        }
    }
}
