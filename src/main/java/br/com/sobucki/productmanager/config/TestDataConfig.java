package br.com.sobucki.productmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;

/**
 * Configuração dos recursos de dados de teste.
 * Essas configurações só estão disponíveis em ambientes de desenvolvimento.
 */
@Configuration
@Profile({"dev", "test", "local"})
public class TestDataConfig {

    /**
     * Configuração do OpenAPI para documentação dos endpoints de teste.
     * @return OpenAPI configurada
     */
    @Bean
    public OpenAPI testDataOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("API de Dados de Teste")
                        .description("API para geração de dados de teste usando Easy Random")
                        .version("v1.0")
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")))
                .addTagsItem(new Tag()
                        .name("Dados de Teste")
                        .description("Endpoints para gerenciamento de dados de teste"));
    }
} 