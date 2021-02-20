package it.albx79.nickbottom;

import com.flextrade.jfixture.FixtureAnnotations;
import com.flextrade.jfixture.annotations.Fixture;
import it.albx79.nickbottom.pokemon.PokemonConnector;
import it.albx79.nickbottom.pokemon.PokemonSpecies;
import it.albx79.nickbottom.pokemon.RandomFlavorText;
import it.albx79.nickbottom.rest.model.Pokemon;
import it.albx79.nickbottom.shakespeare.ShakespeareConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class PokemonApiImplTest {

    PokemonApiImpl pokemonApi;

    @Fixture
    String name;
    @Fixture
    PokemonSpecies species;
    @Fixture
    String originalDescription;
    @Fixture
    String modifiedDescription;

    @Mock
    PokemonConnector pokemonConnector;
    @Mock
    RandomFlavorText randomFlavorText;
    @Mock
    ShakespeareConnector shakespeareConnector;

    @BeforeEach
    void setup() {
        FixtureAnnotations.initFixtures(this);
        MockitoAnnotations.openMocks(this);

        pokemonApi = new PokemonApiImpl(pokemonConnector, randomFlavorText, shakespeareConnector);

        when(shakespeareConnector.shakespearify(originalDescription)).thenReturn(modifiedDescription);
        when(pokemonConnector.getSpecies(name)).thenReturn(species);
        when(randomFlavorText.randomize(species, "en")).thenReturn(originalDescription);
    }

    @Test
    void returns_shakespearified_pokemon_description() throws ExecutionException, InterruptedException {
        ResponseEntity<Pokemon> result = pokemonApi.getDescription(name).get();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getName()).isEqualTo(name);
        assertThat(result.getBody().getDescription()).isEqualTo(modifiedDescription);
    }

    @Test
    void given_that_translation_fails_then_return_original_description() throws ExecutionException, InterruptedException {
        when(shakespeareConnector.shakespearify(originalDescription)).thenThrow(new RuntimeException());

        Pokemon pokemon = pokemonApi.getDescription(name).get().getBody();

        assertThat(pokemon.getDescription()).isEqualTo(originalDescription);
    }

    @Test
    void given_that_pokemon_doesnt_exist_then_return_NOT_FOUND() throws ExecutionException, InterruptedException {
        when(pokemonConnector.getSpecies(name)).thenReturn(null);

        ResponseEntity<Pokemon> response = pokemonApi.getDescription(name).get();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
