package com.example.carekids.ui.learn

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.carekids.data.model.Disease
import com.example.carekids.data.model.allDiseases
import com.example.carekids.ui.theme.CareKidsLightPink
import com.example.carekids.ui.theme.FredokaFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnScreen(
    onBack: () -> Unit,
    onDiseaseClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "¡A jugar y aprender!",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Elige tu enfermedad para aprender jugando 🎮",
                style = TextStyle(
                    fontFamily = FredokaFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize   = 16.sp,
                    color      = Color(0xFF555555),
                    textAlign  = TextAlign.Center
                ),
                textAlign = TextAlign.Center,
                modifier  = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))

            // Grid 2x2
            val rows = allDiseases.chunked(2)
            rows.forEach { row ->
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    row.forEach { disease ->
                        DiseaseCard(
                            disease  = disease,
                            onClick  = { onDiseaseClick(disease.id) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // Si la fila tiene solo 1 elemento, rellenar con espacio
                    if (row.size == 1) Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun DiseaseCard(
    disease: Disease,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier  = modifier
            .height(150.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication        = null,
                onClick           = onClick
            ),
        shape     = RoundedCornerShape(24.dp),
        colors    = CardDefaults.cardColors(containerColor = disease.color),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier            = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = disease.emoji, fontSize = 42.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = disease.name,
                style = TextStyle(
                    fontFamily = FredokaFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize   = 18.sp,
                    color      = Color(0xFF333333)
                )
            )
            Text(
                text = disease.tagline,
                style = TextStyle(
                    fontFamily = FredokaFamily,
                    fontSize   = 11.sp,
                    color      = Color(0xFF555555),
                    textAlign  = TextAlign.Center
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewLearnScreen() {
    LearnScreen(onBack = {}, onDiseaseClick = {})
}
