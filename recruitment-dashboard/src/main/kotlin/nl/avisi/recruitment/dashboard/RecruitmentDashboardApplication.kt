package nl.avisi.recruitment.dashboard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

/**
 * Main entry point for the Recruitment Dashboard Spring Boot application.
 *
 * This class is responsible for bootstrapping the application using Spring Boot's
 * `runApplication` function. It enables the auto-configuration of the Spring
 * ApplicationContext and initializes all components marked for Spring's component
 * scanning (e.g., @Component, @Service, @Repository, @Controller annotations).
 */
@SpringBootApplication
class RecruitmentDashboardApplication

/**
 * Launches the Spring Boot application.
 *
 * @param args Command line arguments passed to the application.
 */
fun main(args: Array<String>) {
	runApplication<RecruitmentDashboardApplication>(*args)
}
