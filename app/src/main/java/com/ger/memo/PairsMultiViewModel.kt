package com.ger.memo

import android.app.Application
import android.media.MediaPlayer
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class PairsMultiViewModel(val app: Application) : AndroidViewModel(app), OnClick {

    lateinit var timer: CountDownTimer
    val time = MutableLiveData(0)

    val gameState = MutableLiveData(PairGameStateMulti(null, 0, false, 0, emptyList(), BooleanArray(1),
        false))

    private val correct = MediaPlayer.create(app, R.raw.correct)

    init {
        startGame()
    }

    private fun startGame() {
//        val topScore = getTopScore()
        gameState.value = PairGameStateMulti(gameState.value!!.playerCount, 0, false, 0, emptyList(), BooleanArray(1),false)
        initList()
        time.value = 0
        timer = getTimer(600500L)
        timer.start()
    }

    private fun initList() {
        val copy = Animals.get(app).toCollection(mutableListOf()).shuffled()
        val animals = copy.take(12)
        val list = animals.shuffled() + animals.shuffled()
        val imageList = list.mapIndexed { index, i ->
            Image(index, i)
        }
        gameState.value = gameState.value!!.copy(list = imageList)
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
        gameState.value = gameState.value!!.copy(gameOver = false, gameTime = null)
        startGame()
    }

    fun pause() {
        timer.cancel()
    }

    fun saveScore() {

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

    private fun replacedList(state: PairGameStateMulti, index: Int, copy: Image): List<Image> {
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

    private fun clean(state: PairGameStateMulti): PairGameStateMulti {
        val discovered = state.first!!.drawable == state.second!!.drawable
        val cleanList = cleanDiscoveryFromList(state.list, discovered)

        return state.copy(
            list = cleanList,
            first = null,
            second = null,
            gameOver = !isGameAlive(cleanList),
            gameTime = time.value,
            tryCount = state.tryCount + 1
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

    private fun fillFirst(state: PairGameStateMulti, index: Int) {
        val animal = state.list[index]
        val copy = animal.copy(discovering = true)
        val newList = replacedList(state, index, copy)
        gameState.value = state.copy(list = newList, first = copy)
    }

    private fun updateScore(newState: PairGameStateMulti): PairGameStateMulti {
        return when (newState.turn) {
            0 -> newState.copy(p1Score = newState.p1Score + 1)
            1 -> newState.copy(p2Score = newState.p2Score + 1)
            2 -> newState.copy(p3Score = newState.p3Score + 1)
            else -> newState.copy(p4Score = newState.p4Score + 1)
        }
    }

    private fun fillSecond(state: PairGameStateMulti, index: Int) {
        val animal = state.list[index]
        val copy = animal.copy(discovering = true)
        val newList = replacedList(state, index, copy)
        val newState = state.copy(list = newList, second = copy)
        if (state.first!!.drawable == copy.drawable) {
            correct.start()
            val updatedScore = updateScore(newState)
            gameState.value = clean(updatedScore)
        } else {
            gameState.value =
                state.copy(
                    list = newList,
                    second = copy,
                    tryCount = state.tryCount + 1,
                    turn = nextTurn(state.turn, state.playerCount!!)
                )
        }
    }

    private fun nextTurn(index: Int, playerCount: Int): Int {
        return (index + 1) % playerCount
    }


}

data class PairGameStateMulti(
    val playerCount: Int?,
    val turn: Int = 0,
    override val gameOver: Boolean,
    override val gameTime: Int?,
    override val list: List<Image>,
    override val tappedList: BooleanArray,
    override val showExitDialog: Boolean,
    override val tryCount: Int = 0,
    override val luckyGuesses: Int = 0,
    override val repetitions: Int = 0,
    override val first: Image? = null,
    override val second: Image? = null,
    val p1Score: Int = 0,
    val p2Score: Int = 0,
    val p3Score: Int = 0,
    val p4Score: Int = 0,
) : IPairGameState(gameOver, gameTime, list, tappedList,  showExitDialog, tryCount, luckyGuesses, repetitions, 12, first, second)

data class ImageMulti(
    val index: Int,
    val drawable: Int,
    val discovered: Boolean = false,
    val discovering: Boolean = false
)