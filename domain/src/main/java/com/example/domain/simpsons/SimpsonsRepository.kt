package com.example.domain.simpsons


interface SimpsonsRepository {
    suspend fun getCharacters(page: String): TheSimpsonCharacterResponse
}