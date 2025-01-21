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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ger.memo.viewmodel.IPairGameState
import com.ger.memo.viewmodel.Image
import com.ger.memo.viewmodel.PairGameState
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
            Portrait(gameState, interactionSource, viewModel)

        }
    }
}

@Preview(name = "phone", device = "spec:width=360dp,height=640dp,dpi=300")
@Preview(name = "pixel4", device = "id:pixel_4")
@Preview(name = "Phone", device = "spec:width=411dp,height=891dp,dpi=420")
@Preview(name = "Phone X", device = "spec:width=411dp,height=891dp,dpi=240")
@Preview(name = "Tablet", device = "spec:width=800dp,height=1280dp,dpi=240")
annotation class DevicesPreview

@Composable
@DevicesPreview
fun PreviewPortrait() {

    val interactionSource = remember {
        MutableInteractionSource()
    }

    Portrait(
        PairGameState(
            false,
            0,
            listOf(Image(0, R.drawable.badger), Image(1, R.drawable.badger)),
            BooleanArray(1),
            false
        ),
        interactionSource,
        object : OnClick {
            override fun onclick(index: Int) {
                TODO("Not yet implemented")
            }

        }
    )
}

@Composable
fun Portrait(
    gameState: IPairGameState,
    interactionSource: MutableInteractionSource,
    viewModel: OnClick
) {
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
    val configuration = LocalConfiguration.current
    val list = listOf(configuration.screenWidthDp, configuration.screenHeightDp)


    val radius = if (list.min() < 380) 65.0f else 99.0f
    FlipCard(if (image.discovering || image.discovered) CardFace.Front else CardFace.Back,
        back = {
            Box(modifier = Modifier
                .padding(8.dp)
                .size(237.dp)
//                .clip(CircleShape)
//                .background(Color(0xFFFF7F50))

                .drawBehind {
//                    drawCircle(Color(0xFFFF7F50), radius = 99.0f)
                    drawCircle(Color(0xFFFF926A), radius = radius)
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