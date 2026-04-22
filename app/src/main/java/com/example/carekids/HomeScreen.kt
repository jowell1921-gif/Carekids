package com.example.carekids

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.example.carekids.ui.theme.FredokaFamily
import kotlinx.coroutines.launch
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition


/**
 * Project: CareKids
 * From: com.example.carekids
 * Created by: Joel Arturo Osorio
 * On: 01/12/2025 at 16: 07
 * All rights reserved 2025.
 */

@Preview(showBackground = true)
@Composable
fun PreviewContentView() {
    ContentView(modifier = Modifier, {}, {})
}

@Composable
fun ContentView (modifier: Modifier,
                 onProfileClick: () -> Unit,
                 onDialogClick: () -> Unit) {
    Column (modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
    ) {
        // Contenido principal (logo + botones + tarjeta)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.common_padding_X_large))
        ) {
            //IMAGEN CAREKIDS
            val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.carekid_icon))
            val progress = animateLottieCompositionAsState(
                composition = composition.value,
                iterations = LottieConstants.IterateForever
            )

            Spacer(modifier = Modifier.height(48.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                contentAlignment = Alignment.Center
            ) {
                LottieAnimation(
                    composition = composition.value,
                    progress = { progress.value },
                    modifier = Modifier.size(280.dp)
                )
            }
            //BOTÓN RECTANGULAR GRANDE
            val scaleProfile = remember { Animatable(1f) }
            val scope = rememberCoroutineScope()
            ElevatedButton(
                onClick = {
                    scope.launch {
                        scaleProfile.animateTo(1.1f, tween(120))
                        scaleProfile.animateTo(1f, tween(120))
                        onProfileClick()
                    }
                },
                modifier = Modifier
                    .graphicsLayer(
                        scaleX = scaleProfile.value,
                        scaleY = scaleProfile.value
                    )
                    .padding(dimensionResource(R.dimen.common_padding_default)),
                elevation = ButtonDefaults.elevatedButtonElevation(6.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = colorResource(R.color.carekids_light_pink)
                )
            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
            Image(
                    painter = painterResource(id = R.drawable.icon_profile),
            contentDescription = "Mi perfil de superhéroe",
            modifier = Modifier
                .size(38.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
                Text(text = "¡Soy un superhéroe!", fontSize = 16.sp)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        //BOTÓN SUPERIOR IZQUIERDO
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            IconCardButtom (
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp),
                imageRes = R.drawable.icon_animal,
                text = "¡Mi mascota!",
                backgroundColor = colorResource(R.color.carekids_blue),
                textColor = Color(0xFF6A0572),
                onClick = { /* acción del botón */ }
            )
            //BOTÓN SUPERIOR DERECHO
            IconCardButtom(
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp),
                imageRes = R.drawable.icon_hospital,
                text = "¡A jugar y aprender!",
                backgroundColor = colorResource(R.color.carekids_yellow),
                textColor = Color(0xFF1565C0),
                onClick = { /* acción del botón */ }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        //BOTÓN INFERIOR IZQUIERDO
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            IconCardButtom(
                onClick = {},
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp),
                imageRes = R.drawable.icon_people,
                text = "¿Cómo estoy hoy?",
                backgroundColor = colorResource(R.color.carekids_yellow),
                textColor = Color(0xFFB71C1C),
            )
            //BOTÓN INFERIOR DERECHO
            IconCardButtom(
                onClick = { onDialogClick () },
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp),
                imageRes = R.drawable.icon_rewards,
                text = "¡Mis premios!",
                backgroundColor = colorResource(R.color.carekids_blue),
                textColor = Color(0xFF1B5E20),
            )
        }
            // PRIMER DIVIDER
            Spacer(modifier = Modifier.height(32.dp))

            CareKidsDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            //BOTÓN GRANDE INFERIOR
            CardImageText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                image = R.drawable.hospital_family,
                text = "Mi hospital y yo",
                backgroundColor = colorResource(R.color.carekids_yellow)
            )
            Spacer(modifier = Modifier.height(24.dp))

            // CARRUSEL DE NOTICIAS
            NewsCarousel()

            Spacer(modifier = Modifier.height(32.dp))
            // SEGUNDO DIVIDER
            CareKidsDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
            )
            Spacer(modifier = Modifier.height(32.dp))
        }

    }
}

@Composable
fun IconCardButtom(
    modifier: Modifier = Modifier,
    @DrawableRes imageRes: Int,
    text: String,
    backgroundColor: Color,
    textColor: Color = Color(0xFF333333),
    onClick: () -> Unit
) {
    val scale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()

    Card(
        modifier = modifier
            .graphicsLayer(scaleX = scale.value, scaleY = scale.value)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                scope.launch {
                    scale.animateTo(1.1f, animationSpec = tween(160))
                    scale.animateTo(1f, animationSpec = tween(160))
                    onClick()
                }
            },
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.size(38.dp)
            )

            Text(
                text = text,
                style = TextStyle(
                    fontFamily = FredokaFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = textColor,
                    shadow = Shadow(
                        color = Color.White.copy(alpha = 0.6f),
                        offset = Offset(1f, 1f),
                        blurRadius = 2f
                    )
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CareKidsDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    color: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
) {
    HorizontalDivider(
        modifier = modifier,
        thickness = thickness,
        color = color
    )
}



