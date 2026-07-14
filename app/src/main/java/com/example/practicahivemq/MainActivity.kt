package com.example.practicahivemq

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.MqttGlobalPublishFilter
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
import kotlin.text.Charsets.UTF_8
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                MqttScreen()
            }
        }
    }
}

@Composable
fun MqttScreen() {
    var conexion by remember { mutableStateOf("Desconectado") }
    var publicacion by remember { mutableStateOf("") }
    var recibido by remember { mutableStateOf("") }

    var client by remember { mutableStateOf<Mqtt5AsyncClient?>(null) }

    val host = "6bded9a4815f44d6968bd35b65cf6e3b.s1.eu.hivemq.cloud"
    val port = 8883
    val username = "PaoGG"
    val password = "Pao_01"

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val mqttClient = MqttClient.builder()
                .useMqttVersion5()
                .serverHost(host)
                .serverPort(port)
                .sslWithDefaultConfig()
                .buildAsync()

            mqttClient.connectWith()
                .simpleAuth()
                .username(username)
                .password(UTF_8.encode(password))
                .applySimpleAuth()
                .send()
                .whenComplete { ack, ex ->
                    if (ex == null) {
                        conexion = "Conectado: ${ack.reasonCode}"
                        client = mqttClient

                        mqttClient.subscribeWith()
                            .topicFilter("valor-analogico")
                            .send()

                        mqttClient.publishes(MqttGlobalPublishFilter.ALL) { publish ->
                            val topic = publish.topic.toString()
                            val valor = String(publish.payloadAsBytes, Charsets.UTF_8)
                            recibido = "$topic = $valor"
                        }
                    } else {
                        conexion = "Error: ${ex.message}"
                    }
                }
        }
    }

    Column(modifier = Modifier.padding(30.dp)) {
        Text("Conexión: $conexion")
        Spacer(Modifier.height(20.dp))

        Button(onClick = {
            client?.publishWith()
                ?.topic("control-led")
                ?.payload(UTF_8.encode("1"))
                ?.send()
            publicacion = "Publicado en control-led (encender)"
        }) {
            Text("Encender LED")
        }

        Spacer(Modifier.height(10.dp))

        Button(onClick = {
            client?.publishWith()
                ?.topic("control-led")
                ?.payload(UTF_8.encode("0"))
                ?.send()
            publicacion = "Publicado en control-led (apagar)"
        }) {
            Text("Apagar LED")
        }

        Spacer(Modifier.height(20.dp))

        Button(onClick = {
            publicacion = "Último valor recibido: $recibido"
        }) {
            Text("Mostrar valor recibido")
        }

        Spacer(Modifier.height(20.dp))
        Text("Publicación: $publicacion")
        Spacer(Modifier.height(20.dp))
        Text("Recibido: $recibido")
    }
}