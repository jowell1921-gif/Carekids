package com.example.carekids

import android.R.attr.contentDescription
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.carekids.ui.theme.CareKidsTheme
import kotlin.jvm.java


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CareKidsTheme {
                ContentMain(onProfileClick = {
                    startActivity(Intent(this, ProfileActivity::class.java))
                },
                    onDialogClick = {
                    startActivity(Intent(this, DialogActivity::class.java))
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentMain(onProfileClick: () -> Unit,
                onDialogClick: () -> Unit) {
    val context = LocalContext.current
    val colorBar = context.getColor(R.color.carekids_blue)
    var selectedText by remember  { mutableStateOf("") }
    var isExpanded by remember {mutableStateOf(false)}
    val items = listOf("Mi perfil", "Conócenos", "Ayuda", "Cuando sea grande")

    Scaffold(
    ) { innerPadding ->
        ContentView(
            modifier = Modifier
                .padding(innerPadding),
            onProfileClick = onProfileClick,
            onDialogClick = onDialogClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CareKidsTheme{
        ContentMain(onProfileClick = {},
            onDialogClick = {})
    }
}