package com.edulexa.activity.student.fee.model

class FeeModel {
    private var amount = ""
    private var created_at = ""
    private var fee_session_group_id = ""
    private var id = ""
    private var is_active = ""
    private var is_system = ""
    private var name = ""
    private var student_session_id = ""
    private var listFeeDetail : List<FeeDetail>? = null

    fun getAmount(): String {
        return amount
    }
    fun setAmount(amount: String) {
        this.amount = amount
    }

    fun getCreatedAt(): String {
        return created_at
    }
    fun setCreatedAt(created_at: String) {
        this.created_at = created_at
    }

    fun getFeeSessionGroupId(): String {
        return fee_session_group_id
    }
    fun setFeeSessionGroupId(fee_session_group_id: String) {
        this.fee_session_group_id = fee_session_group_id
    }

    fun getId(): String {
        return id
    }
    fun setId(id: String) {
        this.id = id
    }

    fun getIsActive(): String {
        return is_active
    }
    fun setIsActive(is_active: String) {
        this.is_active = is_active
    }

    fun getIsSystem(): String {
        return is_system
    }
    fun setIsSystem(is_system: String) {
        this.is_system = is_system
    }

    fun getName(): String {
        return name
    }
    fun setName(name: String) {
        this.name = name
    }

    fun getStudentSessionId(): String {
        return student_session_id
    }
    fun setStudentSessionId(student_session_id: String) {
        this.student_session_id = student_session_id
    }

    fun getFeeDetail(): List<FeeDetail>? {
        return listFeeDetail
    }
    fun setFeeDetail(listFeeDetail: List<FeeDetail>?) {
        this.listFeeDetail = listFeeDetail
    }
}