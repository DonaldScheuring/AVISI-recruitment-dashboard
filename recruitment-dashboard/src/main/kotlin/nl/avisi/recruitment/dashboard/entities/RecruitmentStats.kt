package nl.avisi.recruitment.dashboard.entities

import jakarta.persistence.*
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

/**
 * Captures aggregated recruitment metrics for a specific date. This entity serves as a snapshot of
 * various recruitment statistics, including the average time to hire and the current number of applicants.
 *
 * @property id Unique identifier for each recruitment stats entry, automatically generated.
 * @property statsDate The date these statistics were calculated, intended to provide a temporal context
 * for the data, allowing for trend analysis over time.
 * @property currentApplicants The total number of applicants considered active in the recruitment pipeline
 * as of the `statsDate`. This count helps in understanding the workload and the scale of recruitment efforts.
 * @property avgTimeToHire The average time taken to hire candidates, calculated from first contact to job offer
 * acceptance. This metric is crucial for assessing the efficiency of the recruitment process.
 */
@Entity
@Table(name = "recruitment_stats")
data class RecruitmentStats(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val statsDate: LocalDate = LocalDate.now(),
    val currentApplicants: Int? = 0,
    val avgTimeToHire: Double? = null,
    val avgTimeToFill: Double? = null
)