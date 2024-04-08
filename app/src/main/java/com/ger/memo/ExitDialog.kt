package com.ger.memo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

@Composable
fun ExitDialog(
    confirm: () -> Unit,
    dismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        text = {
            Column(Modifier.fillMaxWidth()) {
                Text("Do you want to discard the current game?", Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            menuButton(text = "Exit") {
                confirm.invoke()
            }
        }, dismissButton = {
            menuButton(text = "Cancel") {
                dismiss.invoke()
            }
        }
    )

}