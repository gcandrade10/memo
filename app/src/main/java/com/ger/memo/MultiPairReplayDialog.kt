package com.ger.memo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MultiPairReplayDialog(
    gameOver: Boolean,
    winner: String?,
    playerCount: Int?,
    p1HistoricScore: Int,
    p2HistoricScore: Int,
    p3HistoricScore: Int,
    p4HistoricScore: Int,
    confirm: () -> Unit,
    dismiss: () -> Unit
) {
    if (gameOver) {
        AlertDialog(
            onDismissRequest = { confirm.invoke() },
            text = {
                Column(Modifier.fillMaxWidth()) {
                    Text(text = "$winner wins")
                    Text(text = "Player 1: $p1HistoricScore")
                    Text(text = "Player 2: $p2HistoricScore")
                    if(playerCount!! > 2){
                        Text(text = "Player 3: $p3HistoricScore")
                    }
                    if(playerCount> 3){
                        Text(text = "Player 4: $p4HistoricScore")
                    }
                }
            },
            confirmButton = {
                menuButton(text = "Back") {
                    confirm.invoke()
                }
            }, dismissButton = {
                menuButton(text = "Play") {
                    dismiss.invoke()
                }
            }
        )
    }
}
