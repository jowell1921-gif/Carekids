package com.example.carekids

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.carekids.ui.theme.CareKidsTheme
import com.example.carekids.ui.theme.CareKidsTypography

/**
 * Project: CareKids
 * From: com.example.carekids
 * Created by: Joel Arturo Osorio
 * On: 05/12/2025 at 11: 55
 * All rights reserved 2025.
 */

@Preview(showBackground = true)
@Composable
fun DialogPreview() {
    CareKidsTheme {
        DialogScreen()
    }
}


@Composable
fun DialogScreen() {
    Scaffold { padding ->
        DialogView(
            modifier = Modifier.padding(padding)
        )
    }
}


@Composable
fun DialogView(modifier: Modifier = Modifier) {
    Text(
        text = "Mis recompensas",
        style = CareKidsTypography.headlineSmall,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

