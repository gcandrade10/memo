package com.ger.memo

import android.util.Log
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.espresso.device.DeviceInteraction.Companion.setScreenOrientation
import androidx.test.espresso.device.EspressoDevice.Companion.onDevice
import androidx.test.espresso.device.action.ScreenOrientation
import androidx.test.espresso.device.rules.ScreenOrientationRule
import com.ger.memo.numbers.NumbersLayout
import com.ger.memo.numbers.NumbersViewmodel
import com.ger.memo.numbers.data.Item
import com.ger.memo.numbers.data.NumberGameState
import com.ger.memo.util.waitUntilTimeout
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class NumbersTest {

    @get:Rule
    val screenOrientationRule: ScreenOrientationRule =
        ScreenOrientationRule(ScreenOrientation.LANDSCAPE)

    @get:Rule
    val composeTestRule = createComposeRule()

    private val viewModel = NumbersViewmodel()
    private val list = (1..12).toList().mapIndexed { index, i ->
        Item(index, i)
    }

    @Before
    fun set_list() {
        onDevice().setScreenOrientation(ScreenOrientation.LANDSCAPE)
        composeTestRule.setContent {
            viewModel.gameState.value = NumberGameState(list)
            NumbersLayout(viewModel) {}
        }
    }

    @Test
    fun test_numbers_win() {
        for (i in 0..<list.size - 1) {
            Log.d("MyApp", "we are on $i item")
            composeTestRule.onNode(hasTestTag("Back$i"), useUnmergedTree = true).performClick()
            composeTestRule.onNode(hasTestTag("Front$i"), useUnmergedTree = true)
                .assertExists("Card didn't flipped")
        }
        composeTestRule.onNode(hasTestTag("Back11"), useUnmergedTree = true).performClick()

        // TODO why this is not working?
//        composeTestRule.onNode(hasTestTag("completeDialog"), useUnmergedTree = true)
//            .assertExists("win dialog not found")
    }

    @Test
    fun test_numbers_wrong() {
        composeTestRule.onNode(hasTestTag("Back0"), useUnmergedTree = true).performClick()
        composeTestRule.onNode(hasTestTag("Front0"), useUnmergedTree = true).assertExists("Card didn't flipped")
        composeTestRule.onNode(hasTestTag("Back2"), useUnmergedTree = true).performClick()
        composeTestRule.onNode(hasTestTag("Front2"), useUnmergedTree = true).assertExists("Card didn't flipped")

        composeTestRule.onNode(hasTestTag("Back3"), useUnmergedTree = true).performClick()

        composeTestRule.onNode(hasTestTag("Back0"), useUnmergedTree = true).assertExists("Card didn't flipped back")
        composeTestRule.onNode(hasTestTag("Back2"), useUnmergedTree = true).assertExists("Card didn't flipped back")
        composeTestRule.waitUntilTimeout(5000L)

    }
}