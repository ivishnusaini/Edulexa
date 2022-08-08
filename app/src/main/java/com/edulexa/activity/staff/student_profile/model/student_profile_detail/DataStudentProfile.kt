package com.edulexa.activity.staff.student_profile.model.student_profile_detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataStudentProfile {
    @SerializedName("permission")
    @Expose
    private var permission: PermissionStudentProfile? = null

    @SerializedName("token_data")
    @Expose
    private var tokenData: TokenDataStudentProfile? = null

    @SerializedName("session")
    @Expose
    private var session: Any? = null

    @SerializedName("student")
    @Expose
    private var student: StudentProfile? = null

    @SerializedName("class_section")
    @Expose
    private var classSection: List<ClassSection?>? = null

    @SerializedName("guardian_credential")
    @Expose
    private var guardianCredential: GuardianCredential? = null

    @SerializedName("reason")
    @Expose
    private var reason: List<ReasonStudentProfile?>? = null

    @SerializedName("reason_data")
    @Expose
    private var reasonData: Any? = null

    fun getPermission(): PermissionStudentProfile? {
        return permission
    }

    fun setPermission(permission: PermissionStudentProfile?) {
        this.permission = permission
    }

    fun getTokenData(): TokenDataStudentProfile? {
        return tokenData
    }

    fun setTokenData(tokenData: TokenDataStudentProfile?) {
        this.tokenData = tokenData
    }

    fun getSession(): Any? {
        return session
    }

    fun setSession(session: Any?) {
        this.session = session
    }

    fun getStudent(): StudentProfile? {
        return student
    }

    fun setStudent(student: StudentProfile?) {
        this.student = student
    }

    fun getClassSection(): List<ClassSection?>? {
        return classSection
    }

    fun setClassSection(classSection: List<ClassSection?>?) {
        this.classSection = classSection
    }

    fun getGuardianCredential(): GuardianCredential? {
        return guardianCredential
    }

    fun setGuardianCredential(guardianCredential: GuardianCredential?) {
        this.guardianCredential = guardianCredential
    }

    fun getReason(): List<ReasonStudentProfile?>? {
        return reason
    }

    fun setReason(reason: List<ReasonStudentProfile?>?) {
        this.reason = reason
    }

    fun getReasonData(): Any? {
        return reasonData
    }

    fun setReasonData(reasonData: Any?) {
        this.reasonData = reasonData
    }
}