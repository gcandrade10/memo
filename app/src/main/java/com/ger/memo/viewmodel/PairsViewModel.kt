package com.ger.memo.viewmodel

import android.app.Application
import android.media.MediaPlayer
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ger.memo.Animals
import com.ger.memo.OnClick
import com.ger.memo.R

class PairsViewModel(val app: Application) : AndroidViewModel(app), OnClick {

    lateinit var timer: CountDownTimer
    val time = MutableLiveData(0)

    val gameState = MutableLiveData(PairGameState(false, 0, emptyList(), BooleanArray(1), false))

    private val correct = MediaPlayer.create(app, R.raw.correct)

    init {
        startGame()
    }

    private fun startGame(size :Int = 1) {
//        val topScore = getTopScore()
        initList(size)
        time.value = 0
        timer = getTimer(600500L)
        timer.start()
    }

    private fun reStartGame(size: Int = 1) {
        val imageList = cleanList(gameState.value!!.list)
        gameState.value = gameState.value!!.copy(
            list = imageList,
            tappedList = BooleanArray(imageList.size),
            size = size
        )
        time.value = 0
        timer = getTimer(600500L)
        timer.start()
    }

    private fun cleanList(list: List<Image>): List<Image> {
        return list.map {
            it.copy(discovering = false, discovered = false)
        }
    }

    private fun initList(i: Int) {
        val copy = Animals.get(app).toCollection(mutableListOf()).shuffled()
        val animals = copy.take(i)
        val list = animals.shuffled() + animals.shuffled()
        val imageList = list.mapIndexed { index, i ->
            Image(index, i)
        }
        gameState.value = gameState.value!!.copy(list = imageList, tappedList = BooleanArray(imageList.size), size = i)
    }

    private fun getTimer(start: Long): CountDownTimer {
        return object : CountDownTimer(start, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                time.value = ((start - millisUntilFinished) / 1000).toInt()
            }

            override fun onFinish() {
//                saveScore()
            }
        }
    }

    fun setShowExitDialog(showExitDialog: Boolean) {
        gameState.value = gameState.value!!.copy(showExitDialog = showExitDialog)
    }

    fun replay() {
        timer.cancel()
        gameState.value = gameState.value!!.copy(gameOver = false, gameTime = null, tryCount = 0, luckyGuesses = 0, repetitions = 0)
        startGame(gameState.value!!.size +1)
    }

    fun pause() {
        timer.cancel()
    }

    fun saveScore() {

    }

    fun restart() {
        timer.cancel()
        gameState.value = gameState.value!!.copy(gameOver = false, gameTime = null, tryCount = 0, luckyGuesses = 0, repetitions = 0)
        reStartGame(gameState.value!!.size)
    }

    fun resume() {
        val start: Long = ((time.value!! + 1) * 1000).toLong()
        timer = getTimer(start)
        timer.start()
    }

    override fun onclick(index: Int) {
        val state = gameState.value!!
        when {
            state.list[index].discovered -> Unit
            state.first == null -> fillFirst(state, index)
            state.first.index == index -> Unit// Do nothing, not allowed
            state.second == null -> fillSecond(state, index)
            else -> {
                val cleanState = clean(state)
                fillFirst(cleanState, index)
            }
        }
    }

    private fun replacedList(state: PairGameState, index: Int, copy: Image): List<Image> {
        val newList = mutableListOf<Image>()
        state.list.mapIndexed { i, image ->
            if (i == index) {
                newList.add(i, copy)
            } else {
                newList.add(i, image)
            }
        }
        return newList
    }

    private fun isGameAlive(list: List<Image>): Boolean {
        return list.any { !it.discovered }
    }

    private fun clean(state: PairGameState): PairGameState {
        val discovered = state.first!!.drawable == state.second!!.drawable
        val cleanList = cleanDiscoveryFromList(state.list, discovered)
        var luckyGuesses = state.luckyGuesses
        if (!state.tappedList[state.second.index]) {
            luckyGuesses += 1
        }

        val newArray = state.tappedList
        newArray[state.second.index] = true

        val isGameOver = !isGameAlive(cleanList)

        return state.copy(
            list = if (isGameOver) cleanList(state.list) else cleanList,
            tappedList = newArray,
            first = null,
            second = null,
            gameOver = isGameOver,
            gameTime = time.value,
            tryCount = state.tryCount + 1,
            luckyGuesses = luckyGuesses
        )
    }

    private fun cleanDiscoveryFromList(list: List<Image>, discovered: Boolean): List<Image> {
        return list.map {
            if (it.discovering) {
                it.copy(discovering = false, discovered = discovered)
            } else {
                it
            }
        }
    }

    private fun fillFirst(state: PairGameState, index: Int) {
        val animal = state.list[index]
        val copy = animal.copy(discovering = true)
        val newList = replacedList(state, index, copy)

        gameState.value = state.copy(
            list = newList,
            first = copy,
        )
    }

    private fun fillSecond(state: PairGameState, index: Int) {
        val animal = state.list[index]
        val copy = animal.copy(discovering = true)
        val newList = replacedList(state, index, copy)
        val newState = state.copy(list = newList, second = copy)
        if (state.first!!.drawable == copy.drawable) {
            correct.start()
            gameState.value = clean(newState)
        } else {
            val firstIndex = state.first.index
            val repetitions = state.repetitions + getRepetitions(state.tappedList, firstIndex, index)
            val newArray = getUpdatedArray(state.tappedList, firstIndex, index)

            gameState.value = state.copy(list = newList, second = copy, tryCount = state.tryCount + 1, tappedList = newArray, repetitions = repetitions)
        }
    }

    private fun getRepetitions(tappedList: BooleanArray, firstIndex: Int, secondIndex: Int): Int {
        val first = if (tappedList[firstIndex]) 1 else 0
        val second = if (tappedList[secondIndex]) 1 else 0
        return first + second
    }

    private fun getUpdatedArray(tappedList: BooleanArray, firstIndex: Int, secondIndex: Int): BooleanArray {
        tappedList[firstIndex] = true
        tappedList[secondIndex] = true
        return tappedList
    }

}

open class IPairGameState(
    open val gameOver: Boolean,
    open val gameTime: Int?,
    open val list: List<Image>,
    open val tappedList: BooleanArray,
    open val showExitDialog: Boolean,
    open val tryCount : Int = 0,
    open val luckyGuesses : Int = 0,
    open val repetitions : Int = 0,
    open val size : Int = 5,
    open val first: Image? = null,
    open val second: Image? = null
)

data class PairGameState(
    override val gameOver: Boolean,
    override val gameTime: Int?,
    override val list: List<Image>,
    override val tappedList: BooleanArray,
    override val showExitDialog: Boolean,
    override val tryCount : Int = 0,
    override val luckyGuesses : Int = 0,
    override val repetitions : Int = 0,
    override val size : Int = 5,
    override val first: Image? = null,
    override val second: Image? = null
): IPairGameState(gameOver, gameTime, list, tappedList, showExitDialog, tryCount, luckyGuesses, repetitions, size, first, second)

data class Image(
    val index: Int,
    val drawable: Int,
    val discovered: Boolean = false,
    val discovering: Boolean = false,
)
