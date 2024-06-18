package nl.avisi.recruitment.dashboard.repositories

import nl.avisi.recruitment.dashboard.entities.*
import nl.avisi.recruitment.dashboard.entities.configuration.IssueStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

/**
 * Repository interface for JiraIssue entities. Provides methods for retrieving issue data,
 * including average time to a specific status, count of issues by status, and generation of
 * hiring event records based on status changes.
 */
@Repository
interface JiraIssueRepository : JpaRepository<JiraIssue, String> {

    /**
     * Finds all status changes for the given status for ApplicantIssue and VacancyIssue types.
     * This is needed to filter for only non-intern related issues
     *
     * @param status The target issue status to filter by.
     * @return A list of StatusChange records for the given status.
     */
    @Query("""
    SELECT sc 
    FROM StatusChange sc 
    JOIN sc.jiraIssue ji 
    WHERE TYPE(ji) IN (ApplicantIssue, VacancyIssue) 
    AND ji.currentStatus = :status 
    AND sc.toStatus = :status
    """)
    fun findStatusChangesForCalculation(@Param("status") status: IssueStatus): List<StatusChange>

    /**
     * Calculates the average time (in days) it takes for issues to reach a specified status.
     * Only filters for non-intern issues.
     *
     * @param status The target issue status for which the average time is calculated.
     * @return The average time in days as a Double, or null if no matching records found.
     */
    @Query(value = """
        SELECT AVG(EXTRACT(EPOCH FROM age(sc.change_date, ap.create_date)) / 86400) as average_time_to_hire
        FROM jira_issues ap
        JOIN status_changes sc ON ap.id = sc.jira_issue_id
        WHERE ap.current_status = :#{#status.status}
        AND sc.to_status = :#{#status.status}
        """, nativeQuery = true)
    fun averageTimeToStatus(@Param("status") status: IssueStatus): Double?

    /**
     * Counts the number of Jira issues that are currently in a specified status.
     *
     * @param status The status to count issues for.
     * @return The count of issues in the specified status.
     */
    fun countByCurrentStatus(status: String): Int

    /**
     * Finds all Jira issues that are currently in a specified status.
     *
     * @param currentStatus The status to find issues for.
     * @return A list of JiraIssue entities that are in the specified status.
     */
    fun findByCurrentStatus(currentStatus: IssueStatus): List<JiraIssue>


    /**
     * Counts the number of Jira issues for each status in a given collection of statuses.
     *
     * @param statuses A collection of issue statuses to count issues for.
     * @return A list of StatusCount objects, each representing the count of issues for a specific status.
     */
    @Query(value = """
    SELECT new nl.avisi.recruitment.dashboard.entities.StatusCount(j.currentStatus, COUNT(j)) 
    FROM JiraIssue j 
    WHERE TYPE(j) = ApplicantIssue 
    AND j.currentStatus IN :statuses 
    GROUP BY j.currentStatus
    """)
    fun countByCurrentStatusIn(@Param("statuses") statuses: Collection<IssueStatus>): List<StatusCount>

    /**
     * Finds hiring events for (non-intern) applicants based on status changes to statuses indicating a hire.
     * A hiring event is generated for each Jira issue that transitions to a hiring status,
     * with the earliest date of such transition.
     *
     * @param hiringStatuses A collection of statuses that indicate a successful hire.
     * @return A list of HiringEvent entities, each representing a hiring event for a specific Jira issue.
     */
    @Query("SELECT new nl.avisi.recruitment.dashboard.entities.InternHiringEvent(sc.jiraIssue.id, MIN(sc.changeDate)) " +
            "FROM StatusChange sc " +
            "JOIN sc.jiraIssue ji " +
            "WHERE TYPE(ji) = ApplicantIssue and sc.toStatus IN :hiringStatuses " +
            "GROUP BY sc.jiraIssue.id")
    fun findHiringEvents(@Param("hiringStatuses") hiringStatuses: Collection<IssueStatus>): List<HiringEvent>

    /**
     * Finds hiring events for interns based on status changes to statuses indicating a hire.
     * A hiring event is generated for each Jira issue that transitions to a hiring status,
     * with the earliest date of such transition.
     *
     * @param hiringStatuses A collection of statuses that indicate a successful hire.
     * @return A list of HiringEvent entities, each representing a hiring event for a specific Jira issue.
     */
    @Query("SELECT new nl.avisi.recruitment.dashboard.entities.InternHiringEvent(sc.jiraIssue.id, MIN(sc.changeDate)) " +
            "FROM StatusChange sc " +
            "JOIN sc.jiraIssue ji " +
            "WHERE TYPE(ji) = InternIssue and sc.toStatus IN :hiringStatuses " +
            "GROUP BY sc.jiraIssue.id")
    fun findInternHiringEvents(@Param("hiringStatuses") hiringStatuses: Collection<IssueStatus>): List<InternHiringEvent>

    /**
     * Finds all ApplicantIssue objects.
     *
     * @return A list of ApplicantIssue entities.
     */
    @Query("""
    SELECT ji
    FROM JiraIssue ji 
    WHERE TYPE(ji) = ApplicantIssue
    """)
    fun findAllApplicantIssues(): List<ApplicantIssue>

    /**
     * Finds all InternIssue objects.
     *
     * @return A list of InternIssue entities.
     */
    @Query("""
    SELECT ji
    FROM JiraIssue ji 
    WHERE TYPE(ji) = InternIssue
    """)
    fun findAllInternIssues(): List<InternIssue>

}

