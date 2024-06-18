package nl.avisi.recruitment.dashboard.repositories
import nl.avisi.recruitment.dashboard.entities.InternIssue
import org.springframework.data.jpa.repository.JpaRepository

/**
 * JPA repository for performing CRUD operations on InternIssue entities.
 *
 * This interface provides automatic CRUD functionality for InternIssue entities,
 * including basic operations like create, read, update, and delete. It leverages Spring
 * Data JPA to simplify the interaction with the database layer.
 */

interface InternIssueRepository : JpaRepository<InternIssue, String> {
}