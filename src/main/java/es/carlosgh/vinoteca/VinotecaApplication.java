package es.carlosgh.vinoteca;

import es.carlosgh.vinoteca.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan
public class VinotecaApplication {

    public static void main(String[] args) {
        SpringApplication.run(VinotecaApplication.class, args);
    }

    /**
     * Este bean se inicia al lanzar la aplicación. Nos permite inicializar el almacenamiento
     * secundario del proyecto
     *
     * @param storageService Almacenamiento secundario del proyecto
     * @return
     */
    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}
