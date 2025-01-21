package com.ger.memo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun PairReplayDialog(
    gameOver: Boolean,
    time: Int?,
    tryCount: Int? = null,
    luckyCount: Int? = null,
    repetitions: Int? = null,
    confirm: () -> Unit,
    dismiss: () -> Unit
) {
    if (gameOver) {
        AlertDialog(
            onDismissRequest = { confirm.invoke() },
            text = {
                Column(Modifier.fillMaxWidth()) {
                    Text("You won!")
                    Text("Time: $time seconds", Modifier.fillMaxWidth())
                    tryCount?.let {
                        Text("$tryCount tries", Modifier.fillMaxWidth())
                    }
                    luckyCount?.let {
                        Text("$luckyCount lucky guesses", Modifier.fillMaxWidth())
                    }
                    repetitions?.let {
                        Text("$repetitions repetitions", Modifier.fillMaxWidth())
                    }
                }
            },
            confirmButton = {
                menuButton(text = stringResource(R.string.back)) {
                    confirm.invoke()
                }
            }, dismissButton = {
                menuButton(text = stringResource(R.string.play)) {
                    dismiss.invoke()
                }
            }
        )
    }
}
