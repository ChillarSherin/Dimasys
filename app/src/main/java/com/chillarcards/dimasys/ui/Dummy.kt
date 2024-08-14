package com.chillarcards.dimasys.ui

data class Dummy(val name: String, val id: Long, val custname: String)
data class DummyStaff(val id: Int,val name: String, val total: String )
data class StaffService(val id: Int, val name: String, val total: String, var isSelected: Boolean = false)

data class Staff(val id: Int, val name: String) {
    override fun toString(): String {
        return name
    }
}
