package com.ger.memo

import JetpackComposeDarkThemeTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.ger.memo.viewmodel.Image
import com.ger.memo.viewmodel.PairGameState
import com.ger.memo.viewmodel.PairsViewModel
import kotlin.math.ceil

class PairsH : ComponentActivity() {

    private lateinit var viewModel: PairsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[PairsViewModel::class.java]

        setContent {
            val gameStateState = viewModel.gameState.observeAsState()
            val gameState = gameStateState.value!!
            JetpackComposeDarkThemeTheme {
                BackPressHandler(onBackPressed = {
                    viewModel.setShowExitDialog(true)
                })
                if (gameState.gameOver) {
                    val score = measureScore(gameState)
                    CompleteDialog(score, {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp)
                        ) {
                            star(score)
//                                Text("Time: $gameTime seconds", Modifier.fillMaxWidth())
//                                Text("$tryCount tries", Modifier.fillMaxWidth())
//                                Text("$luckyGuesses lucky guesses", Modifier.fillMaxWidth())
//                                Text("$repetitions repetitions", Modifier.fillMaxWidth())
                        }
                    }, {
                        viewModel.restart()
                    }) {
                        viewModel.replay()
                    }
                }

                if (gameState.showExitDialog) {
                    viewModel.pause()
                    EDialog({
                        Text(
                            stringResource(R.string.do_you_want_to_discard_the_current_game),
                            Modifier.fillMaxWidth(),
                            color = AlertDialogDefaults.textContentColor,
                        )
                    }, {
                        viewModel.saveScore()
                        finish()
                    }, {
                        viewModel.setShowExitDialog(false)
                        viewModel.restart()
                    }) {
                        viewModel.setShowExitDialog(false)
                        viewModel.resume()
                    }
                }
                PairGameBoard(viewModel, gameState)
            }
        }
    }

    private fun measureScore(pairGameState: PairGameState): Float {
        val total = pairGameState.size * 2
        return (3.0 * (total - pairGameState.repetitions) / total).roundUpToMultipleOf(0.5)
            .toFloat()
    }


    @Composable
    fun FlipItem(
        image: Image,
        index: Int,
        interactionSource: MutableInteractionSource,
        cardFace: CardFace
    ) {
        val rotation = animateFloatAsState(
            targetValue = cardFace.angle,
            animationSpec = tween(
                durationMillis = 200,
                easing = FastOutSlowInEasing,
            ), label = ""
        )
        Box(
            modifier = Modifier.graphicsLayer {
                rotationY = rotation.value
                cameraDistance = 12f * density
            },
        ) {
            if (image.discovering || image.discovered) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .graphicsLayer {
//                            rotationY = 180f
                        },
                ) {
                    Image(painter = painterResource(id = image.drawable),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                viewModel.onclick(index)
                            }
                            .aspectRatio(1f, matchHeightConstraintsFirst = true)
                    )
                }
            } else {
                Image(painter = painterResource(id = R.drawable.a122),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            viewModel.onclick(index)
                        }
                        .aspectRatio(1f, matchHeightConstraintsFirst = true)
                )
            }
        }
    }


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun PairGameBoard(viewModel: PairsViewModel, gameState: PairGameState) {
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            val interactionSource = MutableInteractionSource()
            PairGameBoardMinimal(viewModel, gameState, interactionSource)
            Text("repetitions: ${gameState.repetitions}", color = Color.White)
        }
    }

    @Preview
    @Composable
    fun PreviewPairGameBoard() {
        PairGameBoard(
            PairsViewModel(application),
            PairGameState(false, 0, emptyList(), BooleanArray(1), false)
        )
    }
}

fun Double.roundUpToMultipleOf(base: Double): Double = base * ceil(this / base)

