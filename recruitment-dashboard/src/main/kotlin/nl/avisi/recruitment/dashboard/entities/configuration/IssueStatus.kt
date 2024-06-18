package nl.avisi.recruitment.dashboard.entities.configuration

/**
 * Enum representing the status of an issue within the recruitment process.
 *
 * This enum classifies various stages of the recruitment process by defining
 * specific statuses. Each status is associated with flags indicating whether
 * it represents an open vacancy, an active applicant's process stage, or a
 * successful hire. This classification aids in the dynamic analysis and
 * reporting of the recruitment pipeline's state.
 *
 * @property status The string representation of the issue status.
 * @property vacFilled Indicates whether the vacancy is filled.
 * @property activeApplicant Indicates if the status is associated with an applicant
 * actively undergoing the recruitment process.
 * @property successfulHire Indicates if the status marks the successful hiring
 * of an applicant.
 */
enum class IssueStatus(val status: String, val vacFilled: Boolean, val activeApplicant: Boolean, val successfulHire: Boolean) {

    // enum values with documentation for each, explaining its specific role in the recruitment process

    // vacancy statuses
    VAC_OPEN("VAC_OPEN", vacFilled = false, activeApplicant = false, successfulHire = false),
    KDD_IN_PROCEDURE("KDD_IN_PROCEDURE", vacFilled = false, activeApplicant = true, successfulHire = false),
    GEEN_KDD_IN_PROCEDURE("GEEN_KDD_IN_PROCEDURE", vacFilled = false, activeApplicant = true, successfulHire = false),
    CLOSED___NIET_VERVULD("CLOSED___NIET_VERVULD", vacFilled = false, activeApplicant = false, successfulHire = false),
    // status indicating a vacancy is closed and filled successfully
    CLOSED___VERVULD("CLOSED___VERVULD", vacFilled = true, activeApplicant = false, successfulHire = true),


    // applicant/intern statuses
    OPEN("OPEN", vacFilled = false, activeApplicant = true, successfulHire = false),
    BEOORDELING("BEOORDELING", vacFilled = false, activeApplicant = true, successfulHire = false),
    EERSTE_GESPREK("EERSTE_GESPREK", vacFilled = false, activeApplicant = true, successfulHire = false),
    TWEEDE_GESPREK("TWEEDE_GESPREK", vacFilled = false, activeApplicant = true, successfulHire = false),
    DERDE_GESPREK("DERDE_GESPREK", vacFilled = false, activeApplicant = true, successfulHire = false),
    CLOSED___NIET_OP_GESPREK("CLOSED___NIET_OP_GESPREK", vacFilled = false, activeApplicant = false, successfulHire = false),
    CLOSED___NA_1E_GESPREK("CLOSED___NA_1E_GESPREK", vacFilled = false, activeApplicant = false, successfulHire = false),
    CLOSED___NA_2E_GESPREK("CLOSED___NA_2E_GESPREK", vacFilled = false, activeApplicant = false, successfulHire = false),
    CLOSED___NA_3E_GESPREK("CLOSED___NA_3E_GESPREK", vacFilled = false, activeApplicant = false, successfulHire = false),
    // status indicating the hiring process has concluded successfully
    CLOSED___HIRED("CLOSED___HIRED", vacFilled = false, activeApplicant = false, successfulHire = true);

    companion object {
        /**
         * Finds an [IssueStatus] by its string representation.
         *
         * This method allows for the conversion of a status string to its
         * corresponding [IssueStatus] enum value. If no matching status is found,
         * null is returned. This function facilitates the handling of dynamic status
         * strings in the application, ensuring that only defined statuses are processed.
         *
         * @param status The string representation of the status to find.
         * @return The corresponding [IssueStatus] or null if no match is found.
         */
        fun fromStatus(status: String): IssueStatus? = values().find { it.status == status }
    }
}