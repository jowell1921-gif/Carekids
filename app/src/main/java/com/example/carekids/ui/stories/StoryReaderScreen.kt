package com.example.carekids.ui.stories

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.carekids.R
import com.example.carekids.ui.theme.CareKidsBlue
import com.example.carekids.ui.theme.CareKidsLightPink
import com.example.carekids.ui.theme.CareKidsMint
import com.example.carekids.ui.theme.CareKidsYellow
import com.example.carekids.ui.theme.FredokaFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryReaderScreen(
    onBack: () -> Unit,
    viewModel: StoryReaderViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val story = uiState.story ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "${story.emoji} ${story.title}",
                        fontFamily = FredokaFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CareKidsLightPink)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Cover image (only on page 0)
            if (uiState.currentPage == 0) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(story.coverImageUrl)
                        .addHeader("User-Agent", "CareKids/1.0 (Android)")
                        .crossfade(true)
                        .build(),
                    contentDescription = story.title,
                    contentScale = ContentScale.Crop,
                    error = painterResource(R.drawable.ic_hospital),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(20.dp))
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Page text with animation
            AnimatedContent(
                targetState = uiState.currentPage,
                transitionSpec = {
                    if (targetState > initialState) {
                        (slideInHorizontally { it } + fadeIn()) togetherWith
                                (slideOutHorizontally { -it } + fadeOut())
                    } else {
                        (slideInHorizontally { -it } + fadeIn()) togetherWith
                                (slideOutHorizontally { it } + fadeOut())
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                label = "page"
            ) { page ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    CareKidsMint.copy(alpha = 0.25f),
                                    CareKidsBlue.copy(alpha = 0.1f)
                                )
                            )
                        )
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState()),
                    contentAlignment = Alignment.TopStart
                ) {
                    Text(
                        text = story.pages[page],
                        style = TextStyle(
                            fontFamily = FredokaFamily,
                            fontSize = 19.sp,
                            lineHeight = 28.sp,
                            color = Color(0xFF333333),
                            textAlign = TextAlign.Start
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Page indicator dots
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                story.pages.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .size(if (index == uiState.currentPage) 10.dp else 7.dp)
                            .clip(CircleShape)
                            .background(
                                if (index == uiState.currentPage) CareKidsLightPink
                                else Color(0xFFDDDDDD)
                            )
                    )
                    if (index < story.pages.size - 1) Spacer(modifier = Modifier.width(6.dp))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Navigation buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = viewModel::previousPage,
                    enabled = uiState.currentPage > 0,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        text = "← Atrás",
                        fontFamily = FredokaFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                val isLastPage = uiState.currentPage == story.pages.size - 1
                Button(
                    onClick = if (isLastPage) onBack else viewModel::nextPage,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isLastPage) CareKidsMint else CareKidsLightPink
                    )
                ) {
                    Text(
                        text = if (isLastPage) "¡Fin! 🎉" else "Siguiente →",
                        fontFamily = FredokaFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }

        // Celebration overlay
        if (uiState.showCelebration) {
            CelebrationDialog(
                points = story.pointsReward,
                onDismiss = viewModel::dismissCelebration
            )
        }
    }
}

@Composable
private fun CelebrationDialog(points: Int, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(28.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(CareKidsYellow.copy(alpha = 0.9f), CareKidsLightPink)
                    )
                )
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = "🎉", fontSize = 56.sp)
                Text(
                    text = "¡Cuento terminado!",
                    style = TextStyle(
                        fontFamily = FredokaFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = Color(0xFF333333),
                        textAlign = TextAlign.Center
                    )
                )
                Text(
                    text = "¡Has ganado\n+$points puntos! ⭐",
                    style = TextStyle(
                        fontFamily = FredokaFamily,
                        fontSize = 18.sp,
                        color = Color(0xFF555555),
                        textAlign = TextAlign.Center
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CareKidsLightPink)
                ) {
                    Text(
                        text = "¡Genial! 🌟",
                        fontFamily = FredokaFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}
