package nl.avisi.recruitment.dashboard.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A wrapper class for deserializing a collection of Jira issues from JSON.
 * This class is designed to work with JSON data structures that encapsulate a list of Jira issue
 * objects under a "issues" key, conforming to a typical response structure encountered in Jira
 * REST API responses or exported JSON files.
 *
 * @property issues A list of [JiraIssue] instances representing individual issues extracted from
 * the JSON data. Each [JiraIssue] corresponds to a single ticket or task in Jira, containing data
 * like status, creation date, and any custom fields relevant to the recruitment process.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class JiraIssueWrapper(
    @JsonProperty("issues")
    val issues: List<JiraIssue>
)
