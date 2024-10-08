package com.ger.memo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
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
fun CompleteDialog(
    score: Float,
    text: @Composable () -> Unit,
    restart: () -> Unit,
    play: () -> Unit,
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

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Column {
                    menuHButton(Icons.Filled.Refresh, text = "Restart") {
                        restart.invoke()
                    }
                }
                Column {
                    menuHButton(Icons.Filled.PlayArrow, text = "Play", enabled = score > 1.99) {
                        play.invoke()
                    }
                }
            }
        }
    }
}