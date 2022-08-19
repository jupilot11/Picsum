package com.joeydee.picsum.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.joeydee.picsum.model.Person
import com.joeydee.picsum.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
   var repository: HomeRepository,
) : ViewModel() {


    fun result(): Flow<List<Person>> = repository.getResults()

    fun fetchImages(): Flow<PagingData<Person>> {
        return repository.getData().cachedIn(viewModelScope)
    }
}