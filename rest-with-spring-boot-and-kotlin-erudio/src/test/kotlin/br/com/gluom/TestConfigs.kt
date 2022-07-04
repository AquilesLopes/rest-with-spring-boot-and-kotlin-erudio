package br.com.gluom

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TestConfigs {

	val HEADER_PARAM_ORIGIN = "Origin"
	val CONTENT_TYPE_JSON = "application/json"
	val CONTENT_TYPE_XML = "application/xml"
	val CONTENT_TYPE_YAML = "application/x-yaml"
	val ORIGIN_LOCALHOST = "http://localhost:8080"

	@Test
	fun contextLoads() {
	}

}
