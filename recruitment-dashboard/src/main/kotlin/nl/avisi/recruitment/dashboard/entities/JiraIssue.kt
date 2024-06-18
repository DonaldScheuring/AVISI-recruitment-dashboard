package nl.avisi.recruitment.dashboard.entities

import jakarta.persistence.*
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.annotation.JsonSubTypes
import nl.avisi.recruitment.dashboard.util.JiraIssueDeserializer
import nl.avisi.recruitment.dashboard.entities.configuration.IssueStatus
import java.time.LocalDate

/**
 * Abstract base class representing a Jira issue in the database. This serves as the parent class for different
 * types of issues, such as intern, applicant, and vacancy issues.
 *
 * - The inheritance strategy is set to single-table inheritance (`SINGLE_TABLE`), meaning all subtypes will be
 *   stored in a single database table. The `DiscriminatorColumn` is used to distinguish between different issue types.
 * - The custom deserializer [JiraIssueDeserializer] is used to convert JSON data into specific subtypes
 *   based on the discriminator column.
 *
 * @property id The unique identifier of the Jira issue, matching the Jira issue ID.
 * @property currentStatus The current status of the Jira issue, mapped to an [IssueStatus] enum.
 * @property createDate The date the issue was created in Jira.
 */

@Entity
@Table(name = "jira_issues")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "issue_type", discriminatorType = DiscriminatorType.STRING)
@JsonSubTypes(
    JsonSubTypes.Type(value = InternIssue::class, name = "intern"),
    JsonSubTypes.Type(value = ApplicantIssue::class, name = "applicant"),
    JsonSubTypes.Type(value = VacancyIssue::class, name = "vacancy"),
    JsonSubTypes.Type(value = InternVacancyIssue::class, name = "intern_vacancy")
)
@JsonDeserialize(using = JiraIssueDeserializer::class)
abstract class JiraIssue(
    @Id
    open val id: String,

    @Enumerated(EnumType.STRING)
    open var currentStatus: IssueStatus? = null,

    open val createDate: LocalDate? = null
)

