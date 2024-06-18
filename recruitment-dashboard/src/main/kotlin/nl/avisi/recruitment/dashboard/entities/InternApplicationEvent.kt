package nl.avisi.recruitment.dashboard.entities

import jakarta.persistence.*
import java.time.LocalDate

/**
 * Represents an event that logs when an intern's application was submitted.
 *
 * @property issueId The unique identifier of the Jira issue associated with the intern's application.
 * @property applicationDate The date when the intern submitted their application.
 */
@Entity
@Table(name = "intern_application_events")
data class InternApplicationEvent(
    @Id
    @Column(name = "issue_id")
    val issueId: String,

    @Column(name = "application_date")
    val applicationDate: LocalDate? = null
)