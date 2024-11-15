package com.example.bookshelfapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BooksViewModel : ViewModel() {
    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    init {
        fetchBooks("bestseller") // Default query to fetch some books on startup
    }

    fun fetchBooks(query: String) {
        viewModelScope.launch {
            try {
                Log.d("BooksViewModel", "Fetching books for query: $query")
                val response = BookRepository.searchBooks(query)
                if (response.isNotEmpty()) {
                    Log.d("BooksViewModel", "Books fetched successfully: ${response.size}")
                    _books.value = response
                } else {
                    Log.d("BooksViewModel", "No books found")
                    _books.value = emptyList()
                }
            } catch (e: Exception) {
                Log.e("BooksViewModel", "Error fetching books", e)
                _books.value = emptyList()
            }
        }
    }
}
