package nl.avisi.recruitment.dashboard.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDate

/**
 * Entity representing an applicant from LinkedIn.
 *
 * @property id The unique identifier of the LinkedIn applicant entry.
 * @property applicantName The name of the applicant.
 * @property applicationDate The date the application was received.
 */
@Entity
data class LinkedInApplicant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val applicantName: String,
    val applicationDate: LocalDate
)
