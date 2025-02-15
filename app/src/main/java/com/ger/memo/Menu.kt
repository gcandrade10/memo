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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.ger.memo.numbers.NumbersActivity
import com.ger.memo.viewmodel.MenuViewModel

class Menu : ComponentActivity() {

    private lateinit var viewModel: MenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MenuViewModel::class.java]
        setContent {

            val isSoundEnabled = viewModel.isSoundEnabled.collectAsState(initial = false)

            JetpackComposeDarkThemeTheme {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                    LazyColumn(
                        verticalArrangement = Arrangement.Center, modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(start = 32.dp, end = 32.dp, bottom = 50.dp)
                            .align(Alignment.Center)
                    ) {
                        if (BuildConfig.DEBUG) {
                            item {
                                val configuration = LocalConfiguration.current

                                val screenWidthPx = configuration.screenWidthDp
                                val screenHeightPx = configuration.screenHeightDp
                                Column {
                                    Text("PairItem: density: ${LocalDensity.current.density}")
                                    Text("$screenHeightPx x $screenWidthPx")
                                }
                            }
                        }
                        item {
                            menuButton(stringResource(R.string.play)) {
                                startActivity(Intent(this@Menu, PairsH::class.java))
                            }
                        }
                        item {
                            menuButton(stringResource(R.string.play_numbers)) {
                                startActivity(Intent(this@Menu, NumbersActivity::class.java))
                            }
                        }
//                        item {
//                            menuButton("Levels") {
//                                startActivity(Intent(this@Menu, Levels::class.java))
//                            }
//                        }
                        item {
                            menuButton(stringResource(R.string.multiplayer)) {
                                startActivity(Intent(this@Menu, PairsMulti::class.java))
                            }
                        }
                    }


                    FloatingActionButton(
                        onClick = {
                            viewModel.toggleSound(!isSoundEnabled.value)
                        }, modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(24.dp)
                    ) {
                        val icon =
                            if (isSoundEnabled.value) R.drawable.sound else R.drawable.sound_off
                        Icon(painterResource(icon), "toggle sound", modifier = Modifier.size(24.dp))
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
fun menuButton(text: String, modifier: Modifier = Modifier.fillMaxWidth(), onClick: () -> Unit) {
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
fun menuHButton(icon: ImageVector, text: String, enabled: Boolean = true, onClick: () -> Unit) {
    IconButton(
        enabled = enabled,
        modifier = Modifier
            .width(100.dp)
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