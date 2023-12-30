package com.example.data.simpsons

import com.example.domain.simpsons.SimpsonsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SimpsonsRepositoryImpl @Inject constructor(private val simpsonsApi: SimpsonsApi) :
    SimpsonsRepository {
    override suspend fun getCharacters(page: String) = simpsonsApi.getCharacters(page)

}