package com.example.carekids.ui.emotional

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.carekids.data.db.EmotionalEntry
import com.example.carekids.ui.theme.CareKidsLightPink
import com.example.carekids.ui.theme.CareKidsMint
import com.example.carekids.ui.theme.CareKidsYellow
import com.example.carekids.ui.theme.FredokaFamily
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// ── Punto de entrada ──────────────────────────────────────────────────────────

@Composable
fun EmotionalHistoryScreen(
    onBack: () -> Unit,
    viewModel: EmotionalHistoryViewModel = viewModel()
) {
    val entries by viewModel.entries.collectAsStateWithLifecycle()
    EmotionalHistoryContent(entries = entries, onBack = onBack)
}

// ── UI stateless ──────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmotionalHistoryContent(
    entries: List<EmotionalEntry>,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mis emociones",
                        fontFamily = FredokaFamily,
                        fontWeight = FontWeight.Bold
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
        if (entries.isEmpty()) {
            EmptyState(modifier = Modifier.fillMaxSize().padding(padding))
        } else {
            LazyColumn(
                modifier            = Modifier.fillMaxSize().padding(padding),
                contentPadding      = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(entries, key = { it.id }) { entry ->
                    EntryCard(entry = entry)
                }
            }
        }
    }
}

// ── Componentes ───────────────────────────────────────────────────────────────

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier            = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "🌱", fontSize = 56.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Aquí aparecerán\ntus emociones del día",
            style = TextStyle(
                fontFamily = FredokaFamily,
                fontWeight = FontWeight.Bold,
                fontSize   = 18.sp,
                color      = Color(0xFF888888),
                textAlign  = TextAlign.Center
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun EntryCard(entry: EmotionalEntry) {
    // Recuperamos el enum a partir del nombre guardado en la BD.
    // runCatching evita un crash si por algún motivo hay un nombre inválido.
    val emotion = runCatching { Emotion.valueOf(entry.emotionName) }.getOrNull()

    val bgColor = when (emotion) {
        Emotion.HAPPY, Emotion.CALM       -> CareKidsMint
        Emotion.SAD, Emotion.SCARED       -> CareKidsLightPink
        Emotion.NERVOUS, Emotion.CONFUSED -> CareKidsYellow
        else                               -> Color(0xFFF0F0F0)
    }

    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy · HH:mm", Locale.getDefault()) }
    val dateText   = dateFormat.format(Date(entry.timestamp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(text = emotion?.emoji ?: "❓", fontSize = 36.sp)

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Text(
                    text = emotion?.label ?: entry.emotionName,
                    style = TextStyle(
                        fontFamily = FredokaFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize   = 16.sp,
                        color      = Color(0xFF333333)
                    )
                )
                Text(
                    text  = dateText,
                    style = TextStyle(
                        fontFamily = FredokaFamily,
                        fontSize   = 11.sp,
                        color      = Color(0xFF777777)
                    )
                )
            }
            if (entry.note.isNotBlank()) {
                Text(
                    text  = entry.note,
                    style = TextStyle(
                        fontFamily = FredokaFamily,
                        fontSize   = 13.sp,
                        color      = Color(0xFF555555)
                    ),
                    maxLines = 3
                )
            }
        }
    }
}
