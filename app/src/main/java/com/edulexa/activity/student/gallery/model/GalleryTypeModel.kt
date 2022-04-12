package com.edulexa.activity.student.gallery.model

class GalleryTypeModel(typeGalleryType: String) {
    var typeGalleryType: String? = null

    init {
        this.typeGalleryType = typeGalleryType
    }

    fun getGalleryType(): String? {
        return typeGalleryType
    }

    fun setGalleryType(typeGalleryType: String) {
        this.typeGalleryType = typeGalleryType
    }
}
