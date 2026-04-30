package com.example.carekids.ui.pet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.carekids.ui.theme.CareKidsBlue
import com.example.carekids.ui.theme.CareKidsLightPink
import com.example.carekids.ui.theme.CareKidsMint
import com.example.carekids.ui.theme.CareKidsYellow
import com.example.carekids.ui.theme.FredokaFamily
import kotlinx.coroutines.launch

// ── Punto de entrada ──────────────────────────────────────────────────────────

@Composable
fun PetScreen(
    onBack: () -> Unit,
    viewModel: PetViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PetContent(
        uiState = uiState,
        onBack  = onBack,
        onFeed  = viewModel::onFeed,
        onPlay  = viewModel::onPlay,
        onHug   = viewModel::onHug
    )
}

// ── UI stateless ──────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetContent(
    uiState: PetUiState,
    onBack: () -> Unit,
    onFeed: () -> Unit,
    onPlay: () -> Unit,
    onHug: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.petName,
                        fontFamily = FredokaFamily,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    // Puntos totales en el TopAppBar
                    Text(
                        text = "⭐ ${uiState.points} pts",
                        fontFamily = FredokaFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CareKidsBlue)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            LevelBadge(level = uiState.level)
            PetDisplay(uiState = uiState)
            XpBar(progress = uiState.xpProgress, level = uiState.level)
            MoodLabel(mood = uiState.mood)
            Spacer(modifier = Modifier.weight(1f))
            ActionButtons(onFeed = onFeed, onPlay = onPlay, onHug = onHug)
        }
    }
}

// ── Secciones ─────────────────────────────────────────────────────────────────

@Composable
private fun LevelBadge(level: PetLevel) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = level.emoji, fontSize = 20.sp)
        Text(
            text = "Nivel ${level.ordinal + 1} — ${level.label}",
            style = TextStyle(
                fontFamily = FredokaFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF444444)
            )
        )
    }
}

@Composable
private fun PetDisplay(uiState: PetUiState) {
    // Tamaño de la mascota crece con el nivel: de 100dp a 180dp
    val targetSize = (100 + uiState.level.ordinal * 20).dp

    // Animación de respiración continua
    val breathScale = remember { Animatable(1f) }
    LaunchedEffect(Unit) {
        while (true) {
            breathScale.animateTo(1.08f, tween(900, easing = EaseInOut))
            breathScale.animateTo(1f,    tween(900, easing = EaseInOut))
        }
    }

    // Color del fondo según el mood
    val bgColor = when (uiState.mood) {
        PetMood.HAPPY   -> CareKidsMint
        PetMood.NEUTRAL -> CareKidsYellow
        PetMood.TIRED   -> CareKidsLightPink
    }

    Box(contentAlignment = Alignment.Center) {
        // Círculo de fondo con degradado
        Box(
            modifier = Modifier
                .size(targetSize + 40.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(bgColor, bgColor.copy(alpha = 0.3f))
                    )
                )
        )

        // Mascota con animación de respiración
        Text(
            text     = uiState.petEmoji,
            fontSize = (targetSize.value * 0.55f).sp,
            modifier = Modifier.scale(breathScale.value)
        )

        // "+N pts" animación flotante
        AnimatedVisibility(
            visible = uiState.showPointsAnim,
            enter   = fadeIn() + scaleIn(),
            exit    = fadeOut(),
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Text(
                text = "+${uiState.lastPointsEarned} pts",
                style = TextStyle(
                    fontFamily  = FredokaFamily,
                    fontWeight  = FontWeight.Bold,
                    fontSize    = 18.sp,
                    color       = Color(0xFF1B5E20)
                )
            )
        }
    }
}

@Composable
private fun XpBar(progress: Float, level: PetLevel) {
    val animatedProgress by animateFloatAsState(
        targetValue    = progress,
        animationSpec  = tween(600),
        label          = "xpProgress"
    )

    val isMaxLevel = level == PetLevel.CHAMPION

    Column(
        modifier            = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text       = if (isMaxLevel) "¡Nivel máximo!" else "Progreso al siguiente nivel",
                fontFamily = FredokaFamily,
                fontSize   = 13.sp,
                color      = Color(0xFF666666)
            )
            if (!isMaxLevel) {
                Text(
                    text       = "${(animatedProgress * 100).toInt()}%",
                    fontFamily = FredokaFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize   = 13.sp,
                    color      = Color(0xFF444444)
                )
            }
        }
        LinearProgressIndicator(
            progress           = { animatedProgress },
            modifier           = Modifier
                .fillMaxWidth()
                .height(14.dp)
                .clip(RoundedCornerShape(8.dp)),
            color              = CareKidsBlue,
            trackColor         = CareKidsBlue.copy(alpha = 0.2f)
        )
    }
}

@Composable
private fun MoodLabel(mood: PetMood) {
    val (emoji, color) = when (mood) {
        PetMood.HAPPY   -> "😄" to Color(0xFF2E7D32)
        PetMood.NEUTRAL -> "😊" to Color(0xFFF57F17)
        PetMood.TIRED   -> "😴" to Color(0xFF6A1B9A)
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Text(text = emoji, fontSize = 20.sp)
        Text(
            text  = mood.label,
            style = TextStyle(
                fontFamily = FredokaFamily,
                fontWeight = FontWeight.Bold,
                fontSize   = 16.sp,
                color      = color
            )
        )
    }
}

@Composable
private fun ActionButtons(onFeed: () -> Unit, onPlay: () -> Unit, onHug: () -> Unit) {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ActionButton(
            emoji   = "🍎",
            label   = "Dar de comer\n+10 pts",
            color   = CareKidsMint,
            onClick = onFeed,
            modifier = Modifier.weight(1f)
        )
        ActionButton(
            emoji   = "🎮",
            label   = "Jugar\n+15 pts",
            color   = CareKidsBlue,
            onClick = onPlay,
            modifier = Modifier.weight(1f)
        )
        ActionButton(
            emoji   = "🤗",
            label   = "Abrazo\n+5 pts",
            color   = CareKidsLightPink,
            onClick = onHug,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ActionButton(
    emoji: String,
    label: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Animación de rebote al pulsar
    val scale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()

    Card(
        onClick = {
            scope.launch {
                scale.animateTo(0.9f, tween(80))
                scale.animateTo(1f,   tween(80))
                onClick()
            }
        },
        modifier = modifier
            .height(90.dp)
            .scale(scale.value),
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier              = Modifier.fillMaxSize(),
            horizontalAlignment   = Alignment.CenterHorizontally,
            verticalArrangement   = Arrangement.Center
        ) {
            Text(text = emoji, fontSize = 28.sp)
            Text(
                text      = label,
                fontFamily = FredokaFamily,
                fontWeight = FontWeight.Bold,
                fontSize  = 11.sp,
                textAlign = TextAlign.Center,
                color     = Color(0xFF333333),
                lineHeight = 14.sp
            )
        }
    }
}

// ── Preview ───────────────────────────────────────────────────────────────────

@Preview(showSystemUi = true)
@Composable
private fun PreviewPetContent() {
    PetContent(
        uiState = PetUiState(
            petEmoji   = "🐶",
            petName    = "Perrito",
            points     = 175,
            level      = PetLevel.SMALL,
            mood       = PetMood.HAPPY,
            xpProgress = 0.5f
        ),
        onBack = {},
        onFeed = {},
        onPlay = {},
        onHug  = {}
    )
}
