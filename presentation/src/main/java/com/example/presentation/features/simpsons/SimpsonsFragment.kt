package com.example.presentation.features.simpsons

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.domain.simpsons.Doc
import com.example.presentation.R
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentSimpsonsBinding
import com.example.presentation.extensions.getError
import com.example.presentation.extensions.hideProgress
import com.example.presentation.extensions.showError
import com.example.presentation.extensions.showErrorApi
import com.example.presentation.extensions.showProgress
import com.example.presentation.extensions.toJson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint

class SimpsonsFragment : BaseFragment<FragmentSimpsonsBinding>(R.layout.fragment_simpsons) {

    private val adapter = SimpsonsAdapter { clickOnCharacter(it) }
    private val simpsonViewModel: SimpsonViewModel by viewModels()

    override fun setUpUi() {
        binding.recycler.adapter = adapter
        binding.swipRefresh.setOnRefreshListener {
            binding.swipRefresh.isRefreshing = false
            simpsonViewModel.refreshCharactersPagingSource()
        }
        getCharacters()
        listenerAdapter()
    }

    private fun clickOnCharacter(character: Doc) {
        findNavController().navigate(
            SimpsonsFragmentDirections.actionSimpsonsFragmentToCharacterDetailFragment(
                character.toJson()
            )
        )
    }

    private fun getCharacters() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                simpsonViewModel.getCharactersPagingSource.collectLatest { characters ->
                    adapter.submitData(lifecycle, characters)
                }
            }
        }
    }

    private fun listenerAdapter() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.addLoadStateListener { loadState ->
                    if (loadState.source.append is LoadState.Loading || loadState.source.refresh is LoadState.Loading) {
                        showProgress()
                    } else {
                        hideProgress()
                    }
                    val errorState = loadState.getError()
                    errorState?.showError {
                        showErrorApi()
                    }
                }
            }
        }
    }

}