package ru.neoanon.pet.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.neoanon.pet.component.error.ErrorConverter
import ru.neoanon.pet.component.error.ErrorConverterImpl
import ru.neoanon.pet.component.error.ErrorMessageRepository
import ru.neoanon.pet.component.error.ErrorMessageRepositoryImpl
import ru.neoanon.pet.data.repository.PetRepositoryImpl
import ru.neoanon.pet.domain.repository.PetRepository
import ru.neoanon.pet.presentation.PetsViewModel

val petModule = module {
	single<PetRepository> { PetRepositoryImpl() }
	single<ErrorMessageRepository> { ErrorMessageRepositoryImpl(get()) }
	single<ErrorConverter> { ErrorConverterImpl(get()) }
	viewModel { PetsViewModel(get(), get()) }
}