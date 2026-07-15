package com.example.practicahivemq.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.practicahivemq.model.Libro
import com.example.practicahivemq.viewmodel.LibroViewModel

@Composable
fun EditarLibroScreen(
    navController: NavController,
    vm: LibroViewModel,
    id: Int
) {

    val context = LocalContext.current

    val libro = vm.libros.find { it.id == id }

    if (libro == null) {

        Text("Libro no encontrado")
        return

    }

    var titulo by remember { mutableStateOf(libro.titulo) }
    var autor by remember { mutableStateOf(libro.autor) }
    var editorial by remember { mutableStateOf(libro.editorial) }
    var anio by remember { mutableStateOf(libro.anio.toString()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            "Editar Libro",
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

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {

                vm.actualizarLibro(
                    id,
                    Libro(
                        id,
                        titulo,
                        autor,
                        editorial,
                        anio.toInt()
                    )
                )

                Toast.makeText(
                    context,
                    "Libro actualizado correctamente",
                    Toast.LENGTH_SHORT
                ).show()

                navController.popBackStack()

            }
        ) {

            Text("Actualizar")

        }

    }

}