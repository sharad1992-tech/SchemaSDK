package com.wegolook.schemalibrary

object AppConstants {

    const val JWT_TOKEN = "jwToken"
    const val EMAIL = "email"
    const val BEARER = "Bearer "
    const val PASSWORD_PATTERN: String =
        "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[\$@\$!%*?&])[A-Za-zd\$@\$!%*?&].{9,}"
    const val PHONE_NUMBER_PATTERN: String = "([1-9][0-9]{9});"
    const val ONE = "1"
    const val TWO = "2"
    const val THREE = "3"
    const val FOUR = "4"

    const val XACTNET_TYPE = "xactnet_type"
    const val EDUCATION_TYPE = "education_type"
    const val EDUCATION_ID = "education_id"
    const val EMPLOYMENT_TYPE = "employment_type"
    const val CERTIFICATE_TYPE = "certificate_type"
    const val IS_EDIT = "is_edit"
    const val AVAIL_STATUS_KEY = "avail_status_key"
    const val AVAIL_STATUS_VALUE = "avail_status_value"
    const val AVAIL_DATE_VALUE = "avail_date"
    const val EDUCATION_OBJ = "education_obj"
    const val SAUMMARY = "summary"
    const val SSN_STATUS = "ssn_status"
    const val INSURANCE_STATUS = "insurance_status"
    const val FOUNTAIN_STAGE = "fountain_stage"
    const val LAWSON_STATUS = "lawson_status"
    const val NPN_STATUS = "npn_status"
    const val REJECTION_REASON = "rejection_reason"
    const val IS_VERIFIED = "is_verified"
    const val IS_AGREEMENT_ACCEPT = "is_agreement_accept"
    const val ACCEPT_AGREEMENT_DOCUMENT_ID = "acceptAgreementDocumentID "
    const val PROFILE_IMAGE_STATUS = "profile_image_status"
    const val PROFILE_PICTURE = "ProfilePicture.jpg"
    const val ADJUSTER_AGREEMENT = "AdjusterAgreement.pdf"
    const val APPLIED_JOB = "AppliedJob"
    const val ACTIVE_ASSIGNMENT = "active"
    const val PENDING_ASSIGNMENT = "pending"
    const val CLOSED_ASSIGNMENT = "closed"
    const val INSURANCE_DOCUMENT = "Document"
    const val ASSIGNMENT_ID = "id"
    const val IS_RELEASE_REQUEST = "isReleaseRequest"
    const val START_DATE = "start_date"
    const val END_DATE = "end_date"
    const val WEEK = "week"
    const val LADDER_ASSIST_TYPE = "CTLA"
    const val ADJUSTER_TYPE = "CTAD"
    const val WEB_URL = "web_url"
    const val INSURER_NAME = "insurer_name"
    const val COVERGAE_AMOUNT = "coverage_amount"
    const val EFFECTIVE_DATE = "effective_date"
    const val EXPIRATION_DATE = "expiration_date"
    const val DOC_NAME = "doc_name"
    const val INSURANCE_ID = "doc_name"
    const val NO_INTERNET = "No Internet Connection"
    const val IS_SUGGESTED_JOB = "is_suggested_job"

    //Personal Info Prelim

    const val SIGNUP_REFERENCE_OTHER_KEY = "SROR"
    const val INDUSTRY_TYPE = "IINS"

    const val TEXT_INPUT = "TextInput"
    const val TEXT_AREA = "TextArea"
    const val DROPDOWN = "Dropdown"
    const val LOCATION = "Location"
    const val TELEPHONE = "Telephone"
    const val CAMERA = "Camera"
    const val FILE_UPLOAD = "FileUpload"
    const val CHECKBOXES = "Checkboxes"
    const val NUMBER_INPUT = "NumberInput"

    const val FACEBOOK_URL = "https://www.facebook.com/crawfordandco"
    const val TWITTER_URL = "https://twitter.com/crawco"
    const val LINKEDIN_URL = "https://www.linkedin.com/company/crawford-&-company/"
    const val YOUTUBE_URL = "https://www.youtube.com/channel/UCPA0SF10rW7pEe9yryUsdBA"


    const val MAIL_SUBJECT = "mail_subject"
    const val MAIL_FROM = "mail_from"
    const val MAIL_TO = "mail_to"
    const val MAIL_BODY = "mail_body"

