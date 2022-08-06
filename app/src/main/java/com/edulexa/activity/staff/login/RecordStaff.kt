package com.edulexa.activity.staff.login

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class RecordStaff {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("employee_id")
    @Expose
    private var employeeId: String? = null

    @SerializedName("lang_id")
    @Expose
    private var langId: String? = null

    @SerializedName("department")
    @Expose
    private var department: String? = null

    @SerializedName("designation")
    @Expose
    private var designation: String? = null

    @SerializedName("qualification")
    @Expose
    private var qualification: String? = null

    @SerializedName("work_exp")
    @Expose
    private var workExp: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("surname")
    @Expose
    private var surname: String? = null

    @SerializedName("father_name")
    @Expose
    private var fatherName: String? = null

    @SerializedName("mother_name")
    @Expose
    private var motherName: String? = null

    @SerializedName("contact_no")
    @Expose
    private var contactNo: String? = null

    @SerializedName("emergency_contact_no")
    @Expose
    private var emergencyContactNo: String? = null

    @SerializedName("email")
    @Expose
    private var email: String? = null

    @SerializedName("dob")
    @Expose
    private var dob: String? = null

    @SerializedName("marital_status")
    @Expose
    private var maritalStatus: String? = null

    @SerializedName("date_of_joining")
    @Expose
    private var dateOfJoining: String? = null

    @SerializedName("date_of_leaving")
    @Expose
    private var dateOfLeaving: String? = null

    @SerializedName("local_address")
    @Expose
    private var localAddress: String? = null

    @SerializedName("permanent_address")
    @Expose
    private var permanentAddress: String? = null

    @SerializedName("note")
    @Expose
    private var note: String? = null

    @SerializedName("image")
    @Expose
    private var image: String? = null

    @SerializedName("password")
    @Expose
    private var password: String? = null

    @SerializedName("gender")
    @Expose
    private var gender: String? = null

    @SerializedName("account_title")
    @Expose
    private var accountTitle: String? = null

    @SerializedName("bank_account_no")
    @Expose
    private var bankAccountNo: String? = null

    @SerializedName("bank_name")
    @Expose
    private var bankName: String? = null

    @SerializedName("ifsc_code")
    @Expose
    private var ifscCode: String? = null

    @SerializedName("bank_branch")
    @Expose
    private var bankBranch: String? = null

    @SerializedName("payscale")
    @Expose
    private var payscale: String? = null

    @SerializedName("basic_salary")
    @Expose
    private var basicSalary: String? = null

    @SerializedName("epf_no")
    @Expose
    private var epfNo: String? = null

    @SerializedName("contract_type")
    @Expose
    private var contractType: String? = null

    @SerializedName("shift")
    @Expose
    private var shift: String? = null

    @SerializedName("location")
    @Expose
    private var location: String? = null

    @SerializedName("facebook")
    @Expose
    private var facebook: String? = null

    @SerializedName("twitter")
    @Expose
    private var twitter: String? = null

    @SerializedName("linkedin")
    @Expose
    private var linkedin: String? = null

    @SerializedName("instagram")
    @Expose
    private var instagram: String? = null

    @SerializedName("resume")
    @Expose
    private var resume: String? = null

    @SerializedName("joining_letter")
    @Expose
    private var joiningLetter: String? = null

    @SerializedName("resignation_letter")
    @Expose
    private var resignationLetter: String? = null

    @SerializedName("other_document_name")
    @Expose
    private var otherDocumentName: String? = null

    @SerializedName("other_document_file")
    @Expose
    private var otherDocumentFile: String? = null

    @SerializedName("user_id")
    @Expose
    private var userId: String? = null

    @SerializedName("is_active")
    @Expose
    private var isActive: String? = null

    @SerializedName("verification_code")
    @Expose
    private var verificationCode: String? = null

    @SerializedName("rfid_no")
    @Expose
    private var rfidNo: String? = null

    @SerializedName("amount_type")
    @Expose
    private var amountType: String? = null

    @SerializedName("leave_amt")
    @Expose
    private var leaveAmt: Any? = null

    @SerializedName("late_amt")
    @Expose
    private var lateAmt: Any? = null

    @SerializedName("absent_amt")
    @Expose
    private var absentAmt: Any? = null

    @SerializedName("half_day_amt")
    @Expose
    private var halfDayAmt: Any? = null

    @SerializedName("subjects")
    @Expose
    private var subjects: String? = null

    @SerializedName("earamounttype")
    @Expose
    private var earamounttype: Any? = null

    @SerializedName("earning_amount")
    @Expose
    private var earningAmount: String? = null

    @SerializedName("dedamounttype")
    @Expose
    private var dedamounttype: Any? = null

    @SerializedName("deduction_amount")
    @Expose
    private var deductionAmount: String? = null

    @SerializedName("app_key")
    @Expose
    private var appKey: String? = null

    @SerializedName("meeting_id")
    @Expose
    private var meetingId: String? = null

    @SerializedName("passcode_id")
    @Expose
    private var passcodeId: String? = null

    @SerializedName("zoom_url")
    @Expose
    private var zoomUrl: String? = null

    @SerializedName("zoom_api_key")
    @Expose
    private var zoomApiKey: String? = null

    @SerializedName("zoom_secret_key")
    @Expose
    private var zoomSecretKey: String? = null

    @SerializedName("zoom_id")
    @Expose
    private var zoomId: String? = null

    @SerializedName("zoom_password")
    @Expose
    private var zoomPassword: String? = null

    @SerializedName("language")
    @Expose
    private var language: String? = null

    @SerializedName("language_id")
    @Expose
    private var languageId: String? = null

    @SerializedName("roles")
    @Expose
    private var roles: RolesStaff? = null

    fun getId(): String? {
        if (id == null)
            return ""
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getEmployeeId(): String? {
        return employeeId
    }

    fun setEmployeeId(employeeId: String?) {
        this.employeeId = employeeId
    }

    fun getLangId(): String? {
        return langId
    }

    fun setLangId(langId: String?) {
        this.langId = langId
    }

    fun getDepartment(): String? {
        return department
    }

    fun setDepartment(department: String?) {
        this.department = department
    }

    fun getDesignation(): String? {
        return designation
    }

    fun setDesignation(designation: String?) {
        this.designation = designation
    }

    fun getQualification(): String? {
        return qualification
    }

    fun setQualification(qualification: String?) {
        this.qualification = qualification
    }

    fun getWorkExp(): String? {
        return workExp
    }

    fun setWorkExp(workExp: String?) {
        this.workExp = workExp
    }

    fun getName(): String? {
        if (name == null)
            return ""
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getSurname(): String? {
        if (surname == null)
            return ""
        return surname
    }

    fun setSurname(surname: String?) {
        this.surname = surname
    }

    fun getFatherName(): String? {
        return fatherName
    }

    fun setFatherName(fatherName: String?) {
        this.fatherName = fatherName
    }

    fun getMotherName(): String? {
        return motherName
    }

    fun setMotherName(motherName: String?) {
        this.motherName = motherName
    }

    fun getContactNo(): String? {
        return contactNo
    }

    fun setContactNo(contactNo: String?) {
        this.contactNo = contactNo
    }

    fun getEmergencyContactNo(): String? {
        return emergencyContactNo
    }

    fun setEmergencyContactNo(emergencyContactNo: String?) {
        this.emergencyContactNo = emergencyContactNo
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String?) {
        this.email = email
    }

    fun getDob(): String? {
        return dob
    }

    fun setDob(dob: String?) {
        this.dob = dob
    }

    fun getMaritalStatus(): String? {
        return maritalStatus
    }

    fun setMaritalStatus(maritalStatus: String?) {
        this.maritalStatus = maritalStatus
    }

    fun getDateOfJoining(): String? {
        return dateOfJoining
    }

    fun setDateOfJoining(dateOfJoining: String?) {
        this.dateOfJoining = dateOfJoining
    }

    fun getDateOfLeaving(): String? {
        return dateOfLeaving
    }

    fun setDateOfLeaving(dateOfLeaving: String?) {
        this.dateOfLeaving = dateOfLeaving
    }

    fun getLocalAddress(): String? {
        return localAddress
    }

    fun setLocalAddress(localAddress: String?) {
        this.localAddress = localAddress
    }

    fun getPermanentAddress(): String? {
        return permanentAddress
    }

    fun setPermanentAddress(permanentAddress: String?) {
        this.permanentAddress = permanentAddress
    }

    fun getNote(): String? {
        return note
    }

    fun setNote(note: String?) {
        this.note = note
    }

    fun getImage(): String? {
        return image
    }

    fun setImage(image: String?) {
        this.image = image
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String?) {
        this.password = password
    }

    fun getGender(): String? {
        return gender
    }

    fun setGender(gender: String?) {
        this.gender = gender
    }

    fun getAccountTitle(): String? {
        return accountTitle
    }

    fun setAccountTitle(accountTitle: String?) {
        this.accountTitle = accountTitle
    }

    fun getBankAccountNo(): String? {
        return bankAccountNo
    }

    fun setBankAccountNo(bankAccountNo: String?) {
        this.bankAccountNo = bankAccountNo
    }

    fun getBankName(): String? {
        return bankName
    }

    fun setBankName(bankName: String?) {
        this.bankName = bankName
    }

    fun getIfscCode(): String? {
        return ifscCode
    }

    fun setIfscCode(ifscCode: String?) {
        this.ifscCode = ifscCode
    }

    fun getBankBranch(): String? {
        return bankBranch
    }

    fun setBankBranch(bankBranch: String?) {
        this.bankBranch = bankBranch
    }

    fun getPayscale(): String? {
        return payscale
    }

    fun setPayscale(payscale: String?) {
        this.payscale = payscale
    }

    fun getBasicSalary(): String? {
        return basicSalary
    }

    fun setBasicSalary(basicSalary: String?) {
        this.basicSalary = basicSalary
    }

    fun getEpfNo(): String? {
        return epfNo
    }

    fun setEpfNo(epfNo: String?) {
        this.epfNo = epfNo
    }

    fun getContractType(): String? {
        return contractType
    }

    fun setContractType(contractType: String?) {
        this.contractType = contractType
    }

    fun getShift(): String? {
        return shift
    }

    fun setShift(shift: String?) {
        this.shift = shift
    }

    fun getLocation(): String? {
        return location
    }

    fun setLocation(location: String?) {
        this.location = location
    }

    fun getFacebook(): String? {
        return facebook
    }

    fun setFacebook(facebook: String?) {
        this.facebook = facebook
    }

    fun getTwitter(): String? {
        return twitter
    }

    fun setTwitter(twitter: String?) {
        this.twitter = twitter
    }

    fun getLinkedin(): String? {
        return linkedin
    }

    fun setLinkedin(linkedin: String?) {
        this.linkedin = linkedin
    }

    fun getInstagram(): String? {
        return instagram
    }

    fun setInstagram(instagram: String?) {
        this.instagram = instagram
    }

    fun getResume(): String? {
        return resume
    }

    fun setResume(resume: String?) {
        this.resume = resume
    }

    fun getJoiningLetter(): String? {
        return joiningLetter
    }

    fun setJoiningLetter(joiningLetter: String?) {
        this.joiningLetter = joiningLetter
    }

    fun getResignationLetter(): String? {
        return resignationLetter
    }

    fun setResignationLetter(resignationLetter: String?) {
        this.resignationLetter = resignationLetter
    }

    fun getOtherDocumentName(): String? {
        return otherDocumentName
    }

    fun setOtherDocumentName(otherDocumentName: String?) {
        this.otherDocumentName = otherDocumentName
    }

    fun getOtherDocumentFile(): String? {
        return otherDocumentFile
    }

    fun setOtherDocumentFile(otherDocumentFile: String?) {
        this.otherDocumentFile = otherDocumentFile
    }

    fun getUserId(): String? {
        return userId
    }

    fun setUserId(userId: String?) {
        this.userId = userId
    }

    fun getIsActive(): String? {
        return isActive
    }

    fun setIsActive(isActive: String?) {
        this.isActive = isActive
    }

    fun getVerificationCode(): String? {
        return verificationCode
    }

    fun setVerificationCode(verificationCode: String?) {
        this.verificationCode = verificationCode
    }

    fun getRfidNo(): String? {
        return rfidNo
    }

    fun setRfidNo(rfidNo: String?) {
        this.rfidNo = rfidNo
    }

    fun getAmountType(): String? {
        return amountType
    }

    fun setAmountType(amountType: String?) {
        this.amountType = amountType
    }

    fun getLeaveAmt(): Any? {
        return leaveAmt
    }

    fun setLeaveAmt(leaveAmt: Any?) {
        this.leaveAmt = leaveAmt
    }

    fun getLateAmt(): Any? {
        return lateAmt
    }

    fun setLateAmt(lateAmt: Any?) {
        this.lateAmt = lateAmt
    }

    fun getAbsentAmt(): Any? {
        return absentAmt
    }

    fun setAbsentAmt(absentAmt: Any?) {
        this.absentAmt = absentAmt
    }

    fun getHalfDayAmt(): Any? {
        return halfDayAmt
    }

    fun setHalfDayAmt(halfDayAmt: Any?) {
        this.halfDayAmt = halfDayAmt
    }

    fun getSubjects(): String? {
        return subjects
    }

    fun setSubjects(subjects: String?) {
        this.subjects = subjects
    }

    fun getEaramounttype(): Any? {
        return earamounttype
    }

    fun setEaramounttype(earamounttype: Any?) {
        this.earamounttype = earamounttype
    }

    fun getEarningAmount(): String? {
        return earningAmount
    }

    fun setEarningAmount(earningAmount: String?) {
        this.earningAmount = earningAmount
    }

    fun getDedamounttype(): Any? {
        return dedamounttype
    }

    fun setDedamounttype(dedamounttype: Any?) {
        this.dedamounttype = dedamounttype
    }

    fun getDeductionAmount(): String? {
        return deductionAmount
    }

    fun setDeductionAmount(deductionAmount: String?) {
        this.deductionAmount = deductionAmount
    }

    fun getAppKey(): String? {
        return appKey
    }

    fun setAppKey(appKey: String?) {
        this.appKey = appKey
    }

    fun getMeetingId(): String? {
        if (meetingId == null)
            return ""
        return meetingId
    }

    fun setMeetingId(meetingId: String?) {
        this.meetingId = meetingId
    }

    fun getPasscodeId(): String? {
        return passcodeId
    }

    fun setPasscodeId(passcodeId: String?) {
        this.passcodeId = passcodeId
    }

    fun getZoomUrl(): String? {
        if (zoomUrl == null)
            return ""
        return zoomUrl
    }

    fun setZoomUrl(zoomUrl: String?) {
        this.zoomUrl = zoomUrl
    }

    fun getZoomApiKey(): String? {
        if (zoomApiKey == null)
            return ""
        return zoomApiKey
    }

    fun setZoomApiKey(zoomApiKey: String?) {
        this.zoomApiKey = zoomApiKey
    }

    fun getZoomSecretKey(): String? {
        if (zoomSecretKey == null)
            return ""
        return zoomSecretKey
    }

    fun setZoomSecretKey(zoomSecretKey: String?) {
        this.zoomSecretKey = zoomSecretKey
    }

    fun getZoomId(): String? {
        if (zoomId == null)
            return ""
        return zoomId
    }

    fun setZoomId(zoomId: String?) {
        this.zoomId = zoomId
    }

    fun getZoomPassword(): String? {
        if (zoomPassword == null)
            return ""
        return zoomPassword
    }

    fun setZoomPassword(zoomPassword: String?) {
        this.zoomPassword = zoomPassword
    }

    fun getLanguage(): String? {
        return language
    }

    fun setLanguage(language: String?) {
        this.language = language
    }

    fun getLanguageId(): String? {
        return languageId
    }

    fun setLanguageId(languageId: String?) {
        this.languageId = languageId
    }

    fun getRoles(): RolesStaff? {
        return roles
    }

    fun setRoles(roles: RolesStaff?) {
        this.roles = roles
    }
}