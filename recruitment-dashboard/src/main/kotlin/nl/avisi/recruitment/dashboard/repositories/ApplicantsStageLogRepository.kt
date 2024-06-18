package nl.avisi.recruitment.dashboard.repositories

import nl.avisi.recruitment.dashboard.entities.ApplicantsStageLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * JPA repository for performing CRUD operations on ApplicantsStageLog entities.
 *
 * This interface provides automatic CRUD functionality for ApplicantsStageLog entities,
 * including basic operations like create, read, update, and delete. It leverages Spring
 * Data JPA to simplify the interaction with the database layer.
 */
@Repository
interface ApplicantsStageLogRepository : JpaRepository<ApplicantsStageLog, Long> {
}