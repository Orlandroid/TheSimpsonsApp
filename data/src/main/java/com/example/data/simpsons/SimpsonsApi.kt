package com.example.data.simpsons


import com.example.domain.simpsons.TheSimpsonCharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface SimpsonsApi {

    @GET("personajes")
    suspend fun getCharacters(
        @Query("page") page: String,
        @Query("limit") limit: String = "20",
    ): TheSimpsonCharacterResponse
}