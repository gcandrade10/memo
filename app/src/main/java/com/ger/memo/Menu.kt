package com.ger.memo

import JetpackComposeDarkThemeTheme
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
//                        item {
//                            menuButton("Levels") {
//                                startActivity(Intent(this@Menu, Levels::class.java))
//                            }
//                        }
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
fun menuButton(text: String, modifier: Modifier = Modifier.fillMaxWidth(), onClick: () -> Unit, ) {
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = text, color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
fun menuHButton(icon: ImageVector, text: String, enabled:Boolean = true, onClick: () -> Unit) {
    IconButton(
        enabled = enabled,
        modifier = Modifier.width(100.dp)
            .background(MaterialTheme.colorScheme.primary, CircleShape),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        ),
        onClick = onClick
    ) {
        Icon(icon, contentDescription = text)
    }
}