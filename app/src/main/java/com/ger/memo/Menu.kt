package com.ger.memo

import JetpackComposeDarkThemeTheme
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class Menu : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetpackComposeDarkThemeTheme {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                    LazyColumn(
                        verticalArrangement = Arrangement.Center, modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(start = 32.dp, end = 32.dp, bottom = 50.dp)
                            .align(Alignment.Center)
                    ) {
                        item {
                            menuButton("Play") {
                                startActivity(Intent(this@Menu, PairsH::class.java))
                            }
                        }
                        item {
                            menuButton("Multiplayer") {
                                startActivity(Intent(this@Menu, PairsMulti::class.java))
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun menuSpacer() {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )
    }
}

@Composable
fun menuButton(text: String, onClick: () -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text, color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}