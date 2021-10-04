package ru.neoanon.pet.presentation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ru.neoanon.pet.component.error.ErrorConverter
import ru.neoanon.pet.component.test.TestCoroutineRule
import ru.neoanon.pet.domain.entity.Pet
import ru.neoanon.pet.domain.repository.PetRepository

class PetsViewModelTest {

	@get:Rule
	val testCoroutineRule = TestCoroutineRule()

	private val repository: PetRepository = mock()
	private val errorConverter: ErrorConverter = mock()

	private val viewModel: PetsViewModel = PetsViewModel(repository, errorConverter)

	@ExperimentalCoroutinesApi
	@Test
	fun `load data EXPECT repository invoke`() = runBlockingTest {
		val pets = listOf(Pet("qwerty"), Pet("ytrewq"), Pet("qasw"))
		val petsFlow = flow { emit(pets) }
		whenever(repository.get()).thenReturn(petsFlow)

		viewModel.loadData()

		verify(repository).get()
	}

	@ExperimentalCoroutinesApi
	@Test
	fun `load data EXPECT state changed correct`() = runBlockingTest {
		val pets = listOf(Pet("qwerty"), Pet("ytrewq"), Pet("qasw"))
		val petsFlow = flow { emit(pets) }
		whenever(repository.get()).thenReturn(petsFlow)
		val results = mutableListOf<PetsState>()
		val job = launch {
			viewModel.petsState.toList(results)
		}

		viewModel.loadData()

		assertEquals(PetsState.InProgress, results[0])
		assertEquals(PetsState.Content(pets), results[1])
		job.cancel()
	}

	@ExperimentalCoroutinesApi
	@Test
	fun `load data  with error EXPECT state changed correct`() = runBlockingTest {
		val exception = Exception()
		val expectedErrorMessage = "ops error"
		val petsFlow = flow<List<Pet>> { throw  exception }
		whenever(repository.get()).thenReturn(petsFlow)
		whenever(errorConverter.convert(exception)).thenReturn(expectedErrorMessage)
		val results = mutableListOf<PetsState>()
		val job = launch {
			viewModel.petsState.toList(results)
		}

		viewModel.loadData()

		assertEquals(PetsState.InProgress, results[0])
		assertEquals(PetsState.Error(expectedErrorMessage), results[1])
		job.cancel()
	}
}