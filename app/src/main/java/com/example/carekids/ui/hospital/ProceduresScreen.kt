package com.example.carekids.ui.hospital

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carekids.data.model.Procedure
import com.example.carekids.data.model.allProcedures
import com.example.carekids.ui.theme.CareKidsBlue
import com.example.carekids.ui.theme.CareKidsLightPink
import com.example.carekids.ui.theme.FredokaFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProceduresScreen(
    onBack: () -> Unit,
    onProcedureClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("¿Qué va a pasar hoy?", fontFamily = FredokaFamily, fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "Volver") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CareKidsLightPink)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier       = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Toca el procedimiento para ver qué va a pasar paso a paso 👇",
                    style = TextStyle(fontFamily = FredokaFamily, fontSize = 14.sp, color = Color(0xFF666666))
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            items(allProcedures, key = { it.id }) { procedure ->
                ProcedureCard(procedure = procedure, onClick = { onProcedureClick(procedure.id) })
            }
        }
    }
}

@Composable
private fun ProcedureCard(procedure: Procedure, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(CareKidsBlue.copy(alpha = 0.2f))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication        = null,
                onClick           = onClick
            )
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(text = procedure.emoji, fontSize = 36.sp)
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text  = procedure.name,
                style = TextStyle(fontFamily = FredokaFamily, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color(0xFF222222))
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                InfoChip(text = "⏱ ${procedure.duration}")
                InfoChip(text = "💊 ${procedure.painLevel}")
            }
        }
        Text(text = "›", style = TextStyle(fontSize = 24.sp, color = Color(0xFF888888), fontWeight = FontWeight.Bold))
    }
}

@Composable
private fun InfoChip(text: String) {
    Text(
        text  = text,
        style = TextStyle(fontFamily = FredokaFamily, fontSize = 11.sp, color = Color(0xFF555555))
    )
}
