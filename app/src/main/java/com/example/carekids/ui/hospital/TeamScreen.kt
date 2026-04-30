package com.example.carekids.ui.hospital

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
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
import com.example.carekids.data.db.TeamMember
import com.example.carekids.ui.theme.CareKidsBlue
import com.example.carekids.ui.theme.CareKidsLightPink
import com.example.carekids.ui.theme.CareKidsMint
import com.example.carekids.ui.theme.FredokaFamily

@Composable
fun TeamScreen(
    onBack: () -> Unit,
    viewModel: TeamViewModel = viewModel()
) {
    val members     by viewModel.members.collectAsStateWithLifecycle()
    val dialogState by viewModel.dialogState.collectAsStateWithLifecycle()
    val (showDialog, nameInput, roleIndex) = dialogState

    TeamContent(
        members        = members,
        onBack         = onBack,
        onAddClick     = viewModel::openDialog,
        onDeleteClick  = viewModel::deleteMember
    )

    if (showDialog) {
        AddMemberDialog(
            nameInput      = nameInput,
            selectedIndex  = roleIndex,
            onNameChanged  = viewModel::onNameChanged,
            onRoleSelected = viewModel::onRoleSelected,
            onConfirm      = viewModel::saveMember,
            onDismiss      = viewModel::closeDialog
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TeamContent(
    members: List<TeamMember>,
    onBack: () -> Unit,
    onAddClick: () -> Unit,
    onDeleteClick: (TeamMember) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi equipo médico", fontFamily = FredokaFamily, fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "Volver") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CareKidsLightPink)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick           = onAddClick,
                containerColor    = CareKidsBlue,
                contentColor      = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Añadir miembro")
            }
        }
    ) { padding ->
        if (members.isEmpty()) {
            EmptyTeamState(modifier = Modifier.fillMaxSize().padding(padding))
        } else {
            LazyColumn(
                modifier       = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Text(
                        "Estas son las personas que te cuidan 💙",
                        style = TextStyle(fontFamily = FredokaFamily, fontSize = 14.sp, color = Color(0xFF666666))
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
                items(members, key = { it.id }) { member ->
                    MemberCard(member = member, onDelete = { onDeleteClick(member) })
                }
                item { Spacer(modifier = Modifier.height(72.dp)) } // espacio para el FAB
            }
        }
    }
}

@Composable
private fun MemberCard(member: TeamMember, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(CareKidsMint.copy(alpha = 0.5f))
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = member.emoji, fontSize = 36.sp)
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text  = member.name,
                style = TextStyle(fontFamily = FredokaFamily, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color(0xFF222222))
            )
            Text(
                text  = member.role,
                style = TextStyle(fontFamily = FredokaFamily, fontSize = 13.sp, color = Color(0xFF555555))
            )
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Filled.Delete, contentDescription = "Eliminar", tint = Color(0xFFBBBBBB))
        }
    }
}

@Composable
private fun EmptyTeamState(modifier: Modifier = Modifier) {
    Column(
        modifier            = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("👩‍⚕️", fontSize = 56.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text      = "Aún no has añadido\na nadie de tu equipo",
            style     = TextStyle(fontFamily = FredokaFamily, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF888888), textAlign = TextAlign.Center),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text      = "Pulsa el botón + para añadir\na tu médico, enfermera...",
            style     = TextStyle(fontFamily = FredokaFamily, fontSize = 14.sp, color = Color(0xFFAAAAAA), textAlign = TextAlign.Center),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun AddMemberDialog(
    nameInput: String,
    selectedIndex: Int,
    onNameChanged: (String) -> Unit,
    onRoleSelected: (Int) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Añadir a mi equipo", fontFamily = FredokaFamily, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value         = nameInput,
                    onValueChange = onNameChanged,
                    placeholder   = { Text("Nombre...", fontFamily = FredokaFamily) },
                    singleLine    = true,
                    shape         = RoundedCornerShape(12.dp),
                    modifier      = Modifier.fillMaxWidth(),
                    textStyle     = TextStyle(fontFamily = FredokaFamily, fontSize = 15.sp),
                    colors        = OutlinedTextFieldDefaults.colors(focusedBorderColor = CareKidsBlue)
                )

                Text("Rol:", style = TextStyle(fontFamily = FredokaFamily, fontWeight = FontWeight.Bold, fontSize = 14.sp))

                // Selector de roles en grid 2 columnas
                val rows = teamRoles.chunked(2)
                rows.forEachIndexed { rowIndex, row ->
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        row.forEachIndexed { colIndex, (role, emoji) ->
                            val index = rowIndex * 2 + colIndex
                            val selected = index == selectedIndex
                            FilterChip(
                                selected = selected,
                                onClick  = { onRoleSelected(index) },
                                label    = { Text("$emoji $role", fontFamily = FredokaFamily, fontSize = 12.sp) },
                                modifier = Modifier.weight(1f),
                                colors   = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = CareKidsBlue.copy(alpha = 0.3f)
                                )
                            )
                        }
                        if (row.size == 1) Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick  = onConfirm,
                enabled  = nameInput.isNotBlank(),
                colors   = ButtonDefaults.buttonColors(containerColor = CareKidsBlue)
            ) {
                Text("Añadir", fontFamily = FredokaFamily, fontWeight = FontWeight.Bold, color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", fontFamily = FredokaFamily, color = Color(0xFF888888))
            }
        },
        shape = RoundedCornerShape(20.dp)
    )
}
