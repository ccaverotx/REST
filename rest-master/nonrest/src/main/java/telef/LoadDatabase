package telef;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {

	@Bean
	CommandLineRunner initDatabase(CabinaRepo repository) {
		return args -> {
			log.info("Precargando " + repository.save(new Cabina("33", "verde")));
			log.info("Precargando " + repository.save(new Cabina("44", "azul")));
		};
	}
}
