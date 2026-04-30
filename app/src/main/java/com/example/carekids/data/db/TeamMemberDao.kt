package com.example.carekids.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamMemberDao {
    @Insert
    suspend fun insert(member: TeamMember)

    @Delete
    suspend fun delete(member: TeamMember)

    @Query("SELECT * FROM team_members ORDER BY role ASC")
    fun getAllMembers(): Flow<List<TeamMember>>
}
