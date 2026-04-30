package com.example.carekids

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.constraintlayout.compose.Dimension
import kotlinx.coroutines.launch

/**
 * Project: CareKids
 * From: com.example.carekids.ui.theme
 * Created by: Joel Arturo Osorio
 * On: 02/12/2025 at 22: 06
 * All rights reserved 2025.
 */
@Preview(showBackground = true)
@Composable
private fun PreviewCardImageText() {
    CardImageText(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        image = R.drawable.hospital_family,
        text = "Estación de la familia",
        backgroundColor = colorResource(R.color.carekids_yellow))
}


@Composable
fun CardImageText(
    modifier: Modifier = Modifier,
    image: Int,
    text: String,
    backgroundColor: Color,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .height(120.dp)
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = CardDefaults.shape
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)   
            ) {
                val scale = remember { Animatable(1f) }
                val scope = rememberCoroutineScope()
                Image(
                    painter = painterResource(id = image),
                    contentDescription = text,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(scaleX = scale.value, scaleY = scale.value)
                        .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                )   {
                        scope.launch {
                            scale.animateTo(1.2f, animationSpec = tween(150))
                            scale.animateTo(1f,   animationSpec = tween(150))
                            onClick()
                        }
                    }
                )
            }

        Text(text = text,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.White,
            fontWeight = FontWeight.Bold)
        }
    }
}