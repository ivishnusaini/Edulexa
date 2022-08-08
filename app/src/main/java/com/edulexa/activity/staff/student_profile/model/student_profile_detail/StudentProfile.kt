package com.edulexa.activity.staff.student_profile.model.student_profile_detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StudentProfile {
    @SerializedName("session_id")
    @Expose
    private var sessionId: String? = null

    @SerializedName("transport_fees")
    @Expose
    private var transportFees: String? = null

    @SerializedName("vehroute_id")
    @Expose
    private var vehrouteId: String? = null

    @SerializedName("route_id")
    @Expose
    private var routeId: Any? = null

    @SerializedName("vehicle_id")
    @Expose
    private var vehicleId: Any? = null

    @SerializedName("route_title")
    @Expose
    private var routeTitle: Any? = null

    @SerializedName("vehicle_no")
    @Expose
    private var vehicleNo: Any? = null

    @SerializedName("room_no")
    @Expose
    private var roomNo: Any? = null

    @SerializedName("driver_name")
    @Expose
    private var driverName: Any? = null

    @SerializedName("driver_contact")
    @Expose
    private var driverContact: Any? = null

    @SerializedName("hostel_id")
    @Expose
    private var hostelId: Any? = null

    @SerializedName("hostel_name")
    @Expose
    private var hostelName: Any? = null

    @SerializedName("room_type_id")
    @Expose
    private var roomTypeId: Any? = null

    @SerializedName("room_type")
    @Expose
    private var roomType: Any? = null

    @SerializedName("hostel_room_id")
    @Expose
    private var hostelRoomId: String? = null

    @SerializedName("student_session_id")
    @Expose
    private var studentSessionId: String? = null

    @SerializedName("fees_discount")
    @Expose
    private var feesDiscount: String? = null

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

    @SerializedName("id")
    @Expose
    private var id: String? = null

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
    private var state: Any? = null

    @SerializedName("city")
    @Expose
    private var city: Any? = null

    @SerializedName("pincode")
    @Expose
    private var pincode: Any? = null

    @SerializedName("religion")
    @Expose
    private var religion: String? = null

    @SerializedName("cast")
    @Expose
    private var cast: String? = null

    @SerializedName("house_name")
    @Expose
    private var houseName: Any? = null

    @SerializedName("dob")
    @Expose
    private var dob: String? = null

    @SerializedName("current_address")
    @Expose
    private var currentAddress: String? = null

    @SerializedName("previous_school")
    @Expose
    private var previousSchool: String? = null

    @SerializedName("guardian_is")
    @Expose
    private var guardianIs: String? = null

    @SerializedName("parent_id")
    @Expose
    private var parentId: String? = null

    @SerializedName("permanent_address")
    @Expose
    private var permanentAddress: String? = null

    @SerializedName("category_id")
    @Expose
    private var categoryId: String? = null

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

    @SerializedName("father_pic")
    @Expose
    private var fatherPic: String? = null

    @SerializedName("height")
    @Expose
    private var height: String? = null

    @SerializedName("weight")
    @Expose
    private var weight: String? = null

    @SerializedName("measurement_date")
    @Expose
    private var measurementDate: String? = null

    @SerializedName("mother_pic")
    @Expose
    private var motherPic: String? = null

    @SerializedName("guardian_pic")
    @Expose
    private var guardianPic: String? = null

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

    @SerializedName("father_phone")
    @Expose
    private var fatherPhone: String? = null

    @SerializedName("blood_group")
    @Expose
    private var bloodGroup: String? = null

    @SerializedName("school_house_id")
    @Expose
    private var schoolHouseId: String? = null

    @SerializedName("father_occupation")
    @Expose
    private var fatherOccupation: String? = null

    @SerializedName("mother_name")
    @Expose
    private var motherName: String? = null

    @SerializedName("mother_phone")
    @Expose
    private var motherPhone: Any? = null

    @SerializedName("mother_occupation")
    @Expose
    private var motherOccupation: String? = null

    @SerializedName("guardian_occupation")
    @Expose
    private var guardianOccupation: String? = null

    @SerializedName("gender")
    @Expose
    private var gender: String? = null

    @SerializedName("rte")
    @Expose
    private var rte: String? = null

    @SerializedName("guardian_email")
    @Expose
    private var guardianEmail: String? = null

    @SerializedName("username")
    @Expose
    private var username: String? = null

    @SerializedName("password")
    @Expose
    private var password: String? = null

    @SerializedName("dis_reason")
    @Expose
    private var disReason: String? = null

    @SerializedName("dis_note")
    @Expose
    private var disNote: String? = null

    @SerializedName("disable_at")
    @Expose
    private var disableAt: Any? = null

    @SerializedName("is_disable")
    @Expose
    private var isDisable: String? = null

    fun getSessionId(): String? {
        return sessionId
    }

    fun setSessionId(sessionId: String?) {
        this.sessionId = sessionId
    }

    fun getTransportFees(): String? {
        return transportFees
    }

    fun setTransportFees(transportFees: String?) {
        this.transportFees = transportFees
    }

    fun getVehrouteId(): String? {
        return vehrouteId
    }

    fun setVehrouteId(vehrouteId: String?) {
        this.vehrouteId = vehrouteId
    }

    fun getRouteId(): Any? {
        return routeId
    }

    fun setRouteId(routeId: Any?) {
        this.routeId = routeId
    }

    fun getVehicleId(): Any? {
        return vehicleId
    }

    fun setVehicleId(vehicleId: Any?) {
        this.vehicleId = vehicleId
    }

    fun getRouteTitle(): Any? {
        return routeTitle
    }

    fun setRouteTitle(routeTitle: Any?) {
        this.routeTitle = routeTitle
    }

    fun getVehicleNo(): Any? {
        return vehicleNo
    }

    fun setVehicleNo(vehicleNo: Any?) {
        this.vehicleNo = vehicleNo
    }

    fun getRoomNo(): Any? {
        return roomNo
    }

    fun setRoomNo(roomNo: Any?) {
        this.roomNo = roomNo
    }

    fun getDriverName(): Any? {
        return driverName
    }

    fun setDriverName(driverName: Any?) {
        this.driverName = driverName
    }

    fun getDriverContact(): Any? {
        return driverContact
    }

    fun setDriverContact(driverContact: Any?) {
        this.driverContact = driverContact
    }

    fun getHostelId(): Any? {
        return hostelId
    }

    fun setHostelId(hostelId: Any?) {
        this.hostelId = hostelId
    }

    fun getHostelName(): Any? {
        return hostelName
    }

    fun setHostelName(hostelName: Any?) {
        this.hostelName = hostelName
    }

    fun getRoomTypeId(): Any? {
        return roomTypeId
    }

    fun setRoomTypeId(roomTypeId: Any?) {
        this.roomTypeId = roomTypeId
    }

    fun getRoomType(): Any? {
        return roomType
    }

    fun setRoomType(roomType: Any?) {
        this.roomType = roomType
    }

    fun getHostelRoomId(): String? {
        return hostelRoomId
    }

    fun setHostelRoomId(hostelRoomId: String?) {
        this.hostelRoomId = hostelRoomId
    }

    fun getStudentSessionId(): String? {
        return if (studentSessionId == null) "" else studentSessionId
    }

    fun setStudentSessionId(studentSessionId: String?) {
        this.studentSessionId = studentSessionId
    }

    fun getFeesDiscount(): String? {
        return feesDiscount
    }

    fun setFeesDiscount(feesDiscount: String?) {
        this.feesDiscount = feesDiscount
    }

    fun getClassId(): String? {
        return if (classId == null) "" else classId
    }

    fun setClassId(classId: String?) {
        this.classId = classId
    }

    fun getClass_(): String? {
        return if (_class == null) "" else _class
    }

    fun setClass_(_class: String?) {
        this._class = _class
    }

    fun getSectionId(): String? {
        return if (sectionId == null) "" else sectionId
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

    fun getId(): String? {
        return if (id == null) "" else id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getAdmissionNo(): String? {
        return admissionNo
    }

    fun setAdmissionNo(admissionNo: String?) {
        this.admissionNo = admissionNo
    }

    fun getRollNo(): String? {
        return rollNo
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
        return image
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

    fun getState(): Any? {
        return state
    }

    fun setState(state: Any?) {
        this.state = state
    }

    fun getCity(): Any? {
        return city
    }

    fun setCity(city: Any?) {
        this.city = city
    }

    fun getPincode(): Any? {
        return pincode
    }

    fun setPincode(pincode: Any?) {
        this.pincode = pincode
    }

    fun getReligion(): String? {
        return religion
    }

    fun setReligion(religion: String?) {
        this.religion = religion
    }

    fun getCast(): String? {
        return cast
    }

    fun setCast(cast: String?) {
        this.cast = cast
    }

    fun getHouseName(): Any? {
        return houseName
    }

    fun setHouseName(houseName: Any?) {
        this.houseName = houseName
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

    fun getPreviousSchool(): String? {
        return previousSchool
    }

    fun setPreviousSchool(previousSchool: String?) {
        this.previousSchool = previousSchool
    }

    fun getGuardianIs(): String? {
        return guardianIs
    }

    fun setGuardianIs(guardianIs: String?) {
        this.guardianIs = guardianIs
    }

    fun getParentId(): String? {
        return parentId
    }

    fun setParentId(parentId: String?) {
        this.parentId = parentId
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

    fun getFatherPic(): String? {
        return fatherPic
    }

    fun setFatherPic(fatherPic: String?) {
        this.fatherPic = fatherPic
    }

    fun getHeight(): String? {
        return height
    }

    fun setHeight(height: String?) {
        this.height = height
    }

    fun getWeight(): String? {
        return weight
    }

    fun setWeight(weight: String?) {
        this.weight = weight
    }

    fun getMeasurementDate(): String? {
        return measurementDate
    }

    fun setMeasurementDate(measurementDate: String?) {
        this.measurementDate = measurementDate
    }

    fun getMotherPic(): String? {
        return motherPic
    }

    fun setMotherPic(motherPic: String?) {
        this.motherPic = motherPic
    }

    fun getGuardianPic(): String? {
        return guardianPic
    }

    fun setGuardianPic(guardianPic: String?) {
        this.guardianPic = guardianPic
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

    fun getFatherPhone(): String? {
        return fatherPhone
    }

    fun setFatherPhone(fatherPhone: String?) {
        this.fatherPhone = fatherPhone
    }

    fun getBloodGroup(): String? {
        return bloodGroup
    }

    fun setBloodGroup(bloodGroup: String?) {
        this.bloodGroup = bloodGroup
    }

    fun getSchoolHouseId(): String? {
        return schoolHouseId
    }

    fun setSchoolHouseId(schoolHouseId: String?) {
        this.schoolHouseId = schoolHouseId
    }

    fun getFatherOccupation(): String? {
        return fatherOccupation
    }

    fun setFatherOccupation(fatherOccupation: String?) {
        this.fatherOccupation = fatherOccupation
    }

    fun getMotherName(): String? {
        return motherName
    }

    fun setMotherName(motherName: String?) {
        this.motherName = motherName
    }

    fun getMotherPhone(): Any? {
        return motherPhone
    }

    fun setMotherPhone(motherPhone: Any?) {
        this.motherPhone = motherPhone
    }

    fun getMotherOccupation(): String? {
        return motherOccupation
    }

    fun setMotherOccupation(motherOccupation: String?) {
        this.motherOccupation = motherOccupation
    }

    fun getGuardianOccupation(): String? {
        return guardianOccupation
    }

    fun setGuardianOccupation(guardianOccupation: String?) {
        this.guardianOccupation = guardianOccupation
    }

    fun getGender(): String? {
        return gender
    }

    fun setGender(gender: String?) {
        this.gender = gender
    }

    fun getRte(): String? {
        return rte
    }

    fun setRte(rte: String?) {
        this.rte = rte
    }

    fun getGuardianEmail(): String? {
        return guardianEmail
    }

    fun setGuardianEmail(guardianEmail: String?) {
        this.guardianEmail = guardianEmail
    }

    fun getUsername(): String? {
        return username
    }

    fun setUsername(username: String?) {
        this.username = username
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String?) {
        this.password = password
    }

    fun getDisReason(): String? {
        return disReason
    }

    fun setDisReason(disReason: String?) {
        this.disReason = disReason
    }

    fun getDisNote(): String? {
        return disNote
    }

    fun setDisNote(disNote: String?) {
        this.disNote = disNote
    }

    fun getDisableAt(): Any? {
        return disableAt
    }

    fun setDisableAt(disableAt: Any?) {
        this.disableAt = disableAt
    }

    fun getIsDisable(): String? {
        return isDisable
    }

    fun setIsDisable(isDisable: String?) {
        this.isDisable = isDisable
    }
}