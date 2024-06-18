package nl.avisi.recruitment.dashboard.services

import nl.avisi.recruitment.dashboard.entities.*
import nl.avisi.recruitment.dashboard.entities.configuration.IssueStatus
import nl.avisi.recruitment.dashboard.repositories.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDate

/**
 * Service for updating and managing recruitment statistics. This service handles the calculation and logging
 * of various recruitment metrics such as the average time to hire, the current number of active applicants, and
 * the logging of hiring events.
 */
@Service
class RecruitmentStatsService(
    @Autowired private val jiraIssueRepository: JiraIssueRepository,
    @Autowired private val applicantIssueRepository: ApplicantIssueRepository,
    @Autowired private val internIssueRepository: InternIssueRepository,
    @Autowired private val vacancyIssueRepository: VacancyIssueRepository,
    @Autowired private val internApplicationEventRepository: InternApplicationEventRepository,
    @Autowired private val applicationEventRepository: ApplicationEventRepository,
    @Autowired private val internVacancyIssueRepository: InternVacancyIssueRepository,
    @Autowired private val recruitmentStatsRepository: RecruitmentStatsRepository,
    @Autowired private val hiringEventRepository: HiringEventRepository,
    @Autowired private val internHiringEventRepository: InternHiringEventRepository,
    @Autowired private val applicantsStageLogRepository: ApplicantsStageLogRepository
) {

    /**
     * Updates the recruitment statistics and hiring events based on the current data.
     */
    fun update() {
        updateRecruitmentStats()
        updateHiringEvents()
        updateApplicationEvents()
        updateInternHiringEvents()
        updateInternApplicationEvents()
    }

    /**
     * Calculates and saves recruitment statistics such as the average time to hire and the current number
     * of active applicants. It also logs the current number of applicants at each stage of the recruitment process.
     */
    //@Transactional
    fun updateRecruitmentStats() {

        // calculate average time to hire
        val hiredStatuses = IssueStatus.values().filter { it.successfulHire }
        val avgTimeToHire = calculateAverageTimeToStatus(hiredStatuses)

        // count current number of active applicants
        val ongoingStatuses = IssueStatus.values().filter { it.activeApplicant }
        val currentApplicantsByStatus = jiraIssueRepository.countByCurrentStatusIn(ongoingStatuses)

        // count current applicants by stage
        val currentApplicants = currentApplicantsByStatus.sumOf { it.count }

        // calculate average time to fill vacancies
        val filledStatuses = IssueStatus.values().filter { it.vacFilled }
        val avgTimeToFill = calculateAverageTimeToStatus(filledStatuses)


        // save recruitment statistics to db
        saveRecruitmentStats(avgTimeToHire, currentApplicants, avgTimeToFill)
        // save the number of applicants by stage to db
        logCurrentApplicantsByStage(currentApplicantsByStatus)
    }

    /**
     * Calculates the average time to reach the specified statuses.
     *
     * This method computes the average number of days it takes for issues to transition
     * to any of the specified statuses. It does this by retrieving the relevant status
     * changes from the repository, calculating the duration in days between the issue
     * creation date and the status change date, and then averaging these durations.
     *
     * @param statuses A list of [IssueStatus] enums representing the statuses to calculate the average time for.
     * @return The average time in days as a [Double], or null if no matching records are found.
     */
    fun calculateAverageTimeToStatus(statuses: List<IssueStatus>): Double? {
        // retrieve all relevant status changes for the provided statuses
        val statusChanges = statuses.flatMap { status ->
            jiraIssueRepository.findStatusChangesForCalculation(status)
        }

        // calculate the total days between issue creation and status change for each status change
        val totalDays = statusChanges.mapNotNull { statusChange ->
            statusChange.jiraIssue?.createDate?.let { createDate ->
                Duration.between(createDate.atStartOfDay(), statusChange.changeDate.atStartOfDay()).toDays().toDouble()
            }
        }.takeIf { it.isNotEmpty() }?.average()

        // return the average of the calculated days, or null if no calculations were performed
        return totalDays
    }

    /**
     * Logs the current number of applicants by each recruitment stage to the database.
     *
     * @param statusCounts A list of status counts representing the number of applicants at each recruitment stage.
     */
    private fun logCurrentApplicantsByStage(statusCounts: List<StatusCount>) {
        // map status counts to the corresponding fields in the log entity
        val logEntry = ApplicantsStageLog(
            logDate = LocalDate.now(),
            open = statusCounts.find { it.status == IssueStatus.OPEN }?.count ?: 0,
            inReview = statusCounts.find { it.status == IssueStatus.BEOORDELING }?.count ?: 0,
            firstInterview = statusCounts.find { it.status == IssueStatus.EERSTE_GESPREK }?.count ?: 0,
            secondInterview = statusCounts.find { it.status == IssueStatus.TWEEDE_GESPREK }?.count ?: 0,
            thirdInterview = statusCounts.find { it.status == IssueStatus.DERDE_GESPREK }?.count ?: 0
        )
        applicantsStageLogRepository.save(logEntry)
    }

    /**
     * Updates hiring events by fetching and saving events where Jira issues have changed to a status
     * indicating a successful hire.
     */
    fun updateHiringEvents() {
        // filter for statuses that indicate a successful hire
        val hiringStatuses = IssueStatus.values().filter { it.successfulHire }

        // fetch hiring events based on status changes to a hiring status
        val hiringEvents = jiraIssueRepository.findHiringEvents(hiringStatuses)

        // save each hiring event
        hiringEvents.forEach { event -> hiringEventRepository.save(event) }
    }

    /**
     * Updates intern hiring events by fetching and saving events where Jira issues have changed to a status
     * indicating a successful hire for interns.
     */
    fun updateInternHiringEvents() {
        // filter for statuses that indicate a successful hire
        val hiringStatuses = IssueStatus.values().filter { it.successfulHire }

        // fetch intern hiring events based on status changes to a hiring status
        val internHiringEvents = jiraIssueRepository.findInternHiringEvents(hiringStatuses)

        // save each intern hiring event
        internHiringEvents.forEach { event -> internHiringEventRepository.save(event) }
    }


    /**
     * Updates intern application events by fetching and saving events where Jira issues have been created for interns.
     */
    fun updateInternApplicationEvents() {
        // fetch all intern issues
        val internIssues = jiraIssueRepository.findAllInternIssues()

        // create intern application events for each intern issue
        val internApplicationEvents = internIssues.map { issue ->
            InternApplicationEvent(issue.id, issue.createDate)
        }

        // save each intern application event
        internApplicationEvents.forEach { event -> internApplicationEventRepository.save(event) }
    }

    /**
     * Updates application events by fetching and saving events where Jira issues have been created for applicants.
     */
    fun updateApplicationEvents() {
        // fetch all applicant issues
        val applicantIssues = jiraIssueRepository.findAllApplicantIssues()

        // create application events for each applicant issue
        val applicationEvents = applicantIssues.map { issue ->
            ApplicationEvent(issue.id, issue.createDate)
        }

        // save each application event
        applicationEvents.forEach { event -> applicationEventRepository.save(event) }
    }

    /**
     * Saves recruitment statistics to the database.
     *
     * @param avgTimeToHire The calculated average time to hire.
     * @param currentApplicants The current number of active applicants.
     */
    private fun saveRecruitmentStats(avgTimeToHire: Double?, currentApplicants: Long?, avgTimeToFill: Double?) {
        // create and save the recruitment stats entity
        val recruitmentStats = RecruitmentStats(
            statsDate = LocalDate.now(),
            currentApplicants = currentApplicants?.toInt(),
            avgTimeToHire = avgTimeToHire,
            avgTimeToFill = avgTimeToFill
        )
        recruitmentStatsRepository.save(recruitmentStats)
    }


}
