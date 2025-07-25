package com.banny.shared

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

actual fun sendWakeOnLan(macAddress: String, ipAddress: String, port: Int): Boolean {
    return try {
        val macBytes = macAddress.split(":").map { it.toInt(16).toByte() }.toByteArray()
        val bytes = ByteArray(6 + 16 * macBytes.size)
        for (i in 0 until 6) bytes[i] = 0xFF.toByte()
        for (i in 6 until bytes.size step macBytes.size) macBytes.copyInto(bytes, i)
        val address = InetAddress.getByName(ipAddress)
        val packet = DatagramPacket(bytes, bytes.size, address, port)
        DatagramSocket().use { it.send(packet) }
        true
    } catch (e: Exception) {
        false
    }
} 