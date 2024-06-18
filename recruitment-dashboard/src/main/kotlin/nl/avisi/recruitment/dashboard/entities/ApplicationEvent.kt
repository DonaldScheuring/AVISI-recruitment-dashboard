package nl.avisi.recruitment.dashboard.entities

import jakarta.persistence.*
import java.time.LocalDate

/**
 * Represents an application event associated with a Jira issue. This entity is used to log when a
 * candidate application associated with a Jira issue is submitted.
 *
 * This class tracks the initial submission of a candidate's application, providing a record of
 * when each application was made. It is linked to the specific Jira issue created for tracking
 * the recruitment process for that candidate.
 *
 * @property issueId The unique identifier of the Jira issue associated with the application event.
 * This ID corresponds to the Jira issue that tracks the candidate's application and subsequent
 * recruitment process.
 * @property applicationDate The date on which the application for the associated Jira issue was submitted.
 * Indicates when the candidate linked to the Jira issue officially applied for the position.
 */
@Entity
@Table(name = "application_events")
data class ApplicationEvent(
    @Id
    @Column(name = "issue_id")
    val issueId: String,

    @Column(name = "application_date")
    val applicationDate: LocalDate? = null
)
