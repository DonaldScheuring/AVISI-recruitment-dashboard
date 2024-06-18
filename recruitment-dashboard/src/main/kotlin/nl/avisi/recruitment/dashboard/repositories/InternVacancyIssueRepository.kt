package nl.avisi.recruitment.dashboard.repositories
import nl.avisi.recruitment.dashboard.entities.InternVacancyIssue
import org.springframework.data.jpa.repository.JpaRepository

/**
 * JPA repository for performing CRUD operations on InternVacancyIssue entities.
 *
 * This interface provides automatic CRUD functionality for VacancyIssue entities,
 * including basic operations like create, read, update, and delete. It leverages Spring
 * Data JPA to simplify the interaction with the database layer.
 */

interface InternVacancyIssueRepository : JpaRepository<InternVacancyIssue, String> {
}