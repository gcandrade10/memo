package com.ger.memo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun ExitDialog(
    confirm: () -> Unit,
    dismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            dismiss.invoke()
        },
        text = {
            Column(Modifier.fillMaxWidth()) {
                Text(
                    stringResource(R.string.do_you_want_to_discard_the_current_game),
                    Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            menuButton(text = stringResource(R.string.exit)) {
                confirm.invoke()
            }
        }, dismissButton = {
            menuButton(text = stringResource(R.string.cancel)) {
                dismiss.invoke()
            }
        }
    )

}