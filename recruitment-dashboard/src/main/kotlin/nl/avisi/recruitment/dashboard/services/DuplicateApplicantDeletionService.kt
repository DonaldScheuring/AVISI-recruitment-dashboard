package nl.avisi.recruitment.dashboard.services

import nl.avisi.recruitment.dashboard.entities.LinkedInApplicant
import nl.avisi.recruitment.dashboard.repositories.ApplicantNameRepository
import nl.avisi.recruitment.dashboard.repositories.LinkedInApplicantRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Service for checking and deleting duplicate applicants from the LinkedInApplicant table.
 * This service identifies overlapping names between the ApplicantName table and the
 * LinkedInApplicant table and removes the duplicates from the LinkedInApplicant table.
 */
@Service
class DuplicateApplicantDeletionService(
    @Autowired private val applicantNameRepository: ApplicantNameRepository,
    @Autowired private val linkedInApplicantRepository: LinkedInApplicantRepository
) {

    /**
     * Checks for duplicate applicants between the ApplicantName table and the LinkedInApplicant table.
     * If duplicates are found, the entries in the LinkedInApplicant table are deleted.
     */
    @Transactional
    fun deleteDuplicateApplicants() {
        val allApplicantNames = applicantNameRepository.findAll().map { it.name }
        val duplicates = linkedInApplicantRepository.findAll()
            .filter { it.applicantName in allApplicantNames }

        if (duplicates.isNotEmpty()) {
            linkedInApplicantRepository.deleteAll(duplicates)
        }
    }
}
