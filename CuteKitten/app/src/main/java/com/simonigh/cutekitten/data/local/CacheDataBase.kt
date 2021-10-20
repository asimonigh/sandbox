package com.simonigh.cutekitten.data.local

import android.app.Application
import androidx.room.*

@Database(entities = [CatEntity::class], version = 1)
abstract class CacheDataBase: RoomDatabase() {
    
    abstract val catDao: CatDao
    
    companion object {
        fun build(application: Application): CacheDataBase {
            return Room.databaseBuilder(application, CacheDataBase::class.java, "room_db").build()
        }
    }
}