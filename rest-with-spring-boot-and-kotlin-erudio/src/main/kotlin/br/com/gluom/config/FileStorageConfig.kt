package br.com.gluom.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "file", ignoreUnknownFields = false)
class FileStorageConfig (var uploadDir: String = "")