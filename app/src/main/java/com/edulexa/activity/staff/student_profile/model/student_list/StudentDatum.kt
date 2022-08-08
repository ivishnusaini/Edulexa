package com.edulexa.activity.staff.student_profile.model.student_list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StudentDatum {
    @SerializedName("class_id")
    @Expose
    private var classId: String? = null

    @SerializedName("token")
    @Expose
    private var token: String? = null

    @SerializedName("student_session_id")
    @Expose
    private var studentSessionId: String? = null

    @SerializedName("id")
    @Expose
    private var id: String? = null

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
    private var rollNo: String? = null

    @SerializedName("admission_date")
    @Expose
    private var admissionDate: String? = null

    @SerializedName("firstname")
    @Expose
    private var firstname: String? = null

    @SerializedName("lastname")
    @Expose
    private var lastname: String? = null

    @SerializedName("image")
    @Expose
    private var image: String? = null

    @SerializedName("mobileno")
    @Expose
    private var mobileno: String? = null

    @SerializedName("email")
    @Expose
    private var email: String? = null

    @SerializedName("state")
    @Expose
    private var state: String? = null

    @SerializedName("city")
    @Expose
    private var city: String? = null

    @SerializedName("pincode")
    @Expose
    private var pincode: String? = null

    @SerializedName("religion")
    @Expose
    private var religion: String? = null

    @SerializedName("dob")
    @Expose
    private var dob: String? = null

    @SerializedName("current_address")
    @Expose
    private var currentAddress: String? = null

    @SerializedName("permanent_address")
    @Expose
    private var permanentAddress: String? = null

    @SerializedName("category_id")
    @Expose
    private var categoryId: String? = null

    @SerializedName("category")
    @Expose
    private var category: String? = null

    @SerializedName("adhar_no")
    @Expose
    private var adharNo: String? = null

    @SerializedName("samagra_id")
    @Expose
    private var samagraId: String? = null

    @SerializedName("bank_account_no")
    @Expose
    private var bankAccountNo: String? = null

    @SerializedName("bank_name")
    @Expose
    private var bankName: String? = null

    @SerializedName("ifsc_code")
    @Expose
    private var ifscCode: String? = null

    @SerializedName("guardian_name")
    @Expose
    private var guardianName: String? = null

    @SerializedName("guardian_relation")
    @Expose
    private var guardianRelation: String? = null

    @SerializedName("guardian_phone")
    @Expose
    private var guardianPhone: String? = null

    @SerializedName("guardian_address")
    @Expose
    private var guardianAddress: String? = null

    @SerializedName("is_active")
    @Expose
    private var isActive: String? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null

    @SerializedName("father_name")
    @Expose
    private var fatherName: String? = null

    @SerializedName("mother_name")
    @Expose
    private var motherName: String? = null

    @SerializedName("rte")
    @Expose
    private var rte: String? = null

    @SerializedName("school_house_id")
    @Expose
    private var schoolHouseId: String? = null

    @SerializedName("gender")
    @Expose
    private var gender: String? = null

    fun getClassId(): String? {
        return classId
    }

    fun setClassId(classId: String?) {
        this.classId = classId
    }

    fun getStudentSessionId(): String? {
        return if (studentSessionId == null) "" else studentSessionId
    }

    fun setStudentSessionId(studentSessionId: String?) {
        this.studentSessionId = studentSessionId
    }

    fun getId(): String? {
        return if (id == null) "" else id
    }

    fun getToken(): String? {
        return if (token == null) "" else token
    }

    fun setToken(token: String?) {
        this.token = token
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getClass_(): String? {
        return if (_class == null) "" else _class
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
        return if (section == null) "" else section
    }

    fun setSection(section: String?) {
        this.section = section
    }

    fun getAdmissionNo(): String? {
        return if (admissionNo == null) "" else admissionNo
    }

    fun setAdmissionNo(admissionNo: String?) {
        this.admissionNo = admissionNo
    }

    fun getRollNo(): String? {
        return if (rollNo == null) "" else rollNo
    }

    fun setRollNo(rollNo: String?) {
        this.rollNo = rollNo
    }

    fun getAdmissionDate(): String? {
        return admissionDate
    }

    fun setAdmissionDate(admissionDate: String?) {
        this.admissionDate = admissionDate
    }

    fun getFirstname(): String? {
        return if (firstname == null) "" else firstname
    }

    fun setFirstname(firstname: String?) {
        this.firstname = firstname
    }

    fun getLastname(): String? {
        return if (lastname == null) "" else lastname
    }

    fun setLastname(lastname: String?) {
        this.lastname = lastname
    }

    fun getImage(): String? {
        return if (image == null) "" else image
    }

    fun setImage(image: String?) {
        this.image = image
    }

    fun getMobileno(): String? {
        return mobileno
    }

    fun setMobileno(mobileno: String?) {
        this.mobileno = mobileno
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String?) {
        this.email = email
    }

    fun getState(): String? {
        return state
    }

    fun setState(state: String?) {
        this.state = state
    }

    fun getCity(): String? {
        return city
    }

    fun setCity(city: String?) {
        this.city = city
    }

    fun getPincode(): String? {
        return pincode
    }

    fun setPincode(pincode: String?) {
        this.pincode = pincode
    }

    fun getReligion(): String? {
        return religion
    }

    fun setReligion(religion: String?) {
        this.religion = religion
    }

    fun getDob(): String? {
        return dob
    }

    fun setDob(dob: String?) {
        this.dob = dob
    }

    fun getCurrentAddress(): String? {
        return currentAddress
    }

    fun setCurrentAddress(currentAddress: String?) {
        this.currentAddress = currentAddress
    }

    fun getPermanentAddress(): String? {
        return permanentAddress
    }

    fun setPermanentAddress(permanentAddress: String?) {
        this.permanentAddress = permanentAddress
    }

    fun getCategoryId(): String? {
        return categoryId
    }

    fun setCategoryId(categoryId: String?) {
        this.categoryId = categoryId
    }

    fun getCategory(): String? {
        return category
    }

    fun setCategory(category: String?) {
        this.category = category
    }

    fun getAdharNo(): String? {
        return adharNo
    }

    fun setAdharNo(adharNo: String?) {
        this.adharNo = adharNo
    }

    fun getSamagraId(): String? {
        return samagraId
    }

    fun setSamagraId(samagraId: String?) {
        this.samagraId = samagraId
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

    fun getGuardianName(): String? {
        return guardianName
    }

    fun setGuardianName(guardianName: String?) {
        this.guardianName = guardianName
    }

    fun getGuardianRelation(): String? {
        return guardianRelation
    }

    fun setGuardianRelation(guardianRelation: String?) {
        this.guardianRelation = guardianRelation
    }

    fun getGuardianPhone(): String? {
        return guardianPhone
    }

    fun setGuardianPhone(guardianPhone: String?) {
        this.guardianPhone = guardianPhone
    }

    fun getGuardianAddress(): String? {
        return guardianAddress
    }

    fun setGuardianAddress(guardianAddress: String?) {
        this.guardianAddress = guardianAddress
    }

    fun getIsActive(): String? {
        return isActive
    }

    fun setIsActive(isActive: String?) {
        this.isActive = isActive
    }

    fun getCreatedAt(): String? {
        return createdAt
    }

    fun setCreatedAt(createdAt: String?) {
        this.createdAt = createdAt
    }

    fun getUpdatedAt(): String? {
        return updatedAt
    }

    fun setUpdatedAt(updatedAt: String?) {
        this.updatedAt = updatedAt
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

    fun getRte(): String? {
        return rte
    }

    fun setRte(rte: String?) {
        this.rte = rte
    }

    fun getSchoolHouseId(): String? {
        return schoolHouseId
    }

    fun setSchoolHouseId(schoolHouseId: String?) {
        this.schoolHouseId = schoolHouseId
    }

    fun getGender(): String? {
        return gender
    }

    fun setGender(gender: String?) {
        this.gender = gender
    }
}