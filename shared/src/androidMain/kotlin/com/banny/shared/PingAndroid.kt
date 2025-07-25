package com.banny.shared

import java.net.InetAddress

actual fun pingHost(ipAddress: String, timeoutMs: Int): Boolean {
    return try {
        InetAddress.getByName(ipAddress).isReachable(timeoutMs)
    } catch (e: Exception) {
        false
    }
} 