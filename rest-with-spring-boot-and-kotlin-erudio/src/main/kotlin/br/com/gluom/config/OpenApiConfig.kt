package br.com.gluom.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customizeConfig(): OpenAPI {
        return OpenAPI().apply {
            info = Info().apply {
                title = "RESTful API with Kotlin and Spring Boot 3"
                version = "v1"
                description = "RESTful API with Kotlin and Spring Boot 3"
                termsOfService = "http://swagger.io/terms/"
                license = License().apply {
                    name = "Apache 2.0"
                    url = "http://www.apache.org/licenses/LICENSE-2.0.html"
                }
            }
        }
    }
}