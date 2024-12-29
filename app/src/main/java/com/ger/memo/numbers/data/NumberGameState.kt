package com.ger.memo.numbers.data

data class NumberGameState(
    val list: List<Item>,
    val gameOver: Boolean = false
)

data class Item(
    val index: Int,
    val number: Int,
    val discovering: Boolean = false,
)