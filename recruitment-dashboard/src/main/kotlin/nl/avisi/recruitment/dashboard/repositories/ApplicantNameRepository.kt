package nl.avisi.recruitment.dashboard.repositories

import nl.avisi.recruitment.dashboard.entities.ApplicantName
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Repository interface for ApplicantName entities. Provides methods for retrieving and managing applicant names.
 */
@Repository
interface ApplicantNameRepository : JpaRepository<ApplicantName, Long> {
    /**
     * Finds an ApplicantName entity by the applicant's name.
     *
     * @param name The name of the applicant to find.
     * @return The ApplicantName entity if found, or null otherwise.
     */
    fun findByName(name: String): ApplicantName?
}
