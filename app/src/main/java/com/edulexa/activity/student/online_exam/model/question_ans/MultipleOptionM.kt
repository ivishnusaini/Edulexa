package com.edulexa.activity.student.online_exam.model.question_ans

class MultipleOptionM(option : String,select : Boolean,position : Int) {
    var option = ""
    var select = false
    var position = -1
    init {
        this.option = option
        this.select = select
        this.position = position
    }

    @JvmName("getOption1")
    fun getOption(): String? {
        return option
    }

    @JvmName("setOption1")
    fun setOption(option: String?) {
        this.option = option!!
    }

    fun isSelect(): Boolean {
        return select
    }

    @JvmName("setSelect1")
    fun setSelect(select: Boolean) {
        this.select = select
    }

    @JvmName("getPosition1")
    fun getPosition(): Int {
        return position
    }

    @JvmName("setPosition1")
    fun setPosition(position: Int) {
        this.position = position
    }
}