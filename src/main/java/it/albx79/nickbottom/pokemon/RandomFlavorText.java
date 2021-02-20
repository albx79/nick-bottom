package it.albx79.nickbottom.pokemon;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class RandomFlavorText {

    private final Random random = new Random();

    /**
     * Choose a random flavour text string, having the selected {@code language}.
     */
    public String randomize(PokemonSpecies species, String language) {
        List<String> strings = species.getFlavorTextEntries().stream()
                .filter(e -> language.equals(e.getLanguage().getName()))
                .map(PokemonSpecies.FlavorTextEntry::getFlavorText)
                .collect(Collectors.toList());

        return strings.get(random.nextInt(strings.size()));
    }
}
