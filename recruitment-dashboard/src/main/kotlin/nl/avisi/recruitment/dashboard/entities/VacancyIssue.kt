package nl.avisi.recruitment.dashboard.entities

import jakarta.persistence.*
import jakarta.persistence.Entity
import nl.avisi.recruitment.dashboard.entities.configuration.IssueStatus
import java.time.LocalDate

/**
 * Represents a vacancy issue in the recruitment process.
 * Inherits from [JiraIssue] and specifies attributes specific to vacancy-related issues.
 *
 * @property statusChanges A list of [StatusChange] entities representing the changes in the vacancy's status.
 */

@Entity
@DiscriminatorValue("VACANCY")
class VacancyIssue(
    id: String,
    currentStatus: IssueStatus? = null,
    createDate: LocalDate? = null,

    @OneToMany(mappedBy = "jiraIssue", cascade = [CascadeType.ALL], orphanRemoval = true)
    val statusChanges: List<StatusChange> = mutableListOf()
) : JiraIssue(id, currentStatus, createDate)
