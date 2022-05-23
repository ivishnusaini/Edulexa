package com.edulexa.activity.student.dashboard.model

class DashboardModuleModel(name:String,iconLink : String) {
    var name = ""
    var iconLink = ""
    init {
        this.name = name
        this.iconLink = iconLink
    }
    @JvmName("getName1")
    fun getName(): String {
        return name
    }

    @JvmName("setName1")
    fun setName(name: String) {
        this.name = name
    }

    @JvmName("getIconLink1")
    fun getIconLink(): String {
        return iconLink
    }

    @JvmName("setIconLink1")
    fun setIconLink(iconLink: String) {
        this.iconLink = iconLink
    }
}