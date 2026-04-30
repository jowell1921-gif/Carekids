package com.example.carekids.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity le dice a Room: "esto es una tabla en SQLite".
// Cada instancia de EmotionalEntry = una fila en esa tabla.
// tableName define el nombre real de la tabla en el archivo .db

@Entity(tableName = "emotional_entries")
data class EmotionalEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,  // Room asigna el ID automáticamente
    val emotionName: String,   // guardamos el nombre del enum: "HAPPY", "SAD", etc.
    val note: String,
    val timestamp: Long        // milisegundos desde epoch — fácil de ordenar y formatear
)
