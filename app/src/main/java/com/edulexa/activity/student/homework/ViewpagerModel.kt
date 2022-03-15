package com.edulexa.activity.student.homework

class ViewpagerModel(name:String,selectFlag:Boolean) {
    var name:String? = null
    var selectFlag:Boolean? = false
    init {
        this.name = name
        this.selectFlag = selectFlag
    }
    fun getTabName(): String? {
        return name
    }

    fun setTabName(name:String) {
        this.name = name
    }
    fun getSelectStatus(): Boolean? {
        return selectFlag
    }

    fun setSelectStatus(selectFlag:Boolean) {
        this.selectFlag = selectFlag
    }
}