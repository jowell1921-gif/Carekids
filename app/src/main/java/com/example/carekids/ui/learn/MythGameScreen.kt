package com.example.carekids.ui.learn

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.carekids.ui.theme.CareKidsBlue
import com.example.carekids.ui.theme.CareKidsLightPink
import com.example.carekids.ui.theme.CareKidsMint
import com.example.carekids.ui.theme.FredokaFamily

// ── Punto de entrada ──────────────────────────────────────────────────────────

@Composable
fun MythGameScreen(
    onBack: () -> Unit,
    viewModel: MythGameViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    MythGameContent(
        uiState    = uiState,
        onBack     = onBack,
        onAnswer   = viewModel::onAnswer,
        onNext     = viewModel::onNext
    )
}

// ── UI stateless ──────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MythGameContent(
    uiState: MythGameUiState,
    onBack: () -> Unit,
    onAnswer: (Boolean) -> Unit,
    onNext: () -> Unit
) {
    val disease = uiState.disease ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "${disease.emoji} ${disease.name}",
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
                    Text(
                        text = "${uiState.currentIndex + 1}/${uiState.totalStatements}",
                        fontFamily = FredokaFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize   = 16.sp,
                        modifier   = Modifier.padding(end = 16.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = disease.color)
            )
        }
    ) { padding ->
        if (uiState.isFinished) {
            ResultsScreen(uiState = uiState, onBack = onBack, modifier = Modifier.padding(padding))
        } else {
            GameplayScreen(
                uiState  = uiState,
                onAnswer = onAnswer,
                onNext   = onNext,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

// ── Pantalla de juego ─────────────────────────────────────────────────────────

@Composable
private fun GameplayScreen(
    uiState: MythGameUiState,
    onAnswer: (Boolean) -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    val disease    = uiState.disease ?: return
    val statement  = disease.statements[uiState.currentIndex]
    val isAnswered = uiState.answerState != AnswerState.WAITING

    Column(
        modifier            = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Barra de progreso
        LinearProgressIndicator(
            progress      = { (uiState.currentIndex + 1).toFloat() / uiState.totalStatements },
            modifier      = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(8.dp)),
            color         = disease.color,
            trackColor    = disease.color.copy(alpha = 0.25f)
        )

        Text(
            text = "¿Mito o Realidad?",
            style = TextStyle(
                fontFamily = FredokaFamily,
                fontWeight = FontWeight.Bold,
                fontSize   = 22.sp,
                color      = Color(0xFF333333)
            )
        )

        // Card con la afirmación — animada al cambiar de pregunta
        AnimatedContent(
            targetState   = uiState.currentIndex,
            transitionSpec = {
                (slideInHorizontally(tween(300)) { it } + fadeIn(tween(300)))
                    .togetherWith(slideOutHorizontally(tween(300)) { -it } + fadeOut(tween(300)))
            },
            label = "statement"
        ) { index ->
            val s = disease.statements[index]
            Card(
                modifier  = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 180.dp),
                shape     = RoundedCornerShape(24.dp),
                colors    = CardDefaults.cardColors(containerColor = disease.color.copy(alpha = 0.35f)),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Box(
                    modifier        = Modifier.fillMaxWidth().padding(28.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text      = s.text,
                        style     = TextStyle(
                            fontFamily = FredokaFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize   = 18.sp,
                            color      = Color(0xFF222222),
                            textAlign  = TextAlign.Center
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Feedback tras responder
        AnimatedVisibility(visible = isAnswered) {
            FeedbackBanner(
                isCorrect   = uiState.answerState == AnswerState.CORRECT,
                explanation = uiState.explanation
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Botones de respuesta o botón "Siguiente"
        if (!isAnswered) {
            AnswerButtons(onAnswer = onAnswer)
        } else {
            NextButton(
                isLast  = uiState.currentIndex + 1 == uiState.totalStatements,
                onClick = onNext
            )
        }
    }
}

@Composable
private fun FeedbackBanner(isCorrect: Boolean, explanation: String) {
    val bgColor   = if (isCorrect) CareKidsMint  else Color(0xFFFFCDD2)
    val textColor = if (isCorrect) Color(0xFF1B5E20) else Color(0xFFB71C1C)
    val icon      = if (isCorrect) "✅" else "❌"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement   = Arrangement.spacedBy(8.dp),
        horizontalAlignment   = Alignment.CenterHorizontally
    ) {
        Text(text = icon, fontSize = 28.sp)
        Text(
            text = if (isCorrect) "¡Correcto!" else "¡Era un mito!",
            style = TextStyle(
                fontFamily = FredokaFamily,
                fontWeight = FontWeight.Bold,
                fontSize   = 16.sp,
                color      = textColor
            )
        )
        Text(
            text = explanation,
            style = TextStyle(
                fontFamily = FredokaFamily,
                fontSize   = 14.sp,
                color      = textColor.copy(alpha = 0.85f),
                textAlign  = TextAlign.Center
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun AnswerButtons(onAnswer: (Boolean) -> Unit) {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Botón MITO
        Button(
            onClick  = { onAnswer(false) },
            modifier = Modifier.weight(1f).height(60.dp),
            shape    = RoundedCornerShape(16.dp),
            colors   = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCDD2))
        ) {
            Text(
                text = "👎 Mito",
                style = TextStyle(
                    fontFamily = FredokaFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize   = 18.sp,
                    color      = Color(0xFFB71C1C)
                )
            )
        }
        // Botón REALIDAD
        Button(
            onClick  = { onAnswer(true) },
            modifier = Modifier.weight(1f).height(60.dp),
            shape    = RoundedCornerShape(16.dp),
            colors   = ButtonDefaults.buttonColors(containerColor = CareKidsMint)
        ) {
            Text(
                text = "👍 Realidad",
                style = TextStyle(
                    fontFamily = FredokaFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize   = 18.sp,
                    color      = Color(0xFF1B5E20)
                )
            )
        }
    }
}

@Composable
private fun NextButton(isLast: Boolean, onClick: () -> Unit) {
    Button(
        onClick  = onClick,
        modifier = Modifier.fillMaxWidth().height(56.dp),
        shape    = RoundedCornerShape(16.dp),
        colors   = ButtonDefaults.buttonColors(containerColor = CareKidsBlue)
    ) {
        Text(
            text = if (isLast) "Ver mis resultados 🏆" else "Siguiente →",
            style = TextStyle(
                fontFamily = FredokaFamily,
                fontWeight = FontWeight.Bold,
                fontSize   = 18.sp,
                color      = Color.White
            )
        )
    }
}

// ── Pantalla de resultados ────────────────────────────────────────────────────

@Composable
private fun ResultsScreen(
    uiState: MythGameUiState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val disease  = uiState.disease ?: return
    val perfect  = uiState.score == uiState.totalStatements

    val (trophy, message) = when {
        perfect                                -> "🏆" to "¡Eres un experto!"
        uiState.score >= uiState.totalStatements / 2 -> "⭐" to "¡Muy bien hecho!"
        else                                   -> "💪" to "¡Sigue practicando!"
    }

    Column(
        modifier            = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = trophy, fontSize = 72.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text  = message,
            style = TextStyle(
                fontFamily = FredokaFamily,
                fontWeight = FontWeight.Bold,
                fontSize   = 28.sp,
                color      = Color(0xFF333333),
                textAlign  = TextAlign.Center
            )
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Puntuación
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(disease.color.copy(alpha = 0.4f))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text  = "${uiState.score} / ${uiState.totalStatements} correctas",
                    style = TextStyle(
                        fontFamily = FredokaFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize   = 22.sp,
                        color      = Color(0xFF333333)
                    )
                )
                Text(
                    text  = "⭐ +${uiState.carePointsEarned} CarePoints",
                    style = TextStyle(
                        fontFamily = FredokaFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize   = 18.sp,
                        color      = Color(0xFF555555)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick  = onBack,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape    = RoundedCornerShape(16.dp),
            colors   = ButtonDefaults.buttonColors(containerColor = CareKidsBlue)
        ) {
            Text(
                text = "Volver al inicio",
                style = TextStyle(
                    fontFamily = FredokaFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize   = 18.sp,
                    color      = Color.White
                )
            )
        }
    }
}
