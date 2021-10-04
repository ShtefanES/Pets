package ru.neoanon.pet.component.error

import java.lang.RuntimeException

interface ErrorConverter {

	fun convert(error: Throwable): String
}

class ErrorConverterImpl(private val errorMessageRepository: ErrorMessageRepository) : ErrorConverter {

	override fun convert(error: Throwable): String {
		val errorCode = when (error) {
			is NetworkError            -> ErrorCode.NETWORK
			is ServiceUnavailableError -> ErrorCode.SERVICE_UNAVAILABLE
			else                       -> ErrorCode.UNKNOWN
		}

		return errorMessageRepository.getString(errorCode)
	}
}

class NetworkError : RuntimeException()
class ServiceUnavailableError : RuntimeException()

enum class ErrorCode {
	NETWORK,
	SERVICE_UNAVAILABLE,
	UNKNOWN
}

