package nl.avisi.recruitment.dashboard.entities.configuration

import com.fasterxml.jackson.databind.InjectableValues
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import nl.avisi.recruitment.dashboard.util.JiraIssueDeserializer
import nl.avisi.recruitment.dashboard.entities.JiraIssue
import nl.avisi.recruitment.dashboard.repositories.LinkedInApplicantRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

/**
 * Configuration class to customize Jackson's ObjectMapper.
 *
 * This class is responsible for configuring a custom ObjectMapper for the application.
 * It ensures that the ObjectMapper can correctly handle Java 8 Date and Time API types
 * by registering the JavaTimeModule. This is particularly important for serializing and
 * deserializing objects with date and time fields to and from JSON.
 *
 * The configuration also explicitly disables XML mapper features since this application
 * primarily deals with JSON data. This focus helps streamline the ObjectMapper's
 * configuration and usage within the application.
 */
@Configuration
class JacksonConfig {

    /**
     * Creates and configures an ObjectMapper instance.
     *
     * This bean method configures the ObjectMapper to not create XML mappers and
     * registers the JavaTimeModule to handle Java 8 Date and Time API types. The
     * configured ObjectMapper is essential for proper JSON processing throughout
     * the application, especially for entities with date and time fields.
     *
     * @param builder The Jackson2ObjectMapperBuilder provided by Spring Boot,
     *                used for configuring the ObjectMapper.
     * @return A fully configured ObjectMapper instance.
     */
    @Bean
    fun objectMapper(builder: Jackson2ObjectMapperBuilder): ObjectMapper {
        val objectMapper: ObjectMapper = builder.createXmlMapper(false).build()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.addMixIn(JiraIssue::class.java, JiraIssueDeserializer::class.java)
        return objectMapper
    }
}


