package com.ger.memo

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

enum class CardFace(val angle: Float) {
    Front(0f) {
        override val next: CardFace
            get() = Back
    },
    Back(180f) {
        override val next: CardFace
            get() = Front
    };

    abstract val next: CardFace
}

@Composable
fun FlipCard(
    cardFace: CardFace,
    modifier: Modifier = Modifier,
    back: @Composable () -> Unit = {},
    front: @Composable () -> Unit = {},
) {
    val rotation = animateFloatAsState(
        targetValue = cardFace.angle,
        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutSlowInEasing,
        ), label = ""
    )
    Box(
        modifier = modifier
            .graphicsLayer {
                rotationY = rotation.value
                cameraDistance = 12f * density
            },
        contentAlignment = Alignment.Center
    ) {
        if (rotation.value <= 90f) {
            Box(
            ) {
                front()
            }
        } else {
            Box(
                Modifier
                    .graphicsLayer {
                        rotationY = 180f
                    },
            ) {
                back()
            }
        }
    }
}
