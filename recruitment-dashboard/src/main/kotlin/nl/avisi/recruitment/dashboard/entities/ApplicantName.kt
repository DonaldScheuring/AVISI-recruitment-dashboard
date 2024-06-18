package nl.avisi.recruitment.dashboard.entities

import jakarta.persistence.*

@Entity
@Table(name = "applicant_names")
data class ApplicantName(
    @Id
    @Column(name = "name")
    val name: String,

    @OneToOne
    @JoinColumn(name = "jira_issue_id")
    var jiraIssue: JiraIssue? = null
)
