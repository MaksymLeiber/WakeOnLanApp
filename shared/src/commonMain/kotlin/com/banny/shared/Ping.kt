package com.banny.shared

expect fun pingHost(ipAddress: String, timeoutMs: Int = 1000): Boolean 