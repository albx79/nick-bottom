package it.albx79.nickbottom;

import it.albx79.nickbottom.pokemon.PokemonConnector;
import it.albx79.nickbottom.pokemon.RandomFlavorText;
import it.albx79.nickbottom.rest.api.PokemonApiDelegate;
import it.albx79.nickbottom.rest.model.Pokemon;
import it.albx79.nickbottom.shakespeare.ShakespeareConnector;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Service
@RequiredArgsConstructor
public class PokemonApiImpl implements PokemonApiDelegate {

    private static final Logger logger = LoggerFactory.getLogger(PokemonApiImpl.class);

    private final PokemonConnector pokemon;
    private final RandomFlavorText randomFlavorText;
    private final ShakespeareConnector shakespeare;

    @Override
    public CompletableFuture<ResponseEntity<Pokemon>> getDescription(String name) {
        return supplyAsync(() -> Optional.ofNullable(pokemon.getSpecies(name))
                .map(species -> randomFlavorText.randomize(species, "en")))
                .thenApply(maybeText -> maybeText.map(this::translate)
                        .map(description -> new Pokemon().name(name).description(description))
                        .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build())
                );
    }

    private String translate(String flavorText) {
        try {
            return shakespeare.shakespearify(flavorText);
        } catch (Exception e) {
            logger.warn("unable to translate " + flavorText, e);
            return flavorText;
        }
    }
}
