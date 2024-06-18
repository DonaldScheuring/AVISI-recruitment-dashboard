package nl.avisi.recruitment.dashboard.routes

import nl.avisi.recruitment.dashboard.entities.LinkedInApplicant
import nl.avisi.recruitment.dashboard.repositories.LinkedInApplicantRepository
import org.apache.camel.Exchange
import org.apache.camel.builder.RouteBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDate
import javax.mail.internet.MimeMessage

/**
 * Apache Camel route to process LinkedIn applications received via Gmail.
 *
 * This route connects to a Gmail inbox, retrieves emails from a specific sender
 * (LinkedIn job applications), extracts the applicant's name from the email subject,
 * and saves the application date and applicant's name to the LinkedInApplicant entity.
 *
 * The route processes unseen emails from the specified Gmail account and stores
 * the relevant details in the database using LinkedInApplicantRepository.
 */
@Component
class GmailRoute(@Autowired private val linkedInApplicantRepository: LinkedInApplicantRepository) : RouteBuilder() {
    override fun configure() {
        // define the route to read emails from a gmail inbox
        from("imaps://imap.gmail.com?username=apachecameltestuser@gmail.com&password=vaip ranl qvuy gsln&delete=false&unseen=false")
            .routeId("gmailRoute")
            .filter { exchange ->
                // filter emails from the specified sender with the correct subject format
                val mailMessage = exchange.getIn().getBody(javax.mail.Message::class.java) as MimeMessage
                val fromAddress = mailMessage.from[0].toString()
                val subject = mailMessage.subject
                fromAddress == "jobs-listings@linkedin.com" && subject.startsWith("New application: ")
            }
            .process { exchange ->
                // extract applicant name and application date
                val mailMessage = exchange.getIn().getBody(javax.mail.Message::class.java) as MimeMessage
                val subject = mailMessage.subject
                val applicantName = subject.substringAfter(" from ").trim()
                val applicationDate = LocalDate.now()

                // Save LinkedIn applicant data to the database
                val linkedInApplicant = LinkedInApplicant(
                    applicantName = applicantName,
                    applicationDate = applicationDate
                )
                linkedInApplicantRepository.save(linkedInApplicant)
            }

            .end()
    }
}


//apachecameltestuser@gmail.com
// Gmail app password:
//vaip ranl qvuy gsln