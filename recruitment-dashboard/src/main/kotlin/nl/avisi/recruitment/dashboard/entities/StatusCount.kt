package nl.avisi.recruitment.dashboard.entities

import nl.avisi.recruitment.dashboard.entities.configuration.IssueStatus

/**
 * Represents a summary count of Jira issues at a specific recruitment stage. This class is used to
 * aggregate and report the number of issues/applicants at various stages of the recruitment process,
 * enabling the monitoring of workflow distribution and identifying areas of concentration.
 *
 * @property status The specific stage/status of the recruitment process this count pertains to, as
 * defined by the [IssueStatus] enum. This classification allows for a structured and clear
 * representation of the recruitment pipeline.
 * @property count The number of Jira issues currently at the status represented by [status]. This
 * metric is instrumental in understanding the volume and flow of recruitment activities, providing
 * insights into workload, bottlenecks, and potential resource allocation needs.
 */
data class StatusCount(
    val status: IssueStatus,
    val count: Long

)