    const val JOB_ID = "job_id"
    const val LOC = "location"
    const val IS_ACCOUNT_PAYMENT_VALID = "isAccountPreValid"
    const val IS_AGREEMENT_ACCEPTED = "isAgreementAccepted"
    const val INSPECTION_SCHEDULE_CHOICE = "inspectionScheduleChoice"
    const val TIME = "time"
    const val DATE = "date"
    const val HOVER_JOB_ID = "hover_job_id"
    const val HOVER_STATUS_ID = "hover_status_id"
    const val MAIL_TYPE = "mail_type"
    const val NOTIFICATION_TYPE = "notification_type"
    const val NOTIFICATION_TASK_ID = "notification_task_id"
    const val NOTIFICATION_JOB_TYPE = "notification_job_type"
    const val IS_NOTIFICATION_UPDATED = "is_notification_updated"
    const val IS_COMING_FROM_NOTIFICATION = "is_coming_from_notification"
    const val IS_COMING_FROM_PUSH_NOTIFICATION = "is_coming_from_push_notification"
    const val NOTIFICATION_ID = "notification_id"
    const val IS_HOVER_REQUIRED = "is_hover_required"


    const val MATCHED_JOB_TYPE = "AD_MJ"
    const val SUGGESTED_JOB_TYPE = "SJA"
    const val SCHEDULE_TYPE = "AD_SA"
    const val LATEST_UPDATE_TYPE = "AD_LU"
    const val QA_FAILURE_TYPE = "AD_QF"
    const val CALENDER_TYPE = "AD_CL"
    const val RECENT_MESSAGE_TYPE = "AD_RM"
    const val TERMINATED_ASSIGNMENT_TYPE = "AD_TA"

    const val JOB_TYPE = "JOB"
    const val ASSIGNMENT_TYPE = "ASSIGNMENT"

    const val MATCHED_jOB = "JBMT"
    const val SAVED_jOB = "JBSV"
    const val SUGGESTED_jOB = "JBSG"

    // assignment
    const val SCHEDULED_ASSIGNMENT = "ASCH"
    const val IN_PROGRESS_ASSIGNMENT = "AINP"
    const val COMPLETED_ASSIGNMENT = "ACOM"
    const val TERMINATED_ASSIGNMENT = "ATER"

    //Assignmnet submit report
    const val CARRIER_USER_DETAIL_ID = "carrier_UserDetailID"
    const val FORM_TYPE_ID = "formTypeID"
    const val ASSIGNMENT_JOB_ID = "jobID"
    const val ID = "id"
    const val VALUE = "value"
    const val IS_ARRAY = "isArray"
    const val FIELD_TYPE = "fieldType"
    const val FORM_DATA = "formData"
    const val CUSTOM_FORM_DATA = "customFormData"
    const val INCLUDE_DESCRIPTION = "Include Description"

    // report
    const val APPROVED_REPORT = "ARCA"
    const val SUBMITTED_REPORT = "ARSB"
    const val QA_FAILURE_REPORT_CLIENT = "ARCR"
    const val QA_FAILURE_REPORT_STAFF = "ARSJ"


    const val NOTIFICATION_MATCHED_JOB = "AJM"
    const val NOTIFICATION_SCHEDULED_ASSIGNMENT = "ASA"
    const val NOTIFICATION_CANCELLED_ASSIGNMENT = "ACC"
    const val NOTIFICATION_TERMINATED_ASSIGNMENT_STAFF = "TAS"
    const val NOTIFICATION_TERMINATED_ASSIGNMENT_CLIENT = "CTA"
    const val NOTIFICATION_RESCHEDULE_ASSIGNMENT = "RAS"
    const val NOTIFICATION_RESCHEDULE_CARRIER = "RAC"
    const val NOTIFICATION_APPROVED_REPORT = "CRA"
    const val NOTIFICATION_REMINDER_ADJUSTER = "SRA"
    const val NOTIFICATION_QA_FAILURE_REPORT = "CRF"
    const val NOTIFICATION_RATING_FEEDBACK = "ACE"
    const val ISWILLING_RELOCATE = "is_willing_relocate"
    const val IS_CRAWFORD_EMPLOYEE = "is_crawford_employee"

    //Assignment Report Status

    const val REPORT_STATUS_PENDING = ""
    const val REPORT_STATUS_WAITING = "ARSB"
    const val REPORT_STATUS_COMPLETED = "ARCA"
    const val REPORT_STATUS_FAILURE_STAFF = "ARSJ"
    const val REPORT_STATUS_FAILURE_CLIENT = "ARCR"

    // Assignment Hover Status

    const val HOVER_STATUS_NEW = "new"
    const val HOVER_STATUS_WAITING = "Waiting"
    const val HOVER_STATUS_COMPLETE = "complete"
    const val HOVER_STATUS_DELETE = "deleted"
}