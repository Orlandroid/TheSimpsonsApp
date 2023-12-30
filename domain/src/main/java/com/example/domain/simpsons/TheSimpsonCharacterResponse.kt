package com.example.domain.simpsons

data class TheSimpsonCharacterResponse(
    val docs: List<Doc>,
    val hasNextPage: Boolean,
    val hasPrevPage: Boolean,
    val limit: Int,
    val nextPage: Int,
    val page: Int,
    val pagingCounter: Int,
    val prevPage: Any,
    val totalDocs: Int,
    val totalPages: Int
)

data class Doc(
    val Estado: String,
    val Genero: String,
    val Historia: String,
    val Imagen: String,
    val Nombre: String,
    val Ocupacion: String,
    val _id: String
)