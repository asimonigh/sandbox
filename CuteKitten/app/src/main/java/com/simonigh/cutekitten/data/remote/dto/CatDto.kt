package com.simonigh.cutekitten.data.remote.dto

import com.simonigh.cutekitten.data.model.Cat

data class CatDto(
    val categories: List<Category>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)

fun CatDto.toCat(): Cat {
    return Cat(id, url)
}