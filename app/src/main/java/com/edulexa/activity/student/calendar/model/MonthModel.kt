package com.edulexa.activity.student.calendar.model

class MonthModel(name:String,selectFlag:Boolean,dateStartFlag:Boolean,currentDateFlag:Boolean,completeDate:String) {
    var name:String? = null
    var selectFlag:Boolean? = false
    var dateStartFlag:Boolean? = false
    var currentDateFlag:Boolean? = false
    var completeDate:String? = null
    init {
        this.name = name
        this.selectFlag = selectFlag
        this.dateStartFlag = dateStartFlag
        this.currentDateFlag = currentDateFlag
        this.completeDate = completeDate
    }
    fun getDay(): String? {
        return name
    }

    fun setDay(name:String) {
        this.name = name
    }
    fun getSelectStatus(): Boolean? {
        return selectFlag
    }

    fun setSelectStatus(selectFlag:Boolean) {
        this.selectFlag = selectFlag
    }
    fun getStartDateFlag(): Boolean? {
        return dateStartFlag
    }

    fun setStartDateFlag(dateStartFlag:Boolean) {
        this.dateStartFlag = dateStartFlag
    }
    @JvmName("getCurrentDateFlag1")
    fun getCurrentDateFlag(): Boolean? {
        return currentDateFlag
    }

    fun setCurrentDateFlag(currentDateFlag:Boolean) {
        this.currentDateFlag = currentDateFlag
    }
    @JvmName("getCurrentDateFlag1")
    fun getCompleteDate(): String? {
        return completeDate
    }

    @JvmName("setCompleteDate1")
    fun setCompleteDate(completeDate:String) {
        this.completeDate = completeDate
    }
}