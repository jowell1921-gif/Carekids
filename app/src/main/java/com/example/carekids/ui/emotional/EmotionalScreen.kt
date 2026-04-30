package com.example.carekids.ui.emotional

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.carekids.ui.theme.CareKidsBlue
import com.example.carekids.ui.theme.CareKidsLightPink
import com.example.carekids.ui.theme.CareKidsMint
import com.example.carekids.ui.theme.CareKidsYellow
import com.example.carekids.ui.theme.CareKidsLilac
import com.example.carekids.ui.theme.FredokaFamily
import kotlinx.coroutines.launch

// ── Punto de entrada ──────────────────────────────────────────────────────────

@Composable
fun EmotionalScreen(
    onBack: () -> Unit,
    onHistoryClick: () -> Unit,
    viewModel: EmotionalViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) onBack()
    }

    EmotionalContent(
        uiState           = uiState,
        onBack            = onBack,
        onHistoryClick    = onHistoryClick,
        onEmotionSelected = viewModel::onEmotionSelected,
        onNoteChanged     = viewModel::onNoteChanged,
        onSave            = viewModel::onSave
    )
}

// ── UI stateless ──────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmotionalContent(
    uiState: EmotionalUiState,
    onBack: () -> Unit,
    onHistoryClick: () -> Unit,
    onEmotionSelected: (Emotion) -> Unit,
    onNoteChanged: (String) -> Unit,
    onSave: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "¿Cómo me siento hoy?",
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
                    IconButton(onClick = onHistoryClick) {
                        Icon(Icons.Filled.DateRange, contentDescription = "Ver historial")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CareKidsYellow )
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                HeaderSection()
                EmotionGrid(
                    emotions = Emotion.entries,
                    selected = uiState.selectedEmotion,
                    onSelected = onEmotionSelected
                )
                uiState.selectedEmotion?.let { emotion ->
                    MessageBubble(message = emotion.message)
                    NoteSection(note = uiState.note, onNoteChanged = onNoteChanged)
                    SaveButton(onClick = onSave)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Overlay de celebración
            if (uiState.showCelebration) {
                CelebrationOverlay()
            }
        }
    }
}

// ── Secciones ─────────────────────────────────────────────────────────────────

@Composable
private fun HeaderSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "🌈", fontSize = 48.sp)
        Text(
            text = "Toca cómo te sientes ahora mismo",
            style = TextStyle(
                fontFamily = FredokaFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF444444),
                textAlign = TextAlign.Center
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun EmotionGrid(
    emotions: List<Emotion>,
    selected: Emotion?,
    onSelected: (Emotion) -> Unit
) {
    // Grid 4×2 usando dos filas de Row
    val rows = emotions.chunked(4)
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        rows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                row.forEach { emotion ->
                    EmotionTile(
                        emotion  = emotion,
                        isSelected = emotion == selected,
                        onSelected = onSelected,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun EmotionTile(
    emotion: Emotion,
    isSelected: Boolean,
    onSelected: (Emotion) -> Unit,
    modifier: Modifier = Modifier
) {
    val scale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()

    val bgColor = if (isSelected) CareKidsMint else Color(0xFFF5F5F5)
    val borderColor = if (isSelected) CareKidsBlue else Color.Transparent

    Card(
        modifier = modifier
            .height(80.dp)
            .scale(scale.value)
            .border(2.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                scope.launch {
                    scale.animateTo(0.9f, tween(80))
                    scale.animateTo(1f,   tween(80))
                    onSelected(emotion)
                }
            },
        shape  = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 6.dp else 2.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = emotion.emoji, fontSize = 28.sp)
            Text(
                text = emotion.label,
                fontFamily = FredokaFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                color = Color(0xFF444444),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun MessageBubble(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(CareKidsYellow.copy(alpha = 0.6f))
            .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
        Text(
            text = message,
            style = TextStyle(
                fontFamily = FredokaFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF444444),
                textAlign = TextAlign.Center
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun NoteSection(note: String, onNoteChanged: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "¿Quieres contarme algo más?",
            style = TextStyle(
                fontFamily = FredokaFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF555555)
            )
        )
        OutlinedTextField(
            value          = note,
            onValueChange  = onNoteChanged,
            placeholder    = { Text("Escribe lo que quieras...", fontFamily = FredokaFamily) },
            modifier       = Modifier
                .fillMaxWidth()
                .height(120.dp),
            shape          = RoundedCornerShape(16.dp),
            textStyle      = TextStyle(fontFamily = FredokaFamily, fontSize = 15.sp),
            colors         = OutlinedTextFieldDefaults.colors(
                focusedBorderColor   = CareKidsLilac,
                unfocusedBorderColor = CareKidsLilac.copy(alpha = 0.5f)
            ),
            supportingText = {
                Text(
                    text = "${note.length}/200",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    fontSize = 11.sp,
                    color = Color(0xFF888888)
                )
            }
        )
    }
}

@Composable
private fun SaveButton(onClick: () -> Unit) {
    Button(
        onClick  = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape  = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = CareKidsBlue)
    ) {
        Text(
            text = "¡Guardar cómo me siento!",
            style = TextStyle(
                fontFamily = FredokaFamily,
                fontWeight = FontWeight.Bold,
                fontSize   = 18.sp,
                color      = Color.White
            )
        )
    }
}

@Composable
private fun CelebrationOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.35f)),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = true,
            enter   = fadeIn() + scaleIn(initialScale = 0.6f),
            exit    = fadeOut() + scaleOut()
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color.White)
                    .padding(horizontal = 40.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = "🌟", fontSize = 56.sp)
                Text(
                    text = "¡Gracias por compartir\ncómo te sientes!",
                    style = TextStyle(
                        fontFamily = FredokaFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize   = 20.sp,
                        color      = Color(0xFF333333),
                        textAlign  = TextAlign.Center
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// ── Preview ───────────────────────────────────────────────────────────────────

@Preview(showSystemUi = true)
@Composable
private fun PreviewEmotionalContent() {
    EmotionalContent(
        uiState           = EmotionalUiState(selectedEmotion = Emotion.HAPPY, note = "Hoy me visita mi mamá 💛"),
        onBack            = {},
        onHistoryClick    = {},
        onEmotionSelected = {},
        onNoteChanged     = {},
        onSave            = {}
    )
}
