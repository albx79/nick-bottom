package it.albx79.nickbottom.pokemon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Jacksonized
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonSpecies {
    @JsonProperty("flavor_text_entries")
    @NonNull
    List<FlavorTextEntry> flavorTextEntries;

    @Value
    @Jacksonized
    @Builder(toBuilder = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FlavorTextEntry {
        @NonNull
        @JsonProperty("flavor_text")
        String flavorText;
        @NonNull
        Language language;
    }

    @Value
    @Jacksonized
    @Builder(toBuilder = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Language {
        @NonNull
        String name;
    }
}
