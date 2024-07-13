package com.ger.memo

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ger.memo.viewmodel.IPairGameState
import com.ger.memo.viewmodel.Image
import com.touchlane.gridpad.GridPad
import com.touchlane.gridpad.GridPadCells
import com.touchlane.gridpad.GridPadPlacementPolicy

@Composable
fun PairGameBoardMinimal(
    viewModel: OnClick,
    gameState: IPairGameState,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            GridPad(
                cells = GridPadCells(rowCount = 4, columnCount = 6),
                modifier = modifier.padding(horizontal = 92.dp),
                placementPolicy = GridPadPlacementPolicy(
                    horizontalDirection = GridPadPlacementPolicy.HorizontalDirection.START_END,
                    verticalDirection = GridPadPlacementPolicy.VerticalDirection.BOTTOM_TOP
                )
//        placementPolicy = GridPadPlacementPolicy(mainAxis = GridPadPlacementPolicy.MainAxis.VERTICAL)

            ) {
                gameState.list.forEachIndexed { index, image ->
                    item {
                        PairItem(image, interactionSource, viewModel, index)
                    }
                }

//                val matrix: Array<Array<Image?>> = Array(10) {
//                    Array(10)
//                }
            }
        }

        else -> {
            GridPad(
                cells = GridPadCells(rowCount = 8, columnCount = 5),
                placementPolicy = GridPadPlacementPolicy(
                    horizontalDirection = GridPadPlacementPolicy.HorizontalDirection.START_END,
                    verticalDirection = GridPadPlacementPolicy.VerticalDirection.BOTTOM_TOP
                )
            ) {
                ListTransformation.fillAndSort(gameState.list).forEachIndexed { _, image ->
                    if (image != null) {
                        item {
                            PairItem(image, interactionSource, viewModel, image.index)
                        }
                    } else {
                        item {
                            Text(text = ".")
                        }
                    }

                }
            }
        }
    }


}

@Composable
fun ConfigChangeExample() {
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Text("Landscape")
        }

        else -> {
            Text("Portrait")
        }
    }
}

@Composable
fun PairItem(
    image: Image,
    interactionSource: MutableInteractionSource,
    viewModel: OnClick,
    index: Int
) {
    FlipCard(if (image.discovering || image.discovered) CardFace.Front else CardFace.Back,
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
                    viewModel.onclick(index)
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
            Image(painter = painterResource(id = image.drawable),
                contentDescription = null,
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource, indication = null
                    ) {
                        viewModel.onclick(index)
                    }
                    .size(237.dp))
//                    .aspectRatio(1f, matchHeightConstraintsFirst = true))
        })
}