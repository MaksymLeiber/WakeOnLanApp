package com.banny.wakeonlanapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.banny.shared.sendWakeOnLan
import com.banny.shared.pingHost
import com.banny.wakeonlanapp.ui.theme.WakeOnLanAppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

data class Device(
    val name: String,
    val mac: String,
    val ip: String,
    val port: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WakeOnLanAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WakeOnLanScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun WakeOnLanScreen(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var mac by remember { mutableStateOf("") }
    var ip by remember { mutableStateOf("") }
    var port by remember { mutableStateOf("9") }
    var devices by remember { mutableStateOf(listOf<Device>()) }
    var statuses by remember { mutableStateOf(mapOf<String, Boolean>()) }

    // Автообновление статусов
    LaunchedEffect(devices) {
        while (true) {
            val newStatuses = devices.associate { it.ip to pingHost(it.ip) }
            statuses = newStatuses
            delay(3000) // обновлять каждые 3 секунды
        }
    }

    Column(modifier = modifier.padding(16.dp)) {
        Text("Добавить устройство", modifier = Modifier)
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Название устройства") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = mac,
            onValueChange = { mac = it },
            label = { Text("MAC-адрес (AA:BB:CC:DD:EE:FF)") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = ip,
            onValueChange = { ip = it },
            label = { Text("IP-адрес") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = port,
            onValueChange = { port = it.filter { c -> c.isDigit() } },
            label = { Text("Порт") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            val portInt = port.toIntOrNull() ?: 9
            if (name.isNotBlank() && mac.isNotBlank() && ip.isNotBlank()) {
                devices = devices + Device(name, mac, ip, portInt)
                name = ""
                mac = ""
                ip = ""
                port = "9"
            }
        }) {
            Text("Сохранить")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text("Устройства:", modifier = Modifier)
        devices.forEach { device: Device ->
            val status = statuses[device.ip] ?: false
            DeviceRow(device, status)
        }
    }
}

@Composable
fun DeviceRow(device: Device, isOnline: Boolean) {
    Column(Modifier.padding(vertical = 8.dp)) {
        Text("${device.name} (${device.ip})")
        Text(if (isOnline) "Статус: Включен" else "Статус: Выключен")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WakeOnLanAppTheme {
        Greeting("Android")
    }
}