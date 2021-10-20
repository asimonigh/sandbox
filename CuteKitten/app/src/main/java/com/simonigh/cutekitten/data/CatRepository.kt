package com.simonigh.cutekitten.data

import com.simonigh.cutekitten.common.Result
import com.simonigh.cutekitten.data.local.CatDao
import com.simonigh.cutekitten.data.model.Cat
import com.simonigh.cutekitten.data.remote.CatsApi
import com.simonigh.cutekitten.data.remote.dto.toCat
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class CatRepository(
    private val catsApi: CatsApi,
    private val catDao: CatDao
) {
    
    fun getCats(count: Int = 10, page: Int): Single<Result<List<Cat>>> {
        return catsApi.getCats(count, page)
            .subscribeOn(Schedulers.io())
            .map<Result<List<Cat>>> { catsList ->
                Result.Success(catsList.map { catDto -> catDto.toCat() })
            }.onErrorReturn {
                Result.Error(it.message ?: "can't get cat list form server")
            }
    }
}