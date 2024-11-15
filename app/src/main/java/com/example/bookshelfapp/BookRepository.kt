package com.example.bookshelfapp

import android.util.Log

object BookRepository {
    private val apiService = RetrofitInstance.createApiService()

    suspend fun searchBooks(query: String): List<Book> {
        try {
            val response = apiService.searchBooks(query)
            if (response.items != null) {
                Log.d("BookRepository", "Books found: ${response.items.size}")
                return response.items
            } else {
                Log.e("BookRepository", "No books found for query: $query")
                return emptyList()
            }
        } catch (e: Exception) {
            Log.e("BookRepository", "Error fetching books", e)
            return emptyList()
        }
    }
}
