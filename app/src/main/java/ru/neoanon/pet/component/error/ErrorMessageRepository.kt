package ru.neoanon.pet.component.error

import android.content.Context
import ru.neoanon.pet.R

interface ErrorMessageRepository {

	fun getString(code: ErrorCode): String
}

class ErrorMessageRepositoryImpl(private val context: Context) : ErrorMessageRepository {

	override fun getString(code: ErrorCode): String {
		val resId = when (code) {
			ErrorCode.NETWORK             -> R.string.network_error_message
			ErrorCode.SERVICE_UNAVAILABLE -> R.string.service_unavailable_error_message
			ErrorCode.UNKNOWN             -> R.string.unknown_error_message
		}

		return context.getString(resId)
	}
}