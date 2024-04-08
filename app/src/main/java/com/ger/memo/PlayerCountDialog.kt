package com.ger.memo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PlayerCountDialog(
    playerCount: Int?,
    viewModel: PairsMultiViewModel,
) {
    if (playerCount == null) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(32.dp)
        ) {
            Row(Modifier.align(Alignment.Center)) {
                ButtonCount(2, viewModel)
                ButtonCount(3, viewModel)
                ButtonCount(4, viewModel)
            }
        }
    }
}

@Composable
fun ButtonCount(i: Int, viewModel: PairsMultiViewModel) {
    Button(onClick = {
        viewModel.gameState.value = viewModel.gameState.value?.copy(playerCount = i)
    }, Modifier.padding(end = 16.dp)) {
        Text(text = "${i}P")
    }
}

