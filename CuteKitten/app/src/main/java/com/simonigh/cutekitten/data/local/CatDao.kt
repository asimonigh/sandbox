package com.simonigh.cutekitten.data.local

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface CatDao {
    
    @Query("SELECT * FROM cat_table")
    fun getSavedCats(): Single<List<CatEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCat(catEntity: CatEntity): Completable
    
    @Query("DELETE FROM cat_table WHERE id = :id" )
    fun removeCat(id: String): Completable
}