package com.simonigh.cutekitten.presentation

import com.simonigh.cutekitten.data.model.Cat

data class CatListState(
    val catList: List<Cat>,
    val error: String? = null
)
