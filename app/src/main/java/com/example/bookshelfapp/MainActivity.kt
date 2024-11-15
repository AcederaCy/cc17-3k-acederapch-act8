package com.example.bookshelfapp

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookshelfapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: BooksViewModel by viewModels()

    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Initialize the adapter with an empty list initially
        bookAdapter = BookAdapter(emptyList())
        recyclerView.adapter = bookAdapter

        // Observe the books LiveData and update the RecyclerView when books are fetched
        viewModel.books.observe(this) { books ->
            if (books.isNotEmpty()) {
                bookAdapter.books = books // Update the book list in the adapter
                bookAdapter.notifyDataSetChanged() // Notify that the data has changed
                Log.d("MainActivity", "Books displayed: ${books.size}")
            } else {
                Log.d("MainActivity", "No books to display")
            }
        }

        // Search functionality
        binding.searchButton.setOnClickListener {
            val query = binding.searchEditText.text.toString()
            if (query.isNotEmpty()) {
                Log.d("MainActivity", "Search query: $query")
                viewModel.fetchBooks(query)
            }
        }
    }
}
