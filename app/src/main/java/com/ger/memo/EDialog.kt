package com.ger.memo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

private val DialogPadding = PaddingValues(all = 24.dp)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun EDialog(
    text: @Composable () -> Unit,
    confirm: () -> Unit,
    restart: () -> Unit,
    dismissButton: @Composable () -> Unit,
) = BasicAlertDialog(
    onDismissRequest = { },
    modifier = Modifier,
    properties = DialogProperties()
) {
    Surface(
        shape = AlertDialogDefaults.shape,
        color = AlertDialogDefaults.containerColor,
        tonalElevation = AlertDialogDefaults.TonalElevation,
    ) {
        Column(
            modifier = Modifier.padding(DialogPadding)
        ) {
            Row(Modifier.padding(bottom = 16.dp)) {
                text()
            }
            Row {
                menuButton(text = "Exit") {
                    confirm.invoke()
                }
            }
            Row {
                menuButton(text = "Restart") {
                    restart.invoke()
                }
            }
            Row {
                dismissButton()
            }
        }
    }
}