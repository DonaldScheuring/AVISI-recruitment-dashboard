package nl.avisi.recruitment.dashboard.entities

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import nl.avisi.recruitment.dashboard.entities.configuration.IssueStatus
import java.time.LocalDate
import jakarta.persistence.*

/**
 * Represents an intern vacancy issue in Jira.
 *
 * @param id The unique identifier of the Jira issue, matching the Jira issue ID.
 * @param currentStatus The current status of the Jira issue, mapped to an [IssueStatus] enum.
 * @param createDate The date the issue was created in Jira.
 * @param statusChanges A list of [StatusChange] entities reflecting the history of status changes for this issue.
 */
@Entity
@DiscriminatorValue("INTERN_VACANCY")
class InternVacancyIssue(
    id: String,
    currentStatus: IssueStatus? = null,
    createDate: LocalDate? = null,
    extraCost: String? = null,

    @OneToMany(mappedBy = "jiraIssue", cascade = [CascadeType.ALL], orphanRemoval = true)
    val statusChanges: List<StatusChange> = mutableListOf()
) : JiraIssue(id, currentStatus, createDate, extraCost)
