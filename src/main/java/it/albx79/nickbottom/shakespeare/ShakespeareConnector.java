package it.albx79.nickbottom.shakespeare;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class ShakespeareConnector {

    private final String shakespeareApiUrl;
    private final OkHttpClient http;
    private final ObjectMapper objectMapper;

    /**
     * Query the shakespeare translation API with the given input string, and return its translation.
     * <p/>
     * Because of limitations of the translation API, newline characters in the input string will be converted
     * to spaces.
     */
    @SneakyThrows
    public String shakespearify(@NonNull String input) {
        input = input.replace('\n', ' ');

        RequestBody requestBody = new FormBody.Builder(StandardCharsets.UTF_8)
                .add("text", input)
                .build();
        Request request = new Request.Builder()
                .url(shakespeareApiUrl + "/translate/shakespeare.json")
                .post(requestBody)
                .build();

        try (
                val response = http.newCall(request).execute();
                val body = response.body().byteStream();
        ) {
            return objectMapper.readValue(body, Translation.class).getContents().getTranslated();
        }
    }
}
