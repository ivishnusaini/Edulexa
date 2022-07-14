package com.edulexa.activity.student.transport.model

class TransportModel {
    var createdAt = ""
    var fare = ""
    var id = ""
    var isActive = ""
    var note = ""
    var noOfVehicle = ""
    var routeTitle = ""
    var shiftId = ""
    var updatedAt = ""
    var vehicleList : List<VehicleListModel>? = null

    fun getCreateAt() : String{
        return createdAt
    }
    fun setCreateAt(createdAt : String){
        this.createdAt = createdAt
    }
    @JvmName("getFare1")
    fun getFare() : String{
        return fare
    }
    @JvmName("setFare1")
    fun setFare(fare : String){
        this.fare = fare
    }

    @JvmName("getid1")
    fun getId() : String{
        return id
    }
    @JvmName("setid1")
    fun setId(id : String){
        this.id = id
    }

    @JvmName("getIsActive1")
    fun getIsActive() : String{
        return isActive
    }
    @JvmName("setIsActive1")
    fun setIsActive(isActive : String){
        this.isActive = isActive
    }

    @JvmName("getnote1")
    fun getNote() : String{
        return note
    }
    @JvmName("setnote1")
    fun setNote(note : String){
        this.note = note
    }

    @JvmName("getNoOfVehicle1")
    fun getNoOfVehicle() : String{
        return noOfVehicle
    }
    @JvmName("setNoOfVehicle1")
    fun setNoOfVehicle(noOfVehicle : String){
        this.noOfVehicle = noOfVehicle
    }

    @JvmName("getrouteTitle1")
    fun getRouteTitle() : String{
        return routeTitle
    }
    @JvmName("setrouteTitle1")
    fun setRouteTitle(routeTitle : String){
        this.routeTitle = routeTitle
    }

    @JvmName("getshiftId1")
    fun getShiftId() : String{
        return shiftId
    }
    @JvmName("setshiftId1")
    fun setShiftId(shiftId : String){
        this.shiftId = shiftId
    }

    @JvmName("getupdatedAt1")
    fun getUpdateAt() : String{
        return updatedAt
    }
    @JvmName("setupdatedAt1")
    fun setUpdateAt(updatedAt : String){
        this.updatedAt = updatedAt
    }

    @JvmName("getvehicleList1")
    fun getVehicleList() : List<VehicleListModel>?{
        return vehicleList
    }
    @JvmName("setvehicleList1")
    fun setVehicleList(vehicleList : List<VehicleListModel>){
        this.vehicleList = vehicleList
    }
}