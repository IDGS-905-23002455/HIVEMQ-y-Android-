package com.example.practicahivemq.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicahivemq.model.Libro
import com.example.practicahivemq.repository.LibroRepository
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import retrofit2.HttpException
import com.google.gson.JsonSyntaxException
import androidx.compose.runtime.*

class LibroViewModel : ViewModel() {

    private val repository = LibroRepository()

    var libros by mutableStateOf<List<Libro>>(emptyList())
        private set

    var cargando by mutableStateOf(false)
        private set

    var error by mutableStateOf("")
        private set

    fun obtenerLibros() {

        viewModelScope.launch {

            cargando = true

            try {

                val response = repository.obtenerLibros()

                if (response.isSuccessful) {
                    libros = response.body() ?: emptyList()
                } else {
                    error = "Error: ${response.code()}"
                }

            } catch (e: UnknownHostException) {

                error = "No hay conexión a Internet."

            }
            catch (e: ConnectException) {

                error = "El servidor Flask no responde."

            }
            catch (e: SocketTimeoutException) {

                error = "Tiempo de espera agotado."

            }
            catch (e: HttpException) {

                error = "Error interno del servidor."

            }
            catch (e: JsonSyntaxException) {

                error = "Error al convertir los datos JSON."

            }
            catch (e: Exception) {

                error = "Ocurrió un error inesperado."

            } finally {

                cargando = false

            }

        }

    }

    fun agregarLibro(libro: Libro){

        viewModelScope.launch{

            repository.agregarLibro(libro)

            obtenerLibros()

        }

    }

    fun actualizarLibro(id:Int, libro:Libro){

        viewModelScope.launch{

            repository.actualizarLibro(id,libro)

            obtenerLibros()

        }

    }

    fun eliminarLibro(id:Int){

        viewModelScope.launch{

            repository.eliminarLibro(id)

            obtenerLibros()

        }

    }
    fun limpiarError() {
        error = ""
    }

}