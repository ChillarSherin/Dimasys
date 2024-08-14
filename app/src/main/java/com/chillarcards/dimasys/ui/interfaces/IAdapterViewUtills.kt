package com.chillarcards.dimasys.ui.interfaces

import com.chillarcards.dimasys.utills.CommonDBaseModel

interface IAdapterViewUtills {

    fun getAdapterPosition(Position: Int, ValueArray: ArrayList<CommonDBaseModel>, Mode: String?)
}