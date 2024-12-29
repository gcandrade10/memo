package com.ger.memo.numbers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ger.memo.numbers.data.Item
import com.ger.memo.numbers.data.NumberGameState

class NumbersViewmodel : INumbersViewModel, ViewModel() {
    private val list = generateRandomList()
    override val gameState = MutableLiveData(NumberGameState(list))

    private fun generateRandomList(): List<Item> {
        return (1..12).toList().shuffled().mapIndexed { index, i ->
            Item(index, i)
        }
    }

    private fun isValid(state: NumberGameState): Boolean {
        val list = state.list.filter { it.discovering }.map { it.number }.sorted()
        return when {
            list.isEmpty() -> true
            list.size == 1 && list[0] == 1 -> true
            else -> list.foldIndexed(true) { index, isValid, currentItem ->
                // Check if each item equals its expected value, which is index + 1
                isValid && currentItem == index + 1
            }
        }
    }

    private fun reset(state: NumberGameState, index: Int) {
        val newList = resetedList(state, index)
        gameState.value = state.copy(list = newList)
    }

    private fun resetedList(state: NumberGameState, index: Int): List<Item> {
        val newList = mutableListOf<Item>()
        state.list.mapIndexed { i, image ->
            newList.add(i, image.copy(discovering = false))
        }
        return newList
    }

    private fun flip(state: NumberGameState, index: Int) {
        val oldItem = state.list[index]
        val copy = oldItem.copy(discovering = true)
        val newList = replacedList(state, index, copy)
        val isGameOver = newList.fold(true) { acc, item -> acc && item.discovering }
        gameState.value = state.copy(list = newList, gameOver = isGameOver)
    }

    private fun replacedList(state: NumberGameState, index: Int, copy: Item): List<Item> {
        val newList = mutableListOf<Item>()
        state.list.mapIndexed { i, image ->
            if (i == index) {
                newList.add(i, copy)
            } else {
                newList.add(i, image)
            }
        }
        return newList
    }

    override fun onclick(index: Int) {
        val state = gameState.value!!
        when {
            state.list[index].discovering -> Unit
            isValid(state) -> flip(state, index)
            else -> reset(state, index)
        }
//                        flip(state,index)
    }

    override fun restart() {
        gameState.value = gameState.value!!.copy(gameOver = false)
    }

    override fun play() {
        gameState.value = gameState.value!!.copy(list = generateRandomList(), gameOver = false)
    }
}