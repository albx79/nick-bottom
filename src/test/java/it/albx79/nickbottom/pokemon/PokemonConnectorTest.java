package it.albx79.nickbottom.pokemon;

import it.albx79.nickbottom.AbstractWiremockTest;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PokemonConnectorTest extends AbstractWiremockTest {
    PokemonConnector connector;

    @BeforeEach
    void setup() {
        connector = new PokemonConnector("http://localhost:" + getWiremockPort(), getHttpClient(), getObjectMapper() );
    }

    @Test
    void retrieve_flavour_text_of_pokemon() {
        PokemonSpecies charizard = connector.getSpecies("charizard");

        assertThat(charizard.getFlavorTextEntries().get(0).getLanguage().getName()).isEqualTo("en");
        assertThat(charizard.getFlavorTextEntries().get(0).getFlavorText())
                .isEqualTo("Spits fire that\nis hot enough to\nmelt boulders.\fKnown to cause\nforest fires\nunintentionally.");
        assertThat(charizard.getFlavorTextEntries().get(153).getLanguage().getName()).isEqualTo("zh-Hans");
        assertThat(charizard.getFlavorTextEntries().get(153).getFlavorText())
                .isEqualTo("２０００度的火焰在体内形成\n了漩涡。当它咆哮的时候，\n火力还会进一步上升。");
    }

    @Test
    void when_pokemon_not_found_then_return_null() {
        Object result = connector.getSpecies("DOES NOT EXIST");

        assertThat(result).isNull();
    }
}