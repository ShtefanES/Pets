package ru.neoanon.pet.presentation

import ru.neoanon.pet.domain.entity.Pet

sealed class PetsState {

	object InProgress : PetsState()

	data class Content(val pets: List<Pet>) : PetsState()

	data class Error(val message: String) : PetsState()
}