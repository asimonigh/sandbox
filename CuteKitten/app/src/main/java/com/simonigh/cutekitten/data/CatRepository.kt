package com.simonigh.cutekitten.data

import com.simonigh.cutekitten.common.Resource
import com.simonigh.cutekitten.data.model.Cat
import com.simonigh.cutekitten.data.remote.CatsApi
import com.simonigh.cutekitten.data.remote.dto.toCat
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class CatRepository(
    private val catsApi: CatsApi
) {
    
    fun getCats(count: Int = 10, page: Int): Single<Resource<List<Cat>>> {
        return catsApi.getCats(count, page)
            .subscribeOn(Schedulers.io())
            .map<Resource<List<Cat>>> { catsList ->
                Resource.Success(catsList.map { catDto -> catDto.toCat() })
            }.onErrorReturn {
                Resource.Error(it.message ?: "can't get cat list form server")
            }
    }
}