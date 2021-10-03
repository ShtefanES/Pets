package ru.neoanon.pet.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.neoanon.pet.domain.entity.Pet

interface PetRepository {

	suspend fun get(): Flow<List<Pet>>
}