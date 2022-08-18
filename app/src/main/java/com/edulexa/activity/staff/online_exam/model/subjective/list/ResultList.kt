package com.edulexa.activity.staff.online_exam.model.subjective.list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResultList {
    @SerializedName("exam_id")
    @Expose
    private var examId: String? = null

    @SerializedName("exam")
    @Expose
    private var exam: String? = null

    @SerializedName("attempt")
    @Expose
    private var attempt: String? = null

    @SerializedName("onlineexam_student_id")
    @Expose
    private var onlineexamStudentId: String? = null

    @SerializedName("total_counter")
    @Expose
    private var totalCounter: String? = null

    @SerializedName("student_session_id")
    @Expose
    private var studentSessionId: String? = null

    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("class_id")
    @Expose
    private var classId: String? = null

    @SerializedName("class")
    @Expose
    private var _class: String? = null

    @SerializedName("section_id")
    @Expose
    private var sectionId: String? = null

    @SerializedName("section")
    @Expose
    private var section: String? = null

    @SerializedName("admission_no")
    @Expose
    private var admissionNo: String? = null

    @SerializedName("roll_no")
    @Expose
    private var rollNo: Any? = null

    @SerializedName("firstname")
    @Expose
    private var firstname: String? = null

    @SerializedName("lastname")
    @Expose
    private var lastname: String? = null

    @SerializedName("mobileno")
    @Expose
    private var mobileno: Any? = null

    @SerializedName("email")
    @Expose
    private var email: Any? = null

    @SerializedName("guardian_name")
    @Expose
    private var guardianName: String? = null

    @SerializedName("father_name")
    @Expose
    private var fatherName: Any? = null

    fun getExamId(): String? {
        return examId
    }

    fun setExamId(examId: String?) {
        this.examId = examId
    }

    fun getExam(): String? {
        return exam
    }

    fun setExam(exam: String?) {
        this.exam = exam
    }

    fun getAttempt(): String? {
        return attempt
    }

    fun setAttempt(attempt: String?) {
        this.attempt = attempt
    }

    fun getOnlineexamStudentId(): String? {
        if (onlineexamStudentId == null)
            return ""
        return onlineexamStudentId
    }

    fun setOnlineexamStudentId(onlineexamStudentId: String?) {
        this.onlineexamStudentId = onlineexamStudentId
    }

    fun getTotalCounter(): String? {
        return totalCounter
    }

    fun setTotalCounter(totalCounter: String?) {
        this.totalCounter = totalCounter
    }

    fun getStudentSessionId(): String? {
        return studentSessionId
    }

    fun setStudentSessionId(studentSessionId: String?) {
        this.studentSessionId = studentSessionId
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getClassId(): String? {
        return classId
    }

    fun setClassId(classId: String?) {
        this.classId = classId
    }

    fun getClass_(): String? {
        return _class
    }

    fun setClass_(_class: String?) {
        this._class = _class
    }

    fun getSectionId(): String? {
        return sectionId
    }

    fun setSectionId(sectionId: String?) {
        this.sectionId = sectionId
    }

    fun getSection(): String? {
        return section
    }

    fun setSection(section: String?) {
        this.section = section
    }

    fun getAdmissionNo(): String? {
        return admissionNo
    }

    fun setAdmissionNo(admissionNo: String?) {
        this.admissionNo = admissionNo
    }

    fun getRollNo(): Any? {
        return if (rollNo == null) "null" else rollNo
    }

    fun setRollNo(rollNo: Any?) {
        this.rollNo = rollNo
    }

    fun getFirstname(): String? {
        return if (firstname == null) "null" else firstname
    }

    fun setFirstname(firstname: String?) {
        this.firstname = firstname
    }

    fun getLastname(): String? {
        return lastname
    }

    fun setLastname(lastname: String?) {
        this.lastname = lastname
    }

    fun getMobileno(): Any? {
        return mobileno
    }

    fun setMobileno(mobileno: Any?) {
        this.mobileno = mobileno
    }

    fun getEmail(): Any? {
        return email
    }

    fun setEmail(email: Any?) {
        this.email = email
    }

    fun getGuardianName(): String? {
        return guardianName
    }

    fun setGuardianName(guardianName: String?) {
        this.guardianName = guardianName
    }

    fun getFatherName(): Any? {
        return fatherName
    }

    fun setFatherName(fatherName: Any?) {
        this.fatherName = fatherName
    }
}