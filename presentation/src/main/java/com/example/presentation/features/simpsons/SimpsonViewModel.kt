package com.example.presentation.features.simpsons

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.di.CoroutineDispatchers
import com.example.data.getPagingConfig
import com.example.data.simpsons.CharactersPagingSource
import com.example.data.simpsons.SimpsonsApi
import com.example.domain.simpsons.Doc
import com.example.domain.simpsons.SimpsonsRepository
import com.example.presentation.base.BaseViewModel
import com.example.presentation.helpers.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SimpsonViewModel @Inject constructor(
    private val repository: SimpsonsRepository,
    networkHelper: NetworkHelper,
    coroutineDispatcher: CoroutineDispatchers,
    private val rickAndMortyService: SimpsonsApi
) : BaseViewModel(coroutineDispatcher, networkHelper) {


    private lateinit var charactersPagingSource: CharactersPagingSource

    val getCharactersPagingSource: Flow<PagingData<Doc>> =
        Pager(
            config = getPagingConfig(),
            pagingSourceFactory = {
                charactersPagingSource = CharactersPagingSource(service = rickAndMortyService)
                charactersPagingSource
            }
        ).flow.cachedIn(viewModelScope)

    fun refreshCharactersPagingSource() = charactersPagingSource.invalidate()

}