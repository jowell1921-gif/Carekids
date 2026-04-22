package com.example.carekids.data.model

import androidx.compose.ui.graphics.Color

/**
 * Project: CareKids
 * From: com.example.carekids.data.model
 * Created by: Joel Arturo Osorio
 * On: 20/04/2026 at 17: 14
 * All rights reserved 2026.
 */
// ── Modelo ───────────────────────────────────────────────────────────────────

data class NewsItem(
    val hospital: String,
    val title: String,
    val imageUrl: String,
    val accentColor: Color
)
