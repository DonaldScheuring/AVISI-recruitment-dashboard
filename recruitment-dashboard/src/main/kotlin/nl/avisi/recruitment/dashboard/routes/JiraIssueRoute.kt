package nl.avisi.recruitment.dashboard.routes

import org.apache.camel.builder.RouteBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.apache.camel.model.dataformat.JsonLibrary
import nl.avisi.recruitment.dashboard.entities.JiraIssueWrapper
import nl.avisi.recruitment.dashboard.repositories.JiraIssueRepository
import nl.avisi.recruitment.dashboard.services.RecruitmentStatsService
import nl.avisi.recruitment.dashboard.services.DuplicateApplicantDeletionService
import org.springframework.stereotype.Component

/**
 * Defines the Camel route for processing Jira issue data.
 * This route is responsible for reading Jira issue data from a specified file, deserializing the
 * JSON content into JiraIssue entities, persisting them to the database, and then triggering the
 * update of recruitment statistics based on the newly ingested data.
 *
 * @property jiraIssueRepository The repository used for saving JiraIssue entities to the database.
 * Provides abstraction over the database layer to interact with JiraIssue data.
 * @property recruitmentStatsService The service responsible for updating recruitment statistics
 * post-ingestion of new JiraIssue data. It encapsulates the logic for statistical computations
 * and persistence of results.
 */
@Component
class JiraIssueRoute(@Autowired private val jiraIssueRepository: JiraIssueRepository,
                     @Autowired private val recruitmentStatsService: RecruitmentStatsService,
                     @Autowired private val duplicateApplicantDeletionService: DuplicateApplicantDeletionService) : RouteBuilder() {
    override fun configure() {
        from("file:src/main/resources?fileName=2024-03-12_export_krr.json&noop=true")
            .unmarshal().json(JsonLibrary.Jackson, JiraIssueWrapper::class.java)
            .process { exchange ->
                val wrapper = exchange.getIn().getBody(JiraIssueWrapper::class.java)

                // we use .filterNotNull(). the deserializer returns null if the type_issue id doesn't match, instead of throwing an exception
                wrapper.issues.filterNotNull().forEach { issue ->
                    jiraIssueRepository.save(issue)
                }
            }
            // update stats
            .bean(recruitmentStatsService, "update")
            // delete duplicates between applicants over LinkedIn and Jira
            .bean(duplicateApplicantDeletionService, "deleteDuplicateApplicants")
            .end()
    }
}

