package nl.avisi.recruitment.dashboard.routes

import nl.avisi.recruitment.dashboard.entities.FormsStats
import nl.avisi.recruitment.dashboard.repositories.FormsStatsRepository
import org.apache.camel.builder.RouteBuilder
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.File
import java.time.LocalDateTime

/**
 * Apache Camel route to process Google Forms responses from a Google Sheets file.
 *
 * This route reads a Google Sheets file containing responses to a survey question
 * about how applicants found Avisi. It counts the occurrences of each response
 * option and saves a snapshot of these counts to the FormsStats entity.
 *
 * The route processes files from a specified directory, extracts relevant data,
 * and persists it in the database using FormsStatsRepository.
 */
@Component
class GoogleFormsRoute(@Autowired private val formsStatsRepository: FormsStatsRepository) : RouteBuilder() {
    override fun configure() {
        // define the route to read files from a specified directory
        from("file:/path/to/google/forms/directory?noop=true")
            .routeId("googleFormsRoute")
            .process { exchange ->
                // get the file from the exchange
                val file = exchange.getIn().getBody(File::class.java)

                // use Apache POI to read the Google Sheets file
                val workbook = WorkbookFactory.create(file)
                val sheet = workbook.getSheetAt(0)

                // find the column index for the survey question
                val questionColumnIndex = sheet.getRow(0).indexOfFirst { it.stringCellValue == "Hoe ben je bij Avisi terecht gekomen?" }

                // initialize counters for each response option
                var viaNetworkCount = 0
                var viaSocialMediaCount = 0
                var viaJobBoardCount = 0
                var viaSchoolActivityCount = 0
                var viaInternshipCount = 0
                var viaRecruitmentAgencyCount = 0
                var viaSearchEngineCount = 0
                var otherCount = 0

                // iterate through each row in the sheet, skipping the header row
                for (row in sheet.drop(1)) {
                    val cellValue = row.getCell(questionColumnIndex).stringCellValue
                    when (cellValue) {
                        "Via via (eigen netwerk)" -> viaNetworkCount++
                        "Via social media (bijvoorbeeld Instagram of LinkedIn)" -> viaSocialMediaCount++
                        "Via jobboard (bijvoorbeeld Indeed)" -> viaJobBoardCount++
                        "Via een bedrijvenmarkt of een andere schoolactiviteit (bijvoorbeeld masterclass, tostitalk, docent)" -> viaSchoolActivityCount++
                        "Via een stage bij Avisi" -> viaInternshipCount++
                        "Via een wervingsbureau (externe recruiter)" -> viaRecruitmentAgencyCount++
                        "Via Google of een andere zoekmachine" -> viaSearchEngineCount++
                        else -> otherCount++
                    }
                }

                // create a new FormsStats entity with the counts
                val formsStats = FormsStats(
                    timestamp = LocalDateTime.now(),
                    viaNetwork = viaNetworkCount,
                    viaSocialMedia = viaSocialMediaCount,
                    viaJobBoard = viaJobBoardCount,
                    viaSchoolActivity = viaSchoolActivityCount,
                    viaInternship = viaInternshipCount,
                    viaRecruitmentAgency = viaRecruitmentAgencyCount,
                    viaSearchEngine = viaSearchEngineCount,
                    other = otherCount
                )

                // save the FormsStats entity to the database
                formsStatsRepository.save(formsStats)
            }
            .end()
    }
}
