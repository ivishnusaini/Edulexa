package com.edulexa.activity.student.dashboard.model

class DashboardModuleModel(name:String,iconLink : String,shortCode : String) {
    var name = ""
    var shortCode = ""
    var iconLink = ""
    init {
        this.name = name
        this.iconLink = iconLink
        this.shortCode = shortCode
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

    @JvmName("setShortCode1")
    fun setShortCode(shortCode: String) {
        this.shortCode = shortCode
    }


    @JvmName("getShortCode1")
    fun getShortCode(): String {
        return shortCode
    }

    @JvmName("setIconLink1")
    fun setIconLink(iconLink: String) {
        this.iconLink = iconLink
    }
}