package ru.neoanon.pet.component.test

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class TestCoroutineRule  : TestRule {

	override fun apply(base: Statement, d: Description): Statement =
		object : Statement() {
			override fun evaluate() {
				Dispatchers.setMain(TestCoroutineDispatcher())

				try {
					base.evaluate()
				} finally {
					Dispatchers.resetMain()
				}
			}
		}
}