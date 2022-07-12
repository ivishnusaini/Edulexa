package com.edulexa.activity.student.download_center.model


class DownloadCenterModel {
    var type = ""
    var selectedFlag = false
    var typeWiseList : List<DownloadDetailModel>? = null

    @JvmName("getType1")
    fun getType() : String{
        return type
    }

    @JvmName("setType1")
    fun setType(type: String){
        this.type = type
    }

    @JvmName("getTypeWiseList1")
    fun getTypeWiseList() : List<DownloadDetailModel>?{
        return typeWiseList
    }

    @JvmName("setTypeWiseList1")
    fun setTypeWiseList(dayWiseList: List<DownloadDetailModel>){
        this.typeWiseList = dayWiseList
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