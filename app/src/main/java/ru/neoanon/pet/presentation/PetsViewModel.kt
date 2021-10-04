package ru.neoanon.pet.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.neoanon.pet.component.error.ErrorConverter
import ru.neoanon.pet.domain.repository.PetRepository

class PetsViewModel(
	private val petRepository: PetRepository,
	private val errorConverter: ErrorConverter,
) : ViewModel() {

	private val _petsState = MutableStateFlow<PetsState>(PetsState.InProgress)
	val petsState: StateFlow<PetsState> = _petsState.asStateFlow()

	fun loadData() {
		_petsState.value = PetsState.InProgress

		viewModelScope.launch {
			petRepository.get()
				.catch { error ->
					handleLoadDataError(error)
				}
				.collect { pets ->
					_petsState.value = PetsState.Content(pets)
				}
		}
	}

	private fun handleLoadDataError(throwable: Throwable) {
		val errorMessage = errorConverter.convert(throwable)
		_petsState.value = PetsState.Error(errorMessage)
	}
}