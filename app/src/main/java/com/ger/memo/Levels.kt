package com.ger.memo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.touchlane.gridpad.GridPad
import com.touchlane.gridpad.GridPadCells
import com.touchlane.gridpad.GridPadPlacementPolicy

class Levels : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            levels()
        }
    }

    @Composable
    fun levels() {
        GridPad(
            cells = GridPadCells(rowCount = 8, columnCount = 5),
            placementPolicy = GridPadPlacementPolicy(
                horizontalDirection = GridPadPlacementPolicy.HorizontalDirection.START_END,
                verticalDirection = GridPadPlacementPolicy.VerticalDirection.TOP_BOTTOM
            )
        ) {
            (1..40).toList().forEach {
                item {
                    Box(modifier = Modifier
                        .padding(8.dp)
                        .size(237.dp)

                        .drawBehind {
//                    drawCircle(Color(0xFFFF7F50), radius = 99.0f)
                            drawCircle(Color(0xFFFF926A), radius = 99.0f)
                        }

                        .clickable(
//                            interactionSource = interactionSource, indication = null
                        ) {
                            startActivity(Intent(this@Levels, PairsH::class.java))

                        }
                    ) {
                        Text(text = "$it", modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }
}