package nl.avisi.recruitment.dashboard.entities
import jakarta.persistence.*
import java.time.LocalDate

/**
 * Represents an event that logs when an intern was successfully hired.
 *
 * @property issueId The unique identifier of the Jira issue associated with the intern's hiring.
 * @property hireDate The date when the intern was hired.
 */
@Entity
@Table(name = "intern_hiring_events")
data class InternHiringEvent(
    @Id
    @Column(name = "issue_id")
    val issueId: String,

    @Column(name = "hire_date")
    val hireDate: LocalDate
)