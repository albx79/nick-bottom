package it.albx79.nickbottom;

import it.albx79.nickbottom.pokemon.PokemonConnector;
import it.albx79.nickbottom.pokemon.PokemonSpecies;
import it.albx79.nickbottom.pokemon.RandomFlavorText;
import it.albx79.nickbottom.rest.api.PokemonApiDelegate;
import it.albx79.nickbottom.rest.model.Pokemon;
import it.albx79.nickbottom.shakespeare.ShakespeareConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PokemonApiImpl implements PokemonApiDelegate {

    private final PokemonConnector pokemon;
    private final RandomFlavorText randomFlavorText;
    private final ShakespeareConnector shakespeare;

    @Override
    public ResponseEntity<Pokemon> getSpecies(String name) {
        PokemonSpecies species = pokemon.getSpecies(name);
        String flavorText = randomFlavorText.randomize(species, "en");
        String shakespeareanDescription = shakespeare.shakespearify(flavorText);
        return ResponseEntity.ok(new Pokemon().name(name).description(shakespeareanDescription));
    }
}
