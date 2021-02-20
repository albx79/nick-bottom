package it.albx79.nickbottom.pokemon;

import it.albx79.nickbottom.pokemon.PokemonSpecies.FlavorTextEntry;
import it.albx79.nickbottom.pokemon.PokemonSpecies.Language;
import kotlin.ranges.IntRange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class RandomFlavorTextTest {

    RandomFlavorText flavorText;

    @BeforeEach
    void setup() {
        flavorText = new RandomFlavorText();
    }

    @Test
    void chose_by_language() {
        PokemonSpecies species = PokemonSpecies.builder()
                .flavorTextEntries(List.of(
                        FlavorTextEntry.builder()
                                .flavorText("text in my language")
                                .language(Language.builder().name("lang").build())
                                .build(),
                        FlavorTextEntry.builder()
                                .flavorText("text in foreign language")
                                .language(Language.builder().name("foreign").build())
                                .build()
                ))
                .build();
        String random = flavorText.randomize(species, "lang");
        assertThat(random).isEqualTo("text in my language");
    }

    /**
     * Testing randomness is always tricky.
     * <p/>
     * Here we create an entry with two matching strings, call the function "many" times,
     * collect all responses, and check that eventually we get both strings.
     */
    @Test
    void given_multiple_matching_strings_then_choose_one_at_random() {
        PokemonSpecies species = PokemonSpecies.builder()
                .flavorTextEntries(List.of(
                        FlavorTextEntry.builder()
                                .flavorText("text 1 in my language")
                                .language(Language.builder().name("lang").build())
                                .build(),
                        FlavorTextEntry.builder()
                                .flavorText("text in foreign language")
                                .language(Language.builder().name("foreign").build())
                                .build(),
                        FlavorTextEntry.builder()
                                .flavorText("text 2 in my language")
                                .language(Language.builder().name("lang").build())
                                .build()
                        ))
                .build();

        Set<String> strings = IntStream.range(0, 100)
                .mapToObj(__ -> flavorText.randomize(species, "lang"))
                .collect(Collectors.toSet());

        assertThat(strings).isEqualTo(Set.of("text 1 in my language", "text 2 in my language"));
    }
}