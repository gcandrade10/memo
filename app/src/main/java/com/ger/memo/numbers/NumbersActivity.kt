package com.ger.memo.numbers

import JetpackComposeDarkThemeTheme
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.ger.memo.CardFace
import com.ger.memo.CompleteDialog
import com.ger.memo.FlipCard
import com.ger.memo.OnClick
import com.ger.memo.R
import com.ger.memo.numbers.data.Item
import com.ger.memo.numbers.data.NumberGameState
import com.ger.memo.star
import com.ger.memo.util.HideSystemBars
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

interface INumbersViewModel : OnClick {
    val gameState: MutableLiveData<NumberGameState>
    fun restart()
    fun play()
}

class NumbersActivity : ComponentActivity() {

    private lateinit var viewModel: INumbersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[NumbersViewmodel::class.java]

        setContent {
            JetpackComposeDarkThemeTheme {
                HideSystemBars()
                NumbersLayout(viewModel) {
                    MediaPlayer.create(this@NumbersActivity, R.raw.correct).start()
                }
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

@Preview(
    showSystemUi = true,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
@Composable
fun NumbersLayoutPreview() {
    val viewmodel = object : INumbersViewModel {
        val list = (1..12).toList().mapIndexed { index, i ->
            Item(index, i)
        }
        override val gameState = MutableLiveData(NumberGameState(list))


        override fun restart() {
        }

        override fun play() {
        }

        override fun onclick(index: Int) {

        }
    }
    NumbersLayout(viewmodel) {

    }
}

@Preview
@Composable
fun NumbersLayPreview() {
    NumbersLayout()
}

@Composable
fun NumbersLayout() {
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    Surface {
        Column {
            Text(
                text = if (orientation == Configuration.ORIENTATION_PORTRAIT)
                    "Portrait" else "Landscape",
                textAlign = TextAlign.Center
            )
        }
    }

}


@Composable
fun NumbersLayout(viewModel: INumbersViewModel, playSound: () -> Unit) {
    val gameStateState = viewModel.gameState.observeAsState()
    val gameState = gameStateState.value!!

    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        val interactionSource = remember {
            MutableInteractionSource()
        }

        NumbersGameBoardMinimal(viewModel, interactionSource, gameState)
    }
    if (gameState.gameOver) {
        playSound()
        KonfettiView(
            modifier = Modifier.fillMaxSize(),
            parties = parade()
        )
        CompleteDialog(3.0f, {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            ) {
                star(3.0f)
            }
        }, {
            viewModel.restart()
        }) {
            viewModel.play()
        }
    }
}

@Composable
fun NumberItem(
    interactionSource: MutableInteractionSource,
    viewmodel: INumbersViewModel,
    index: Int,
    item: Item
) {
    val front = item.discovering
    FlipCard(if (front) CardFace.Front else CardFace.Back,
        back = {
            Box(modifier = Modifier
                .padding(8.dp)
                .size(237.dp)
                .testTag("Back$index")
//                .clip(CircleShape)
//                .background(Color(0xFFFF7F50))

                .drawBehind {
//                    drawCircle(Color(0xFFFF7F50), radius = 99.0f)
                    drawCircle(Color(0xFFFF926A), radius = 99.0f)
                }

                .clickable(
                    interactionSource = interactionSource, indication = null
                ) {
                    viewmodel.onclick(index)
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
                .testTag("Front$index")
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

@Preview
@Composable
fun NumbersGameBoardMinimalPreview() {
    val viewmodel = object : INumbersViewModel {
        val list = (1..12).toList().mapIndexed { index, i ->
            Item(index, i)
        }
        override val gameState = MutableLiveData(NumberGameState(list))


        override fun restart() {
        }

        override fun play() {
        }

        override fun onclick(index: Int) {

        }
    }
    NumbersGameBoardMinimal(
        viewmodel,
        remember { MutableInteractionSource() },
        viewmodel.gameState.value!!
    )
}

@Composable
fun NumbersGameBoardMinimal(
    viewmodel: INumbersViewModel,
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
                            viewmodel = viewmodel
                        )
                    }
                }

//                val matrix: Array<Array<Image?>> = Array(10) {
//                    Array(10)
//                }
            }
        }

        else -> {
            Text("Sorry only landscape supported")
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