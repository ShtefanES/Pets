package ru.neoanon.pet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.neoanon.pet.ui.PetsFragment

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main_activity)
		if (savedInstanceState == null) {
			supportFragmentManager.beginTransaction()
				.replace(R.id.container, PetsFragment.newInstance())
				.commitNow()
		}
	}
}