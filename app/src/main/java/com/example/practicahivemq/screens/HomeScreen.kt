package com.example.practicahivemq.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.practicahivemq.viewmodel.LibroViewModel


@Composable
fun HomeScreen(
    navController: NavController,
    vm: LibroViewModel
) {

    var libroAEliminar by remember {

        mutableStateOf<Int?>(null)
    }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        vm.obtenerLibros()
    }

    LaunchedEffect(vm.error) {

        if (vm.error.isNotBlank()) {

            Toast.makeText(
                context,
                vm.error,
                Toast.LENGTH_LONG
            ).show()

            vm.limpiarError()

        }

    }

    Scaffold(

        floatingActionButton = {

            FloatingActionButton(

                onClick = {

                    navController.navigate("agregar")


                }

            ) {

                Text("+")

            }

        }

    ) { paddingValues ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)

        ) {

            Text(

                text = "Biblioteca",

                style = MaterialTheme.typography.headlineMedium

            )

            Spacer(modifier = Modifier.height(16.dp))

            if (vm.cargando) {

                CircularProgressIndicator()

            } else {

                LazyColumn {

                    items(vm.libros) { libro ->

                        Card(

                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)

                        ) {

                            Column(

                                modifier = Modifier.padding(16.dp)

                            ) {

                                Text(
                                    text = libro.titulo,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text("Autor: ${libro.autor}")

                                Text("Editorial: ${libro.editorial}")

                                Text("Año: ${libro.anio}")

                            }


                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Button(
                                    onClick = {
                                        navController.navigate("editar/${libro.id}")
                                    }
                                ) {
                                    Text("Editar")
                                }

                                Button(
                                    onClick = {
                                        libroAEliminar = libro.id
                                    }
                                ) {
                                    Text("Eliminar")

                                }

                            }


                        }

                        if (libroAEliminar != null) {

                            AlertDialog(

                                onDismissRequest = {
                                    libroAEliminar = null
                                },

                                title = {
                                    Text("Confirmación")
                                },

                                text = {
                                    Text("¿Deseas eliminar este libro?")
                                },

                                confirmButton = {

                                    Button(

                                        onClick = {

                                            vm.eliminarLibro(libroAEliminar!!)

                                            Toast.makeText(
                                                context,
                                                "Libro eliminado correctamente",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            libroAEliminar = null

                                        }

                                    ) {

                                        Text("Eliminar")

                                    }

                                },

                                dismissButton = {

                                    Button(

                                        onClick = {

                                            libroAEliminar = null

                                        }

                                    ) {

                                        Text("Cancelar")

                                    }

                                }

                            )

                        }

                    }

                }

            }

        }

    }

}