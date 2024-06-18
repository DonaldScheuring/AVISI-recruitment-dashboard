package nl.avisi.recruitment.dashboard.repositories

import org.springframework.data.jpa.repository.JpaRepository
import nl.avisi.recruitment.dashboard.entities.StatusChange
import nl.avisi.recruitment.dashboard.entities.configuration.IssueStatus
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

/**
 * Repository interface for managing StatusChange entities. This interface provides custom
 * query methods related to status changes of Jira issues.
 */
interface StatusChangeRepository : JpaRepository<StatusChange, Long> {

    /**
     * Finds status change records where the final status matches the specified status
     * and the associated Jira issue is currently in that status. This can be particularly
     * useful for identifying the dates when Jira issues reached a hiring status.
     *
     * @param status The status used for filtering both the target status of the change
     *               and the current status of the Jira issue.
     * @return A list of StatusChange entities matching the criteria, representing the dates
     *         and details of the status changes.
     */
    @Query("SELECT sc FROM StatusChange sc WHERE sc.toStatus = :status AND sc.jiraIssue.currentStatus = :status")
    fun findHiringDatesByStatus(@Param("status") status: IssueStatus): List<StatusChange>
}
