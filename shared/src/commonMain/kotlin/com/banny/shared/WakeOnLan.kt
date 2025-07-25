package com.banny.shared

expect fun sendWakeOnLan(macAddress: String, ipAddress: String, port: Int = 9): Boolean 