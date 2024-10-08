package com.ger.memo

import android.content.res.Configuration
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.touchlane.gridpad.GridPad
import com.touchlane.gridpad.GridPadCells
import com.touchlane.gridpad.GridPadPlacementPolicy
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Spread
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

class NumbersActivity : ComponentActivity() {

    val list = generateRandomList()

    private val gameStateG = MutableLiveData(NumberGameState(list))

    private fun generateRandomList(): List<Item> {
        return (1..12).toList().shuffled().mapIndexed { index, i ->
            Item(index, i)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val gameStateState = gameStateG.observeAsState()
            val gameState = gameStateState.value!!

            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {

                val interactionSource = remember {
                    MutableInteractionSource()
                }
                val onClick = object : OnClick {
                    override fun onclick(index: Int) {
                        val state = gameStateG.value!!
                        when {
                            state.list[index].discovering -> Unit
                            isValid(state) -> flip(state, index)
                            else -> reset(state, index)
                        }
//                        flip(state,index)
                    }
                }
                NumbersGameBoardMinimal(onClick, interactionSource, gameState)
            }
            if (gameState.gameOver) {
                MediaPlayer.create(this@NumbersActivity , R.raw.correct).start()
                KonfettiView(
                    modifier = Modifier.fillMaxSize(),
                    parties = parade()
                )
                CompleteDialog(3.0f, {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp)) {
                        star(3.0f)
                    }
                }, {
                    gameStateG.value = gameStateG.value!!.copy(gameOver = false)
                }) {
                    gameStateG.value = gameStateG.value!!.copy(generateRandomList(), false)
                }
            }
        }
    }

    fun parade(): List<Party> {
        val party = Party(
            speed = 10f,
            maxSpeed = 30f,
            damping = 0.9f,
            angle = Angle.RIGHT - 45,
            spread = Spread.SMALL,
            colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
            emitter = Emitter(duration = 5, TimeUnit.SECONDS).perSecond(30),
            position = Position.Relative(0.0, 0.5)
        )

        return listOf(
            party,
            party.copy(
                angle = party.angle - 90, // flip angle from right to left
                position = Position.Relative(1.0, 0.5)
            ),
        )
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

    private fun flip(state: NumberGameState, index: Int) {
        val oldItem = state.list[index]
        val copy = oldItem.copy(discovering = true)
        val newList = replacedList(state, index, copy)
        val isGameOver = newList.fold(true) { acc, item -> acc && item.discovering }
        gameStateG.value = state.copy(list = newList, gameOver = isGameOver)
    }

    private fun reset(state: NumberGameState, index: Int) {
        val newList = resetedList(state, index)
        gameStateG.value = state.copy(list = newList)
    }

    private fun resetedList(state: NumberGameState, index: Int): List<Item> {
        val newList = mutableListOf<Item>()
        state.list.mapIndexed { i, image ->
            newList.add(i, image.copy(discovering = false))
        }
        return newList
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

}

data class NumberGameState(
    val list: List<Item>,
    val gameOver: Boolean = false
)

data class Item(
    val index: Int,
    val number: Int,
    val discovering: Boolean = false,
)


@Composable
fun NumberItem(
    interactionSource: MutableInteractionSource,
    onClick: OnClick,
    index: Int,
    item: Item
) {
    val front = item.discovering
    FlipCard(if (front) CardFace.Front else CardFace.Back,
        back = {
            Box(modifier = Modifier
                .padding(8.dp)
                .size(237.dp)
//                .clip(CircleShape)
//                .background(Color(0xFFFF7F50))

                .drawBehind {
//                    drawCircle(Color(0xFFFF7F50), radius = 99.0f)
                    drawCircle(Color(0xFFFF926A), radius = 99.0f)
                }

                .clickable(
                    interactionSource = interactionSource, indication = null
                ) {
                    onClick.onclick(index)
                }
            )
//            Image(painter = painterResource(id = R.drawable.kiwi_bird),
//                contentDescription = null,
//                modifier = Modifier
//                    .clickable(
//                        interactionSource = interactionSource, indication = null
//                    ) {
//                        viewModel.onclick(index)
//                    }
//                    .size(237.dp))
//                    .aspectRatio(1f, matchHeightConstraintsFirst = true))
        },
        front = {

            Box(modifier = Modifier
                .padding(8.dp)
                .size(237.dp)
                .drawBehind {
                    drawCircle(Color(0xFF21B6A8), radius = 99.0f)
                }
                .clickable(
                    interactionSource = interactionSource, indication = null
                ) {
//                    viewModel.onclick(index)
                }
            ) {
                Text(text = "${item.number}", modifier = Modifier.align(Alignment.Center))
            }
        })
}

@Composable
fun NumbersGameBoardMinimal(
    onClick: OnClick,
//    gameState: IPairGameState,
    interactionSource: MutableInteractionSource,
    state: NumberGameState,
    modifier: Modifier = Modifier
) {

    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            GridPad(
                cells = GridPadCells(rowCount = 3, columnCount = 4),
                modifier = modifier.padding(horizontal = 92.dp),
                placementPolicy = GridPadPlacementPolicy(
                    horizontalDirection = GridPadPlacementPolicy.HorizontalDirection.START_END,
                    verticalDirection = GridPadPlacementPolicy.VerticalDirection.BOTTOM_TOP
                )
//        placementPolicy = GridPadPlacementPolicy(mainAxis = GridPadPlacementPolicy.MainAxis.VERTICAL)

            ) {
                state.list.forEachIndexed { index, item ->
                    item {
                        NumberItem(
                            interactionSource = interactionSource,
                            index = index,
                            item = item,
                            onClick = onClick
                        )
                    }
                }

//                val matrix: Array<Array<Image?>> = Array(10) {
//                    Array(10)
//                }
            }
        }
    }
    Party(
        emitter = Emitter(duration = 5, TimeUnit.SECONDS).perSecond(30)
    )
}

