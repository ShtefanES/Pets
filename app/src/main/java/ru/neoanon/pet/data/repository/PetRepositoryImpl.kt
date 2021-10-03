package ru.neoanon.pet.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.neoanon.pet.domain.entity.Pet
import ru.neoanon.pet.domain.repository.PetRepository

class PetRepositoryImpl : PetRepository {

	override suspend fun get(): Flow<List<Pet>> =
		flow { emit(generatePets()) }.flowOn(Dispatchers.IO)

	private suspend fun generatePets(): List<Pet> {
		delay(3000)
		return listOf(
			Pet("qwe"),
			Pet("sdgf"),
			Pet("ggfg"),
			Pet("jj"),
			Pet("qq"),
			Pet("ppp"),
		)
	}
}