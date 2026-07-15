package com.example.practicahivemq.network

import com.example.practicahivemq.model.Libro
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("libros")
    suspend fun obtenerLibros(): Response<List<Libro>>

    @POST("libros")
    suspend fun agregarLibro(
        @Body libro: Libro
    ): Response<Libro>

    @PUT("libros/{id}")
    suspend fun actualizarLibro(
        @Path("id") id:Int,
        @Body libro: Libro
    ): Response<Libro>

    @DELETE("libros/{id}")
    suspend fun eliminarLibro(
        @Path("id") id:Int
    ): Response<Unit>

}