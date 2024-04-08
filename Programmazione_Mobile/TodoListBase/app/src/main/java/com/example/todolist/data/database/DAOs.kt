package com.example.todolist.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TodosDAO {
    @Query("SELECT * FROM todo")
    fun getAll(): Flow<List<Todo>>

    @Upsert
    suspend fun upsert(todo: Todo) //inserimento e modifica in un'unica funzione

    @Delete
    suspend fun delete(todo: Todo)
}
