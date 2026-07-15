package com.example.practicahivemq.repository

import com.example.practicahivemq.model.Libro
import com.example.practicahivemq.network.RetrofitClient

class LibroRepository {

    suspend fun obtenerLibros() =
        RetrofitClient.api.obtenerLibros()

    suspend fun agregarLibro(libro: Libro)=
        RetrofitClient.api.agregarLibro(libro)

    suspend fun actualizarLibro(id:Int, libro: Libro)=
        RetrofitClient.api.actualizarLibro(id,libro)

    suspend fun eliminarLibro(id:Int)=
        RetrofitClient.api.eliminarLibro(id)

}