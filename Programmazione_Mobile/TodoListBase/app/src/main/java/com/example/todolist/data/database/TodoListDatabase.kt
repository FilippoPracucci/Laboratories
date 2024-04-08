package com.example.todolist.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Todo::class], version = 1)
abstract class TodoListDatabase : RoomDatabase() {
    //astratta perché è Room che gestisce l'implementazione
    abstract fun todosDAO(): TodosDAO
}