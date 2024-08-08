package com.ger.memo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ger.memo.viewmodel.PairsMultiViewModel

@Composable
fun PlayerCountDialog(
    playerCount: Int?,
    viewModel: PairsMultiViewModel,
) {
    if (playerCount == null) {
        val setNames = remember {
            mutableStateOf(false)
        }
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(32.dp)
        ) {
            if (!setNames.value) {
                Row(Modifier.align(Alignment.Center)) {
                    ButtonCount(2, viewModel)
                    ButtonCount(3, viewModel)
                    ButtonCount(4, viewModel)
                }
                // TODO this screen must be vertical to enable this
//                FloatingActionButton(
//                    onClick = {
//                        setNames.value = true
//                    }, modifier = Modifier
//                        .align(Alignment.BottomEnd)
//                        .padding(24.dp)
//                ) {
//                    Icon(Icons.Filled.Settings, "toggle sound", modifier = Modifier.size(24.dp))
//                }
            } else {
                Column(Modifier.align(Alignment.Center)) {
                    UserInput("Player 1")
                    UserInput("Player 2")
                    UserInput("Player 3")
                    UserInput("Player 4")
                }
            }

        }
    }
}

@Composable
fun UserInput(label: String) {
    var text by remember { mutableStateOf(label) }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(label) }
    )
}

@Composable
fun ButtonCount(i: Int, viewModel: PairsMultiViewModel) {
    Button(onClick = {
        viewModel.startGame(i)
        viewModel.gameState.value = viewModel.gameState.value?.copy(playerCount = i)
    }, Modifier.padding(end = 16.dp)) {
        Text(text = "${i}P")
    }
}

