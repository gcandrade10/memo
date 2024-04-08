package com.ger.memo

import JetpackComposeDarkThemeTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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

    private fun getUserColor(index :Int): Color {
        return when(index){
            0-> Color.Blue
            1-> Color.Red
            2-> Color.Black
            else -> Color.Gray
        }.copy(alpha = 0.35f)
    }

    @Composable
    private fun Count(color: Color, modifier: Modifier, score: Int) {
        Text(
            modifier = modifier
                .drawBehind {
                    drawCircle(
                        color = Color.White,
                        radius = this.size.maxDimension
                    )
                },
            fontSize = 32.sp, color = color, fontWeight = FontWeight.Bold,
            text = "$score",
        )
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun PairGameBoard(viewModel: PairsMultiViewModel, gameState: PairGameStateMulti) {
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            val interactionSource = MutableInteractionSource()
            Box(Modifier.background(getUserColor(gameState.turn))) {
                PairGameBoardMinimal(viewModel , gameState , interactionSource, Modifier.align(Alignment.Center) )
                Count(getUserColor(0), Modifier.align(Alignment.BottomStart).padding(start = 16.dp), gameState.p1Score)
                Count(
                    getUserColor(1),
                    Modifier.align(Alignment.TopEnd).padding(end = 16.dp),
                    gameState.p2Score
                )
                if((gameState.playerCount ?: 0) > 2){
                    Count(
                        getUserColor(2),
                        Modifier.align(Alignment.BottomEnd).padding(end = 16.dp),
                        gameState.p3Score
                    )
                }

                if((gameState.playerCount ?: 0) > 3){
                    Count(
                        getUserColor(3),
                        Modifier.align(Alignment.TopStart).padding(start = 16.dp),
                        gameState.p4Score
                    )
                }
            }

        }
    }
}

