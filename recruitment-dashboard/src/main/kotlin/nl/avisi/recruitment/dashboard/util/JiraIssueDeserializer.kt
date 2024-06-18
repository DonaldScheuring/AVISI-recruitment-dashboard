package nl.avisi.recruitment.dashboard.util

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import nl.avisi.recruitment.dashboard.entities.*
import java.time.LocalDate
import nl.avisi.recruitment.dashboard.entities.configuration.IssueStatus
import nl.avisi.recruitment.dashboard.repositories.LinkedInApplicantRepository
import org.springframework.beans.factory.annotation.Autowired
import java.time.format.DateTimeFormatter
import org.springframework.stereotype.Component

/**
 * Custom deserializer for JiraIssue entities.
 *
 * This deserializer is responsible for parsing JSON data into various JiraIssue subtypes, which are
 * [InternIssue], [ApplicantIssue], [InternVacancyIssue], and [VacancyIssue] based on the issue type in the JSON input.
 * It handles the extraction and conversion of JSON fields into respective entity attributes,
 * including nested structures like the changelog history.
 *
 * The deserializer specifically converts status names into [IssueStatus] enums, ensuring that status
 * changes are accurately captured and associated with their respective [JiraIssue] instances.
 */
@Component
class JiraIssueDeserializer : JsonDeserializer<JiraIssue>() {

    override fun deserialize(jsonParser: JsonParser, deserializationContext: DeserializationContext): JiraIssue? {

        val rootNode = jsonParser.codec.readTree<JsonNode>(jsonParser)

        // fetch the issue type from the json to determine the specific sub-class to use
        val issueType = rootNode.path("fields").path("issuetype").path("id").asText(null)

        // fetch the information needed from the json for the jira issue entity, based on the path for each respective field
        val id = rootNode.get("id").asText()
        val statusName = rootNode.path("fields").path("status").path("name").asText(null)
        val status = IssueStatus.valueOf(statusName.uppercase().replace(" ", "_").replace("-", "_").replace("1E", "EERSTE_").replace("2E", "TWEEDE_").replace("3E", "DERDE_"))
        val timeString = rootNode.path("fields").path("created").asText(null)
        val date = LocalDate.parse(timeString.substring(0, 10), DateTimeFormatter.ISO_LOCAL_DATE)
        val name = ApplicantName(rootNode.path("fields").path("summary").asText(null))

        val extraCost = null // fill in path to extra cost here

        // root node for change log of the jira issue which contains the histories list
        val changelogNode = rootNode.path("changelog").path("histories")
        val statusChanges = mutableListOf<StatusChange>()

        // Determine the appropriate sub-class based on the issue type
        val jiraIssue: JiraIssue = when (issueType) {
            // id for InternIssue needs to be added!!!
            "00000" -> InternIssue(id, status, date, extraCost, statusChanges, name)
            "10080" -> VacancyIssue(id, status, date, extraCost, statusChanges)
            "10079" -> ApplicantIssue(id, status, date, extraCost, statusChanges, name)
            "10081" -> InternVacancyIssue(id, status, date, extraCost, statusChanges)

            // handling unknown issue types
            // right now the deserializer returns null when encountering an unknown issue id
            // the alternative (commented out below) throws an exception
            //else -> throw IllegalArgumentException("Unknown issue type: $issueType")
            else -> return null
        }

        // linking issue id to applicant name
        name.jiraIssue = jiraIssue

        // process each history entry and extract status changes
        changelogNode.forEach { historyNode ->
            historyNode.path("items").forEach { itemNode ->
                if (itemNode.get("field").asText() == "status") {
                    val historyId = historyNode.get("id").asText()
                    val createdDate = LocalDate.parse(historyNode.get("created").asText().substring(0, 10), DateTimeFormatter.ISO_LOCAL_DATE)
                    val fromString = itemNode.get("fromString").asText(null)
                    val fromStringStatus = IssueStatus.valueOf(fromString.uppercase().replace(" ", "_").replace("-", "_").replace("1E", "EERSTE_").replace("2E", "TWEEDE_").replace("3E", "DERDE_"))
                    val toString = itemNode.get("toString").asText(null)
                    val toStringStatus = IssueStatus.valueOf(toString.uppercase().replace(" ", "_").replace("-", "_").replace("1E", "EERSTE_").replace("2E", "TWEEDE_").replace("3E", "DERDE_"))
                    statusChanges.add(StatusChange(historyId, createdDate, fromStringStatus, toStringStatus, jiraIssue))
                }
            }
        }

        return jiraIssue
    }
}


