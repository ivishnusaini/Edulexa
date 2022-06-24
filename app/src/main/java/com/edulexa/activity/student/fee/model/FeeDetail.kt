package com.edulexa.activity.student.fee.model

class FeeDetail {
    private var amount = ""
    private var due_date = ""
    private var feetype_id = ""
    private var fee_groups_feetype_id = ""
    private var fee_groups_id = ""
    private var fee_session_group_id = ""
    private var id = ""
    private var is_active = ""
    private var name = ""
    private var status = ""
    private var student_fees_deposite_id = ""
    private var student_session_id = ""
    private var total_amount_discount = ""
    private var total_amount_display = ""
    private var total_amount_fine = ""
    private var total_amount_paid = ""
    private var total_amount_remaining = ""
    private var type = ""

    fun getAmount(): String {
        return amount
    }
    fun setAmount(amount: String) {
        this.amount = amount
    }

    fun getDueDate(): String {
        return due_date
    }
    fun setDueDate(due_date: String) {
        this.due_date = due_date
    }

    fun getFeeTypeId(): String {
        return feetype_id
    }
    fun setFeeTypeId(feetype_id: String) {
        this.feetype_id = feetype_id
    }

    fun getFeeGroupdFeeTypeId(): String {
        return fee_groups_feetype_id
    }
    fun setFeeGroupdFeeTypeId(fee_groups_feetype_id: String) {
        this.fee_groups_feetype_id = fee_groups_feetype_id
    }

    fun getFeeGroupsId(): String {
        return fee_groups_id
    }
    fun setFeeGroupsId(fee_groups_id: String) {
        this.fee_groups_id = fee_groups_id
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

    fun getName(): String {
        return name
    }
    fun setName(name: String) {
        this.name = name
    }

    fun getStatus(): String {
        return status
    }
    fun setStatus(status: String) {
        this.status = status
    }

    fun getStudentFeesDepositeId(): String {
        return student_fees_deposite_id
    }
    fun setStudentFeesDepositeId(student_fees_deposite_id: String) {
        this.student_fees_deposite_id = student_fees_deposite_id
    }

    fun getStudentSessionId(): String {
        return student_session_id
    }
    fun setStudentSessionId(student_session_id: String) {
        this.student_session_id = student_session_id
    }

    fun getTotalAmountDiscount(): String {
        return total_amount_discount
    }
    fun setTotalAmountDiscount(total_amount_discount: String) {
        this.total_amount_discount = total_amount_discount
    }

    fun getTotalAmountDisplay(): String {
        return total_amount_display
    }
    fun setTotalAmountDisplay(total_amount_display: String) {
        this.total_amount_display = total_amount_display
    }

    fun getTotalAmountFine(): String {
        return total_amount_fine
    }
    fun setTotalAmountFine(total_amount_fine: String) {
        this.total_amount_fine = total_amount_fine
    }

    fun getTotalAmountPaid(): String {
        return total_amount_paid
    }
    fun setTotalAmountPaid(total_amount_paid: String) {
        this.total_amount_paid = total_amount_paid
    }

    fun getTotalAmountRemaining(): String {
        return total_amount_remaining
    }
    fun setTotalAmountRemaining(total_amount_remaining: String) {
        this.total_amount_remaining = total_amount_remaining
    }

    fun getType(): String {
        return type
    }
    fun setType(type: String) {
        this.type = type
    }
}