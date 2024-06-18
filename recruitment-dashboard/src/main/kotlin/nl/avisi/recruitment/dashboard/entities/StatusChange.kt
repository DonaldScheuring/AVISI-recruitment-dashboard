package nl.avisi.recruitment.dashboard.entities

import jakarta.persistence.*
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import nl.avisi.recruitment.dashboard.entities.configuration.IssueStatus

/**
 * Represents a change in status for a Jira issue, capturing the transition from one status to another
 * at a specific point in time. This entity is pivotal for tracking the progression of each recruitment
 * case through different stages of the hiring process.
 *
 * @property id The unique identifier of the status change event. Corresponds to specific entries in the
 * changelog of a Jira issue, providing a direct link to the historical data in Jira.
 * @property changeDate The date when the status change occurred. This temporal data is critical for
 * analyzing the flow and efficiency of the recruitment process over time.
 * @property fromStatus The status of the Jira issue before the change. Indicates the starting point of
 * the transition, helping in understanding the progression path of an issue.
 * @property toStatus The status of the Jira issue after the change. Marks the target state achieved by
 * the transition, highlighting the dynamic nature of the recruitment process.
 * @property jiraIssue The associated Jira issue that underwent the status change. This relationship
 * ties the status change event back to the specific recruitment case, allowing for a comprehensive
 * view of each issue's history.
 */
@Entity
@Table(name = "status_changes")
data class StatusChange(
    @Id
    val id: String,
    val changeDate: LocalDate,

    @Enumerated(EnumType.STRING)
    val fromStatus: IssueStatus?,

    @Enumerated(EnumType.STRING)
    val toStatus: IssueStatus?,

    @ManyToOne
    @JoinColumn(name = "jira_issue_id")
    val jiraIssue: JiraIssue? = null
)
