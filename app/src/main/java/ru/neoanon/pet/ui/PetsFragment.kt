package ru.neoanon.pet.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.neoanon.pet.databinding.PetsFragmentBinding
import ru.neoanon.pet.presentation.PetsState
import ru.neoanon.pet.presentation.PetsViewModel

class PetsFragment : Fragment() {

	companion object {

		fun newInstance() = PetsFragment()
	}

	private var _binding: PetsFragmentBinding? = null
	private val binding get() = _binding!!

	private val viewModel: PetsViewModel by viewModel()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = PetsFragmentBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		lifecycleScope.launchWhenStarted {
			viewModel.petsState.collect(::renderState)
		}

		viewModel.loadData()
	}

	private fun renderState(state: PetsState) {
		when (state) {
			PetsState.InProgress -> binding.message.text = "loading..."
			is PetsState.Content -> binding.message.text = "${state.pets.joinToString(separator = ",\n") { it.name }}"
			is PetsState.Error   -> binding.message.text = state.message
		}
	}
}