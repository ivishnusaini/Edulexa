package com.edulexa.activity.staff.dashboard.model

class DashboardModel(moduleImage : Int,moduleName : String) {
    var moduleImage : Int? = null
    var moduleName : String? = null

    init {
        this.moduleImage = moduleImage
        this.moduleName = moduleName
    }

    fun getModuleImage() : Int{
        return moduleImage!!
    }
    fun setModuleImage(moduleImage : Int){
        this.moduleImage = moduleImage
    }

    @JvmName("getModuleName1")
    fun getModuleName() : String{
        return moduleName!!
    }
    @JvmName("setModuleName1")
    fun setModuleName(moduleName : String){
        this.moduleName = moduleName
    }
}