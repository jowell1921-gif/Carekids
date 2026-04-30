package com.example.carekids.ui.hospital

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carekids.ui.theme.CareKidsBlue
import com.example.carekids.ui.theme.CareKidsLightPink
import com.example.carekids.ui.theme.CareKidsMint
import com.example.carekids.ui.theme.CareKidsYellow
import com.example.carekids.ui.theme.FredokaFamily

private data class PatientRight(
    val emoji: String,
    val title: String,
    val description: String
)

private val patientRights = listOf(
    PatientRight("🏠", "Estar con tu familia",
        "Tienes derecho a que tus padres o familiares estén contigo en el hospital todo el tiempo que necesites."),
    PatientRight("📋", "Que te lo expliquen todo",
        "Tienes derecho a que los médicos te expliquen, en palabras que entiendas, qué te pasa y qué van a hacerte."),
    PatientRight("💊", "No sufrir dolor",
        "Tienes derecho a recibir medicación para que no sientas dolor innecesario. ¡Díselo siempre al médico si te duele algo!"),
    PatientRight("🎮", "Jugar y divertirte",
        "Tienes derecho a jugar, descansar y hacer actividades adecuadas para tu edad, incluso estando en el hospital."),
    PatientRight("📚", "Seguir aprendiendo",
        "Tienes derecho a continuar tu educación y no quedarte atrás en el colegio durante tu estancia en el hospital."),
    PatientRight("🔒", "Privacidad e intimidad",
        "Tienes derecho a que respeten tu intimidad durante las exploraciones, curas y tratamientos."),
    PatientRight("❤️", "Respeto y cariño",
        "Tienes derecho a ser tratado siempre con respeto, cariño y comprensión por todas las personas del hospital."),
    PatientRight("🗣️", "Expresarte y ser escuchado",
        "Tienes derecho a decir cómo te sientes, hacer preguntas y que te escuchen. Tu opinión importa."),
    PatientRight("👩‍⚕️", "Personal especializado",
        "Tienes derecho a ser atendido por personas entrenadas especialmente para cuidar a niños y jóvenes."),
    PatientRight("🔄", "Continuidad en tu tratamiento",
        "Tienes derecho a que tu tratamiento sea continuo y bien coordinado entre todos los profesionales que te atienden.")
)

private val cardColors = listOf(
    CareKidsBlue.copy(alpha = 0.25f),
    CareKidsMint.copy(alpha = 0.4f),
    CareKidsYellow.copy(alpha = 0.4f)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RightsScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis derechos", fontFamily = FredokaFamily, fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "Volver") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CareKidsLightPink)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier       = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(CareKidsLightPink)
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text      = "⭐ Como paciente, tienes derechos importantes.\n¡Conócelos y exígelos!",
                        style     = TextStyle(fontFamily = FredokaFamily, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF444444), textAlign = TextAlign.Center),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

            itemsIndexed(patientRights) { index, right ->
                RightCard(right = right, color = cardColors[index % cardColors.size])
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text      = "Basado en la Carta Europea de Derechos del Niño Hospitalizado (EACH Charter)",
                    style     = TextStyle(fontFamily = FredokaFamily, fontSize = 10.sp, color = Color(0xFFAAAAAA), textAlign = TextAlign.Center),
                    textAlign = TextAlign.Center,
                    modifier  = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun RightCard(right: PatientRight, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(color)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment     = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(text = right.emoji, fontSize = 32.sp, modifier = Modifier.padding(top = 2.dp))
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text  = right.title,
                style = TextStyle(fontFamily = FredokaFamily, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF222222))
            )
            Text(
                text  = right.description,
                style = TextStyle(fontFamily = FredokaFamily, fontSize = 13.sp, color = Color(0xFF555555))
            )
        }
    }
}
