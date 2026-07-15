package com.example.practicahivemq.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.practicahivemq.model.Libro
import com.example.practicahivemq.viewmodel.LibroViewModel

@Composable
fun AgregarLibroScreen(
    navController: NavController,
    vm: LibroViewModel = viewModel()
) {

    var titulo by remember { mutableStateOf("") }
    var autor by remember { mutableStateOf("") }
    var editorial by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") }
    var mensajeError by remember {
        mutableStateOf("")}
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            "Agregar Libro",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = autor,
            onValueChange = { autor = it },
            label = { Text("Autor") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = editorial,
            onValueChange = { editorial = it },
            label = { Text("Editorial") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = anio,
            onValueChange = { anio = it },
            label = { Text("Año") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(25.dp))

        if (mensajeError.isNotEmpty()) {

            Text(
                text = mensajeError,
                color = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(10.dp))

        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {

                when {

                    titulo.isBlank() -> {
                        mensajeError = "Ingrese el título."
                    }

                    autor.isBlank() -> {
                        mensajeError = "Ingrese el autor."
                    }

                    editorial.isBlank() -> {
                        mensajeError = "Ingrese la editorial."
                    }

                    anio.isBlank() -> {
                        mensajeError = "Ingrese el año."
                    }

                    anio.toIntOrNull() == null -> {
                        mensajeError = "El año debe ser numérico."
                    }

                    else -> {

                        mensajeError = ""

                        vm.agregarLibro(

                            Libro(

                                id = 0,
                                titulo = titulo,
                                autor = autor,
                                editorial = editorial,
                                anio = anio.toInt()

                            )

                        )
                        Toast.makeText(
                            context,
                            "Libro agregado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()

                        navController.popBackStack()

                    }

                }

            }
        ) {

            Text("Guardar")

        }

    }

}