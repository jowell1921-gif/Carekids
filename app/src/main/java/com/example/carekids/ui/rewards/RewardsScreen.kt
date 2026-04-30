package com.example.carekids.ui.rewards

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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

// ── Punto de entrada ──────────────────────────────────────────────────────────

@Composable
fun RewardsScreen(
    onBack: () -> Unit,
    viewModel: RewardsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    RewardsContent(uiState = uiState, onBack = onBack)
}

// ── UI stateless ──────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RewardsContent(
    uiState: RewardsUiState,
    onBack: () -> Unit
) {
    val unlockedCount = uiState.badges.count { it.unlocked }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "¡Mis premios!",
                        fontFamily = FredokaFamily,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CareKidsBlue)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            PointsBanner(
                carePoints     = uiState.carePoints,
                unlockedCount  = unlockedCount,
                totalCount     = uiState.badges.size
            )
            ProgressSection(unlocked = unlockedCount, total = uiState.badges.size)

            if (uiState.badges.isEmpty()) {
                EmptyState(modifier = Modifier.weight(1f))
            } else {
                LazyVerticalGrid(
                    columns             = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding      = PaddingValues(bottom = 24.dp),
                    modifier            = Modifier.weight(1f)
                ) {
                    // Primero los desbloqueados, luego los bloqueados
                    val sorted = uiState.badges.sortedByDescending { it.unlocked }
                    items(sorted, key = { it.badge.id }) { badgeState ->
                        BadgeCard(badgeState = badgeState)
                    }
                }
            }
        }
    }
}

// ── Secciones ─────────────────────────────────────────────────────────────────

@Composable
private fun PointsBanner(carePoints: Int, unlockedCount: Int, totalCount: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(CareKidsBlue, CareKidsMint)
                )
            )
            .padding(horizontal = 24.dp, vertical = 18.dp)
    ) {
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "⭐ CarePoints",
                    style = TextStyle(
                        fontFamily = FredokaFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize   = 13.sp,
                        color      = Color(0xFF444444)
                    )
                )
                Text(
                    text = "$carePoints pts",
                    style = TextStyle(
                        fontFamily = FredokaFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize   = 32.sp,
                        color      = Color(0xFF222222)
                    )
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "🏅", fontSize = 32.sp)
                Text(
                    text = "$unlockedCount / $totalCount",
                    style = TextStyle(
                        fontFamily = FredokaFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize   = 16.sp,
                        color      = Color(0xFF333333)
                    )
                )
                Text(
                    text = "badges",
                    style = TextStyle(
                        fontFamily = FredokaFamily,
                        fontSize   = 11.sp,
                        color      = Color(0xFF666666)
                    )
                )
            }
        }
    }
}

@Composable
private fun ProgressSection(unlocked: Int, total: Int) {
    val progress = if (total == 0) 0f else unlocked.toFloat() / total.toFloat()
    val animatedProgress by animateFloatAsState(
        targetValue   = progress,
        animationSpec = tween(800),
        label         = "badgeProgress"
    )
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = if (unlocked == total && total > 0) "¡Colección completa! 🎉"
                   else "Sigue así para desbloquear más badges",
            style = TextStyle(
                fontFamily = FredokaFamily,
                fontWeight = FontWeight.Bold,
                fontSize   = 14.sp,
                color      = Color(0xFF555555)
            )
        )
        LinearProgressIndicator(
            progress      = { animatedProgress },
            modifier      = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(8.dp)),
            color         = CareKidsBlue,
            trackColor    = CareKidsBlue.copy(alpha = 0.2f)
        )
    }
}

@Composable
private fun BadgeCard(badgeState: BadgeUiState) {
    val scale = remember { Animatable(1f) }

    // Animación de entrada solo para badges desbloqueados
    LaunchedEffect(badgeState.unlocked) {
        if (badgeState.unlocked) {
            scale.animateTo(1.08f, tween(200))
            scale.animateTo(1f, tween(200))
        }
    }

    val bgColor = if (badgeState.unlocked) CareKidsYellow else Color(0xFFF0F0F0)
    val contentAlpha = if (badgeState.unlocked) 1f else 0.35f

    Card(
        modifier  = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .scale(scale.value),
        shape     = RoundedCornerShape(20.dp),
        colors    = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (badgeState.unlocked) 4.dp else 1.dp
        )
    ) {
        Column(
            modifier              = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment   = Alignment.CenterHorizontally,
            verticalArrangement   = Arrangement.Center
        ) {
            Text(
                text = if (badgeState.unlocked) badgeState.badge.emoji else "🔒",
                fontSize = 34.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = badgeState.badge.name,
                style = TextStyle(
                    fontFamily = FredokaFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize   = 12.sp,
                    color      = Color(0xFF333333).copy(alpha = contentAlpha),
                    textAlign  = TextAlign.Center
                ),
                textAlign = TextAlign.Center,
                maxLines  = 1
            )
            Text(
                text = badgeState.badge.description,
                style = TextStyle(
                    fontFamily = FredokaFamily,
                    fontSize   = 10.sp,
                    color      = Color(0xFF666666).copy(alpha = contentAlpha),
                    textAlign  = TextAlign.Center
                ),
                textAlign = TextAlign.Center,
                maxLines  = 2
            )
        }
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier            = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "🎁", fontSize = 56.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "¡Registra cómo te sientes\npara ganar tu primer badge!",
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

// ── Preview ───────────────────────────────────────────────────────────────────

@Preview(showSystemUi = true)
@Composable
private fun PreviewRewardsContent() {
    val fakeBadges = allBadges.mapIndexed { i, b -> BadgeUiState(b, i % 2 == 0) }
    RewardsContent(
        uiState = RewardsUiState(badges = fakeBadges, carePoints = 75, totalEntries = 5),
        onBack  = {}
    )
}
