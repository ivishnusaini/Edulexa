package com.edulexa.activity.student.hostel.model.hostel_detail

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class DatumHostelDetail {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("hostel_id")
    @Expose
    private var hostelId: String? = null

    @SerializedName("room_type_id")
    @Expose
    private var roomTypeId: String? = null

    @SerializedName("room_no")
    @Expose
    private var roomNo: String? = null

    @SerializedName("no_of_bed")
    @Expose
    private var noOfBed: String? = null

    @SerializedName("cost_per_bed")
    @Expose
    private var costPerBed: String? = null

    @SerializedName("title")
    @Expose
    private var title: Any? = null

    @SerializedName("description")
    @Expose
    private var description: String? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null

    @SerializedName("room_type")
    @Expose
    private var roomType: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getHostelId(): String? {
        return hostelId
    }

    fun setHostelId(hostelId: String?) {
        this.hostelId = hostelId
    }

    fun getRoomTypeId(): String? {
        return roomTypeId
    }

    fun setRoomTypeId(roomTypeId: String?) {
        this.roomTypeId = roomTypeId
    }

    fun getRoomNo(): String? {
        if (roomNo == null)
            return ""
        return roomNo
    }

    fun setRoomNo(roomNo: String?) {
        this.roomNo = roomNo
    }

    fun getNoOfBed(): String? {
        return noOfBed
    }

    fun setNoOfBed(noOfBed: String?) {
        this.noOfBed = noOfBed
    }

    fun getCostPerBed(): String? {
        if (costPerBed == null)
            return ""
        return costPerBed
    }

    fun setCostPerBed(costPerBed: String?) {
        this.costPerBed = costPerBed
    }

    fun getTitle(): Any? {
        return title
    }

    fun setTitle(title: Any?) {
        this.title = title
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getCreatedAt(): String? {
        return createdAt
    }

    fun setCreatedAt(createdAt: String?) {
        this.createdAt = createdAt
    }

    fun getUpdatedAt(): String? {
        return updatedAt
    }

    fun setUpdatedAt(updatedAt: String?) {
        this.updatedAt = updatedAt
    }

    fun getRoomType(): String? {
        if (roomType == null)
            return ""
        return roomType
    }

    fun setRoomType(roomType: String?) {
        this.roomType = roomType
    }
}