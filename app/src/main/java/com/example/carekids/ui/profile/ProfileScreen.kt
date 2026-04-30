package com.example.carekids.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.carekids.ui.theme.CareKidsTheme
import com.example.carekids.ui.theme.CareKidsYellow
import com.example.carekids.ui.theme.CareKidsLightPink
import com.example.carekids.ui.theme.CareKidsBlue
import com.example.carekids.ui.theme.CareKidsMint
import com.example.carekids.ui.theme.FredokaFamily

// ── Punto de entrada (conoce el ViewModel) ────────────────────────────────────

@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Cuando el perfil se guarda, volvemos atrás automáticamente
    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) onBack()
    }

    ProfileContent(
        uiState          = uiState,
        onBack           = onBack,
        onNameChanged    = viewModel::onNameChanged,
        onAgeIncreased   = viewModel::onAgeIncreased,
        onAgeDecreased   = viewModel::onAgeDecreased,
        onAvatarSelected = viewModel::onAvatarSelected,
        onPetSelected    = viewModel::onPetSelected,
        onSave           = viewModel::saveProfile
    )
}

// ── UI stateless ──────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    uiState: ProfileUiState,
    onBack: () -> Unit,
    onNameChanged: (String) -> Unit,
    onAgeIncreased: () -> Unit,
    onAgeDecreased: () -> Unit,
    onAvatarSelected: (Int) -> Unit,
    onPetSelected: (Int) -> Unit,
    onSave: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "¡Mi perfil de superhéroe!",
                        fontFamily = FredokaFamily,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CareKidsLightPink
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            NameSection(name = uiState.name, onNameChanged = onNameChanged)
            AgeSection(
                age = uiState.age,
                onIncrease = onAgeIncreased,
                onDecrease = onAgeDecreased
            )
            AvatarSection(
                avatars = avatarOptions,
                selectedIndex = uiState.selectedAvatarIndex,
                onSelected = onAvatarSelected
            )
            PetSection(
                pets = petOptions,
                selectedIndex = uiState.selectedPetIndex,
                onSelected = onPetSelected
            )
            SaveButton(
                enabled = uiState.name.isNotBlank(),
                onClick = onSave
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ── Secciones ─────────────────────────────────────────────────────────────────

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = TextStyle(
            fontFamily = FredokaFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color(0xFF444444)
        )
    )
}

@Composable
private fun NameSection(name: String, onNameChanged: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        SectionTitle("¿Cómo te llamas?")
        OutlinedTextField(
            value = name,
            onValueChange = onNameChanged,
            placeholder = {
                Text("Escribe tu nombre...", fontFamily = FredokaFamily)
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(fontFamily = FredokaFamily, fontSize = 16.sp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CareKidsBlue,
                unfocusedBorderColor = CareKidsBlue.copy(alpha = 0.5f)
            )
        )
    }
}

@Composable
private fun AgeSection(age: Int, onIncrease: () -> Unit, onDecrease: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        SectionTitle("¿Cuántos años tienes?")
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(CareKidsYellow.copy(alpha = 0.4f))
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            AgeButton(label = "−", onClick = onDecrease)
            Text(
                text = "$age",
                style = TextStyle(
                    fontFamily = FredokaFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = Color(0xFF333333)
                ),
                modifier = Modifier.width(48.dp),
                textAlign = TextAlign.Center
            )
            AgeButton(label = "+", onClick = onIncrease)
        }
    }
}

@Composable
private fun AgeButton(label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(CareKidsBlue)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontFamily = FredokaFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.White
            )
        )
    }
}

@Composable
private fun AvatarSection(
    avatars: List<AvatarOption>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionTitle("Elige tu superhéroe")
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            avatars.forEachIndexed { index, avatar ->
                val isSelected = index == selectedIndex
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) CareKidsBlue else Color(0xFFEEEEEE))
                            .border(
                                width = if (isSelected) 3.dp else 0.dp,
                                color = if (isSelected) Color(0xFF1565C0) else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { onSelected(index) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = avatar.emoji, fontSize = 24.sp)
                    }
                    Text(
                        text = avatar.label,
                        fontSize = 9.sp,
                        fontFamily = FredokaFamily,
                        textAlign = TextAlign.Center,
                        color = Color(0xFF555555),
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
private fun PetSection(
    pets: List<PetOption>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionTitle("Elige tu mascota")
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            pets.forEachIndexed { index, pet ->
                val isSelected = index == selectedIndex
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(80.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onSelected(index) },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) CareKidsMint else Color(0xFFEEEEEE)
                    ),
                    border = if (isSelected)
                        CardDefaults.outlinedCardBorder()
                    else null,
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = if (isSelected) 6.dp else 2.dp
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = pet.emoji, fontSize = 28.sp)
                        Text(
                            text = pet.name,
                            fontFamily = FredokaFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = Color(0xFF333333)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SaveButton(enabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = CareKidsBlue,
            disabledContainerColor = Color(0xFFCCCCCC)
        )
    ) {
        Text(
            text = "¡Guardar mi perfil!",
            style = TextStyle(
                fontFamily = FredokaFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
            )
        )
    }
}

// ── Preview ───────────────────────────────────────────────────────────────────

@Preview(showSystemUi = true)
@Composable
private fun PreviewProfileContent() {
    CareKidsTheme {
        ProfileContent(
            uiState          = ProfileUiState(name = "Lucía", age = 9, selectedAvatarIndex = 2, selectedPetIndex = 1),
            onBack           = {},
            onNameChanged    = {},
            onAgeIncreased   = {},
            onAgeDecreased   = {},
            onAvatarSelected = {},
            onPetSelected    = {},
            onSave           = {}
        )
    }
}
