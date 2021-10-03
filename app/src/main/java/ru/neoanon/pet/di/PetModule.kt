package ru.neoanon.pet.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.neoanon.pet.data.repository.PetRepositoryImpl
import ru.neoanon.pet.domain.repository.PetRepository
import ru.neoanon.pet.presentation.PetsViewModel

val petModule = module {
	single<PetRepository> { PetRepositoryImpl() }
	viewModel { PetsViewModel(get()) }
}