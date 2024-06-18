package nl.avisi.recruitment.dashboard.entities

import jakarta.persistence.*
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

/**
 * Represents a hiring event associated with a Jira issue. This entity is used to log when a candidate
 * associated with a Jira issue is successfully hired.
 *
 * @property issueId The unique identifier of the Jira issue associated with the hiring event. This ID
 * corresponds to the Jira issue that tracks the candidate's hiring process.
 * @property hireDate The date on which the hiring decision for the associated Jira issue was made.
 * Indicates when the candidate linked to the Jira issue was successfully hired.
 */
@Entity
@Table(name = "hiring_events")
data class HiringEvent(
    @Id
    @Column(name = "issue_id")
    val issueId: String,

    @Column(name = "hire_date")
    val hireDate: LocalDate? = null
)

