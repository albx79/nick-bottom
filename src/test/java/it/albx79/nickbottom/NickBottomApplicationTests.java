package it.albx79.nickbottom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flextrade.jfixture.FixtureAnnotations;
import com.flextrade.jfixture.annotations.Fixture;
import it.albx79.nickbottom.rest.api.PokemonApiDelegate;
import it.albx79.nickbottom.rest.model.Pokemon;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.concurrent.CompletableFuture;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class NickBottomApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Fixture
	Pokemon pokemon;

	@MockBean
	PokemonApiDelegate delegate;

	ObjectMapper objectMapper;

	@BeforeEach
	void setup() {
		FixtureAnnotations.initFixtures(this);
		objectMapper = new ObjectMapper();
	}

	@Test
	void getPokemonDescriptions() throws Exception {
		Mockito.when(delegate.getDescription(pokemon.getName())).thenReturn(CompletableFuture.completedFuture(ResponseEntity.ok(pokemon)));

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/pokemon/{name}", pokemon.getName()))
				.andExpect(request().asyncStarted())
				.andExpect(status().isOk())
				.andReturn();

		mockMvc.perform(asyncDispatch(result))
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json(asJsonString(pokemon)));
	}

	@SneakyThrows
	String asJsonString(Object o) {
		return objectMapper.writeValueAsString(o);
	}
}
