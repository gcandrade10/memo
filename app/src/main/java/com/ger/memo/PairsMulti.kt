package com.ger.memo

import JetpackComposeDarkThemeTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider

class PairsMulti : ComponentActivity() {

    private lateinit var viewModel: PairsMultiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[PairsMultiViewModel::class.java]

        setContent {
            val gameStateState = viewModel.gameState.observeAsState()
            val gameState = gameStateState.value!!
            JetpackComposeDarkThemeTheme {
                BackPressHandler(onBackPressed = {
                    viewModel.setShowExitDialog(true)
                })
                PairReplayDialog(
                    gameState.gameOver,
                    gameState.gameTime,
                    gameState.tryCount,
                    gameState.luckyGuesses,
                    gameState.repetitions,
                    { finish() }) {
                    viewModel.replay()
                }
                if (gameState.showExitDialog) {
                    viewModel.pause()
                    ExitDialog({
                        viewModel.saveScore()
                        finish()
                    }) {
                        viewModel.setShowExitDialog(false)
                        viewModel.resume()
                    }
                }
                PairGameBoard(viewModel, gameState)
                PlayerCountDialog(gameState.playerCount, viewModel)
            }
        }
    }

//    private fun getUserColor(index :Int): Color {
//        return when(index){
//            0-> Color.Blue
//            1-> Color.Red
//            2-> Color.Green
//            else -> Color.Yellow
//        }.copy(alpha = 0.35f)
//    }

    @Composable
    private fun Count(modifier: Modifier, score: Int, isMyTurn: Boolean, color: Color = Color.Black) {
        val backgroundInfiniteTransition = rememberInfiniteTransition()

        val backgroundBlinking = if (isMyTurn) backgroundInfiniteTransition.animateColor(Color(0xFFFF4400), Color(0xFFFF4400),
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 3000
                    Color(0xFF470000) at 1500
//                    Color(0xFFFF4400) at 1500
                }
            )
        ).value else Color.White

        Box(modifier
            .drawBehind {
                drawCircle(
//                    color = if(isMyTurn) Color(0xFFFF7F50) else Color.White,
                    color = if(isMyTurn) Color(0xFFFF4400) else Color.White,
//                    color = backgroundBlinking,
                    radius = this.size.maxDimension
                )
            }) {

            val infiniteTransition = rememberInfiniteTransition()
            val blinking = if (isMyTurn) infiniteTransition.animateColor(color, color,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 3000
                        Color.White at 1500
                    }
                )
            ).value else color

//            Box(modifier = blinkingModifier
//                .drawBehind {
//                    drawCircle(blinking, radius = 20.0f)
//                })

            Text(
                modifier = Modifier.align(Alignment.Center),
                fontSize = 32.sp, color = blinking, fontWeight = FontWeight.Bold,
                text = "$score",
            )
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun PairGameBoard(viewModel: PairsMultiViewModel, gameState: PairGameStateMulti) {
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            val interactionSource = MutableInteractionSource()
            Box(Modifier.background(Color.LightGray.copy(alpha = 0.20f))) {
                PairGameBoardMinimal(
                    viewModel,
                    gameState,
                    interactionSource,
                    Modifier.align(Alignment.Center)
                )

                Count(
                    Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 16.dp), gameState.p1Score,
                    gameState.turn == 0
                )
                Count(
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 16.dp),
                    gameState.p2Score,
                    gameState.turn == 1
                )
                if ((gameState.playerCount ?: 0) > 2) {
                    Count(
                        Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 16.dp),
                        gameState.p3Score,
                        gameState.turn == 2
                    )
                }

                if ((gameState.playerCount ?: 0) > 3) {
                    Count(
                        Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 16.dp),
                        gameState.p4Score,
                        gameState.turn == 3
                    )
                }
            }

        }
    }
}

