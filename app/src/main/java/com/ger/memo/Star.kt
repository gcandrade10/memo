package com.ger.memo

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.ratingbar.RatingBar
import com.smarttoolfactory.ratingbar.model.GestureStrategy

@Composable
fun star(rating: Float) {
    val imageBackground = ImageBitmap.imageResource(id = R.drawable.star_background)
    val imageForeground = ImageBitmap.imageResource(id = R.drawable.star_foreground)
    RatingBar(
        itemCount = 3,
        modifier = Modifier.fillMaxWidth().height(50.dp),
        rating = rating,
        space = 2.dp,
        imageEmpty = imageBackground,
        imageFilled = imageForeground,
        itemSize = 60.dp,
        gestureStrategy = GestureStrategy.None
    ) {
    }
}