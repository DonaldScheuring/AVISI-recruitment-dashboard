package nl.avisi.recruitment.dashboard.entities

import jakarta.persistence.*
import java.time.LocalDateTime

/**
 * Entity representing the statistics collected from Google Forms responses.
 *
 * This entity is used to store the count of different response options from
 * a Google Forms survey question. Each instance of this entity represents
 * a snapshot of the counts at a specific time.
 *
 * @property id The unique identifier for the form statistics entry.
 * @property timestamp The timestamp when this entry was created.
 * @property viaNetwork The count of responses indicating the applicant came via their own network.
 * @property viaSocialMedia The count of responses indicating the applicant came via social media.
 * @property viaJobBoard The count of responses indicating the applicant came via a job board.
 * @property viaSchoolActivity The count of responses indicating the applicant came via a school activity.
 * @property viaInternship The count of responses indicating the applicant came via an internship.
 * @property viaRecruitmentAgency The count of responses indicating the applicant came via a recruitment agency.
 * @property viaSearchEngine The count of responses indicating the applicant came via a search engine.
 * @property other The count of responses that did not match any of the predefined options.
 */
@Entity
@Table(name = "forms_stats")
data class FormsStats(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val timestamp: LocalDateTime = LocalDateTime.now(),

    val viaNetwork: Int = 0,
    val viaSocialMedia: Int = 0,
    val viaJobBoard: Int = 0,
    val viaSchoolActivity: Int = 0,
    val viaInternship: Int = 0,
    val viaRecruitmentAgency: Int = 0,
    val viaSearchEngine: Int = 0,
    val other: Int = 0
)
