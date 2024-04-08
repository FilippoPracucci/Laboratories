package com.example.todolist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.database.Todo
import com.example.todolist.data.repositories.TodosRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TodosState(val todos: List<Todo>)

interface TodosActions {
    fun addTodo(todo: Todo): Job
    fun removeTodo(todo: Todo): Job
    fun toggleComplete(todo: Todo): Job
}

class TodosViewModel(
    private val repository: TodosRepository
) : ViewModel() {
    val state = repository.todos.map { TodosState(it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = TodosState(emptyList())
    )

    val actions = object : TodosActions {
        override fun addTodo(todo: Todo) = viewModelScope.launch {
            repository.upsert(todo)
        }

        override fun removeTodo(todo: Todo) = viewModelScope.launch {
            repository.delete(todo)
        }

        override fun toggleComplete(todo: Todo) = viewModelScope.launch {
            repository.upsert(todo.copy(isComplete = !todo.isComplete))
        }
    }
}
