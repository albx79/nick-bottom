package it.albx79.nickbottom.shakespeare;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Translation {

    @NonNull
    Contents contents;

    @Value
    @Jacksonized
    @Builder(toBuilder = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Contents {
        @NonNull
        String translated;
    }
}
