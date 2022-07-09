package com.edulexa.activity.student.class_timetable.model

class ClassTimetableModel {
    var dayName = ""
    var selectedFlag = false
    var dayWiseList : List<DayWiseListModel>? = null

    @JvmName("getDayName1")
    fun getDayName() : String{
        return dayName
    }

    @JvmName("setDayName1")
    fun setDayName(dayName: String){
        this.dayName = dayName
    }

    @JvmName("getDayWiseList1")
    fun getDayWiseList() : List<DayWiseListModel>?{
        return dayWiseList
    }

    @JvmName("setDayWiseList1")
    fun setDayWiseList(dayWiseList: List<DayWiseListModel>){
        this.dayWiseList = dayWiseList
    }

    @JvmName("getSelectedFlag1")
    fun getSelectedFlag() : Boolean{
        return selectedFlag
    }
    @JvmName("setSelectedFlag1")
    fun setSelectedFlag(selectedFlag: Boolean){
        this.selectedFlag = selectedFlag
    }
}