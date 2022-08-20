package com.runew.fasthome4

data class DeviceList(
    val data: List<Devices>
)
data class Devices(
    val id: String,
    val name: String,
    val icon: String,
    val isOnline: String,
    val type: String,
    val status: String,
    val lastWorkTime: String
)