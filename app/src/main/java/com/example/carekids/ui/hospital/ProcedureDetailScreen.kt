package com.example.carekids.ui.hospital

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.carekids.data.model.Procedure
import com.example.carekids.data.model.ProcedureStep
import com.example.carekids.data.model.allProcedures
import com.example.carekids.ui.theme.CareKidsBlue
import com.example.carekids.ui.theme.CareKidsLightPink
import com.example.carekids.ui.theme.CareKidsMint
import com.example.carekids.ui.theme.FredokaFamily

// ViewModel mínimo — solo resuelve el procedureId desde la navegación
class ProcedureDetailViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val procedure: Procedure = allProcedures.first {
        it.id == checkNotNull(savedStateHandle["procedureId"])
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProcedureDetailScreen(
    onBack: () -> Unit,
    viewModel: ProcedureDetailViewModel = viewModel()
) {
    val procedure = viewModel.procedure

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "${procedure.emoji} ${procedure.name}",
                        fontFamily = FredokaFamily,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "Volver") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CareKidsLightPink)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier       = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                InfoRow(procedure = procedure)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text  = "Así va a ocurrir, paso a paso:",
                    style = TextStyle(fontFamily = FredokaFamily, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color(0xFF444444))
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            itemsIndexed(procedure.steps) { index, step ->
                StepCard(step = step, stepNumber = index + 1, isLast = index == procedure.steps.lastIndex)
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                EncouragementBanner()
            }
        }
    }
}

@Composable
private fun InfoRow(procedure: Procedure) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CareKidsBlue.copy(alpha = 0.2f))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        InfoItem(emoji = "⏱", label = "Duración", value = procedure.duration)
        InfoItem(emoji = "💊", label = "Dolor", value = procedure.painLevel)
        InfoItem(emoji = "📋", label = "Pasos", value = "${procedure.steps.size} pasos")
    }
}

@Composable
private fun InfoItem(emoji: String, label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(text = emoji, fontSize = 22.sp)
        Text(text = label, style = TextStyle(fontFamily = FredokaFamily, fontSize = 10.sp, color = Color(0xFF888888)))
        Text(text = value, style = TextStyle(fontFamily = FredokaFamily, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF333333)))
    }
}

@Composable
private fun StepCard(step: ProcedureStep, stepNumber: Int, isLast: Boolean) {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment     = Alignment.Top
    ) {
        // Línea de progreso vertical
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier         = Modifier.size(36.dp).clip(CircleShape).background(CareKidsBlue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text  = "$stepNumber",
                    style = TextStyle(fontFamily = FredokaFamily, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.White)
                )
            }
            if (!isLast) {
                Box(modifier = Modifier.width(2.dp).height(12.dp).background(CareKidsBlue.copy(alpha = 0.3f)))
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF8F8F8))
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = step.emoji, fontSize = 22.sp)
                Text(
                    text  = step.title,
                    style = TextStyle(fontFamily = FredokaFamily, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF333333))
                )
            }
            Text(
                text  = step.description,
                style = TextStyle(fontFamily = FredokaFamily, fontSize = 13.sp, color = Color(0xFF555555))
            )
        }
    }
}

@Composable
private fun EncouragementBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(CareKidsMint)
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text  = "💪 Recuerda: el equipo médico está aquí para cuidarte. ¡Tú puedes!",
            style = TextStyle(
                fontFamily = FredokaFamily,
                fontWeight = FontWeight.Bold,
                fontSize   = 15.sp,
                color      = Color(0xFF1B5E20)
            ),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
