package com.example.carekids.ui.hospital

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carekids.ui.theme.CareKidsBlue
import com.example.carekids.ui.theme.CareKidsLightPink
import com.example.carekids.ui.theme.CareKidsMint
import com.example.carekids.ui.theme.CareKidsYellow
import com.example.carekids.ui.theme.FredokaFamily

private data class HospitalSection(
    val emoji: String,
    val title: String,
    val subtitle: String,
    val color: Color,
    val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HospitalScreen(
    onBack: () -> Unit,
    onProceduresClick: () -> Unit,
    onTeamClick: () -> Unit,
    onRightsClick: () -> Unit
) {
    val sections = listOf(
        HospitalSection("🩺", "¿Qué va a pasar hoy?", "Guías paso a paso de procedimientos médicos", CareKidsBlue, onProceduresClick),
        HospitalSection("👩‍⚕️", "Mi equipo médico", "Las personas que me cuidan cada día", CareKidsMint, onTeamClick),
        HospitalSection("⭐", "Mis derechos", "Lo que me corresponde como paciente", CareKidsYellow, onRightsClick)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Mi hospital y yo", fontFamily = FredokaFamily, fontWeight = FontWeight.Bold)
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
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "🏥 ¿Qué quieres explorar hoy?",
                style = TextStyle(
                    fontFamily = FredokaFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize   = 18.sp,
                    color      = Color(0xFF444444)
                )
            )
            sections.forEach { section ->
                SectionCard(section = section)
            }
        }
    }
}

@Composable
private fun SectionCard(section: HospitalSection) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(section.color.copy(alpha = 0.45f))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication        = null,
                onClick           = section.onClick
            )
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = section.emoji, fontSize = 40.sp)
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = section.title,
                style = TextStyle(
                    fontFamily = FredokaFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize   = 18.sp,
                    color      = Color(0xFF222222)
                )
            )
            Text(
                text = section.subtitle,
                style = TextStyle(
                    fontFamily = FredokaFamily,
                    fontSize   = 13.sp,
                    color      = Color(0xFF555555)
                )
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewHospitalScreen() {
    HospitalScreen(onBack = {}, onProceduresClick = {}, onTeamClick = {}, onRightsClick = {})
}
