package com.ger.memo

import JetpackComposeDarkThemeTheme
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.ViewModelProvider
import com.ger.memo.numbers.NumbersActivity
import com.ger.memo.viewmodel.MenuViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Menu : ComponentActivity() {

    private lateinit var viewModel: MenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MenuViewModel::class.java]
        setContent {

            val isSoundEnabled = viewModel.isSoundEnabled.collectAsState(initial = false)

            JetpackComposeDarkThemeTheme {
                MenuContent(
                    getString(R.string.rate_us),
                    getString(R.string.wanna_try_other_games), {
                        launchMarket()
                    },
                    { viewModel.toggleSound(!isSoundEnabled.value) },
                    isSoundEnabled.value,
                    { startActivity(Intent(this@Menu, PairsH::class.java)) },
                    { startActivity(Intent(this@Menu, NumbersActivity::class.java)) },
                    { startActivity(Intent(this@Menu, NumbersActivity::class.java)) }
                ) { intent ->
                    startActivity(intent)

                }
            }
        }
    }
}

@Preview
@Composable
fun MenuContentPreview() {
    MenuContent(
        "Rate us",
        "Wanna try other games?",
        {},
        {},
        true
    )
}

@Composable
fun MenuContent(
    rateUs: String,
    tryOtherGames: String,
    launchMarket: () -> Unit,
    toggleSound: () -> Unit,
    isSoundEnabled: Boolean,
    pairsH: () -> Unit = {},
    numbersActivity: () -> Unit = {},
    pairsMulti: () -> Unit = {},
    startIntent: (Intent) -> Unit = {}
) {

    val showAd = remember {
        mutableStateOf(true)
    }

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
                    pairsH()
                }
            }
            item {
                menuButton(stringResource(R.string.play_numbers)) {
                    numbersActivity()
                }
            }
//                        item {
//                            menuButton("Levels") {
//                                startActivity(Intent(this@Menu, Levels::class.java))
//                            }
//                        }
            item {
                menuButton(stringResource(R.string.multiplayer)) {
                    pairsMulti()
                }
            }
        }


        FloatingActionButton(
            onClick = toggleSound, modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp, 24.dp, 24.dp, 90.dp)
        ) {
            val icon =
                if (isSoundEnabled) R.drawable.sound else R.drawable.sound_off
            Icon(painterResource(icon), "toggle sound", modifier = Modifier.size(24.dp))
        }

        if (showAd.value) {
            CustomAd(
                Modifier.align(Alignment.BottomCenter),
                rateUs,
                tryOtherGames,
                launchMarket,
                { showAd.value = false }
            ) { intent: Intent ->
                startIntent(intent)
            }
        }

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

@Composable
fun CustomAd(
    modifier: Modifier,
    rateUs: String,
    tryOtherGames: String,
    launchMarket: () -> Unit,
    close: () -> Unit,
    startIntent: (Intent) -> Unit
) {
    val isRate = (0..1).random() == 1
    val label =
        if (isRate) rateUs else tryOtherGames
    val lambda = {
        if (isRate) {
            launchMarket()
        } else {
            try {
                startIntent(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://search?q=pub%3A%20Simpl3%20apps")
                    )
                )
                //https://play.google.com/store/apps/details?id=com.ger.spot
            } catch (e: ActivityNotFoundException) {
                startIntent(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/search?q=pub%3A%20Simpl3%20apps&c=apps")
                    )
                )
            }
        }
    }
    Box(
        modifier
            .padding(16.dp)
            .size(320.dp, 50.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable {
                lambda()
            }
    ) {
        Icon(
            Icons.Filled.Close, "close",
            Modifier
                .zIndex(8f)
                .align(Alignment.TopEnd)
                .clickable(onClickLabel = "Close") {
                    close()
                })
        Text(
            text = label,
            modifier = Modifier.align(Alignment.Center),
            fontSize = 11.sp
        )
    }
}