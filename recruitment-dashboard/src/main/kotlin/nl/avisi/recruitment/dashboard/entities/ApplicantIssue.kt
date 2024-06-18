package nl.avisi.recruitment.dashboard.entities

import jakarta.persistence.*
import jakarta.persistence.Entity
import nl.avisi.recruitment.dashboard.entities.configuration.IssueStatus
import java.time.LocalDate

/**
 * Represents an applicant issue in the recruitment process.
 * Inherits from [JiraIssue] and specifies attributes specific to applicant-related issues.
 *
 * @property statusChanges A list of [StatusChange] entities representing the changes in the applicant's status.
 */
@Entity
@DiscriminatorValue("APPLICANT")
class ApplicantIssue(
    id: String,
    currentStatus: IssueStatus? = null,
    createDate: LocalDate? = null,

    @OneToMany(mappedBy = "jiraIssue", cascade = [CascadeType.ALL], orphanRemoval = true)
    val statusChanges: List<StatusChange> = mutableListOf(),

    @OneToOne(mappedBy = "jiraIssue", cascade = [CascadeType.ALL], orphanRemoval = true)
    val applicantName: ApplicantName? = null
) : JiraIssue(id, currentStatus, createDate)