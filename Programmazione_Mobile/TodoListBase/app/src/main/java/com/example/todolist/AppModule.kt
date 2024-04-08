package com.example.todolist

import androidx.room.Room
import com.example.todolist.data.database.TodoListDatabase
import com.example.todolist.data.repositories.TodosRepository
import com.example.todolist.ui.TodosViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
     single {
          Room.databaseBuilder(
               get(),
               TodoListDatabase::class.java,
               "todo-list"
          ).build()
     }

     single { TodosRepository(get<TodoListDatabase>().todosDAO()) }

     viewModel { TodosViewModel(get()) }
}
