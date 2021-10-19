package com.simonigh.cutekitten.presentation

import androidx.lifecycle.*
import com.simonigh.cutekitten.common.Resource.Error
import com.simonigh.cutekitten.common.Resource.Success
import com.simonigh.cutekitten.data.CatRepository
import com.simonigh.cutekitten.data.model.Cat
import io.reactivex.rxjava3.disposables.CompositeDisposable

class CatListViewModel(
    private val catRepository: CatRepository
) : ViewModel() {
    
    val state : LiveData<CatListState>
        get() = _state
    private val _state = MutableLiveData<CatListState>()
    
    val disposeBag = CompositeDisposable()
    
    var nextPage: Int = 1
    
    init {
        disposeBag.add(
            catRepository.getCats(page = 1, count = PAGE_SIZE * 2)
                .doOnSuccess { result ->
                    when(result) {
                        is Error -> _state.postValue(CatListState(listOf(), result.message))
                        is Success -> _state.postValue(CatListState(catList = result.data))
                    }
                    nextPage++
                }.subscribe()
        )
    }
    
    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }
    
    fun loadMoreCats() {
        disposeBag.add(
            catRepository.getCats(page = nextPage, count = PAGE_SIZE)
                .doOnSuccess { result ->
                    when(result) {
                        is Error -> _state.postValue(CatListState(listOf(), result.message))
                        is Success -> _state.postValue(CatListState(catList = result.data))
                    }
                    nextPage++
                }.subscribe()
        )
    }
    
    companion object {
        const val PAGE_SIZE = 10
    }
}

