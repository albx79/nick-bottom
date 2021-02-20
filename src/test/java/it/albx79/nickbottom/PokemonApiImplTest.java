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
    }

    @Test
    void returns_shakespearified_pokemon_description() {
        when(shakespeareConnector.shakespearify(originalDescription)).thenReturn(modifiedDescription);
        when(pokemonConnector.getSpecies(name)).thenReturn(species);
        when(randomFlavorText.randomize(species, "en")).thenReturn(originalDescription);

        ResponseEntity<Pokemon> result = pokemonApi.getSpecies(name);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getName()).isEqualTo(name);
        assertThat(result.getBody().getDescription()).isEqualTo(modifiedDescription);
    }
}
