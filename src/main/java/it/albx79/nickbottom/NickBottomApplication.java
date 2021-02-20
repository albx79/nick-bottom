package it.albx79.nickbottom;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.albx79.nickbottom.pokemon.PokemonConnector;
import it.albx79.nickbottom.shakespeare.ShakespeareConnector;
import okhttp3.OkHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NickBottomApplication {

	public static void main(String[] args) {
		SpringApplication.run(NickBottomApplication.class, args);
	}

	@Bean
	OkHttpClient okHttpClient() {
		return new OkHttpClient();
	}

	@Bean
	ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	PokemonConnector pokemonConnector(OkHttpClient okHttpClient, ObjectMapper objectMapper) {
		return new PokemonConnector("https://pokeapi.co", okHttpClient, objectMapper);
	}

	@Bean
	ShakespeareConnector shakespeareConnector(OkHttpClient okHttpClient, ObjectMapper objectMapper) {
		return new ShakespeareConnector("https://api.funtranslations.com", okHttpClient, objectMapper);
	}
}