//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun NumbersGameBoard(viewModel: PairsMultiViewModel, gameState: PairGameStateMulti) {
//    CompositionLocalProvider(
//        LocalOverscrollConfiguration provides null
//    ) {
//        val interactionSource = remember {
//            MutableInteractionSource()
//        }
//        Box(Modifier.background(Color.LightGray.copy(alpha = 0.20f))) {
//            NumbersGameBoardMinimal(
//                viewModel,
//                gameState,
//                interactionSource,
//                Modifier.align(Alignment.Center)
//            )
//
//            Count(
//                Modifier
//                    .align(Alignment.BottomStart)
//                    .padding(start = 16.dp), gameState.p1Score,
//                gameState.turn == 0,
//                "Player 1",
//                Modifier
//                    .align(Alignment.TopCenter)
//                    .padding(bottom = 24.dp),
//                Modifier
//                    .align(Alignment.BottomStart)
//                    .padding(start = 8.dp)
//
//            )
//            Count(
//                Modifier
//                    .align(Alignment.TopEnd)
//                    .padding(end = 16.dp),
//                gameState.p2Score,
//                gameState.turn == 1,
//                "Player 2",
//                Modifier
//                    .align(Alignment.BottomCenter)
//                    .padding(top = 24.dp),
//                Modifier
//                    .align(Alignment.TopEnd)
//                    .padding(end = 8.dp)
//            )
//            if ((gameState.playerCount ?: 0) > 2) {
//                Count(
//                    Modifier
//                        .align(Alignment.BottomEnd)
//                        .padding(end = 16.dp),
//                    gameState.p3Score,
//                    gameState.turn == 2,
//                    "Player 3",
//                    Modifier
//                        .align(Alignment.TopCenter)
//                        .padding(bottom = 24.dp),
//                    Modifier
//                        .align(Alignment.BottomEnd)
//                        .padding(end = 8.dp)
//                )
//            }
//
//            if ((gameState.playerCount ?: 0) > 3) {
//                Count(
//                    Modifier
//                        .align(Alignment.TopStart)
//                        .padding(start = 16.dp),
//                    gameState.p4Score,
//                    gameState.turn == 3,
//                    "Player 4",
//                    Modifier
//                        .align(Alignment.BottomCenter)
//                        .padding(top = 24.dp),
//                    Modifier
//                        .align(Alignment.TopStart)
//                        .padding(start = 8.dp)
//                )
//            }
//        }
//
//    }
//}