package com.example.carekids.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// @Dao = Data Access Object. Es una interfaz: tú declaras QUÉ quieres hacer,
// Room genera el código SQL real en tiempo de compilación (por eso necesita KSP).

@Dao
interface EmotionalDao {

    // suspend: operación única que se ejecuta en una corrutina y termina.
    @Insert
    suspend fun insert(entry: EmotionalEntry)

    // Flow<List<...>>: Room observa la tabla y emite una nueva lista
    // cada vez que hay un INSERT o DELETE. No es suspend porque no termina —
    // es un stream continuo que la UI observa mientras está activa.
    @Query("SELECT * FROM emotional_entries ORDER BY timestamp DESC")
    fun getAllEntries(): Flow<List<EmotionalEntry>>
}
