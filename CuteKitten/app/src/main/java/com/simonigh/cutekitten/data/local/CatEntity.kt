package com.simonigh.cutekitten.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_table")
data class CatEntity(
    @PrimaryKey val id: String,
    val picUrl: String
    )