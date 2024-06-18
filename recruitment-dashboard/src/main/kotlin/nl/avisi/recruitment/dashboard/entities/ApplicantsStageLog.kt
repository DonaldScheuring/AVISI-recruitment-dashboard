package nl.avisi.recruitment.dashboard.entities

import jakarta.persistence.*
import java.time.LocalDate

/**
 * Represents a log entry for tracking the number of applicants at various stages
 * of the recruitment process on a specific date.
 *
 * @property id The unique identifier of the log entry.
 * @property logDate The date when the log entry was recorded.
 * @property inReview The number of applicants currently in review.
 * @property firstInterview The number of applicants who have reached the first interview stage.
 * @property secondInterview The number of applicants who have reached the second interview stage.
 * @property thirdInterview The number of applicants who have reached the third interview stage.
 */
@Entity
@Table(name = "applicants_stage_log")
data class ApplicantsStageLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val logDate: LocalDate,

    @Column(name = "OPEN")
    val open: Long,
    @Column(name = "BEOORDELING")
    val inReview: Long,
    @Column(name = "EERSTE_GESPREK")
    val firstInterview: Long,
    @Column(name = "TWEEDE_GESPREK")
    val secondInterview: Long,
    @Column(name = "DERDE_GESPREK")
    val thirdInterview: Long
    /*
    @Column(name = "CLOSED___NIET_OP_GESPREK")
    val closedNoInterview: Long = 0,
    @Column(name = "CLOSED___NA_1E_GESPREK")
    val closedInterviewOne: Long = 0,
    @Column(name = "CLOSED___NA_2E_GESPREK")
    val closedInterviewTwo: Long = 0,
    @Column(name = "CLOSED___NA_3E_GESPREK")
    val closedInterviewThree: Long = 0,
    @Column(name = "CLOSED___HIRED")
    val closedHired: Long = 0
    */
)

