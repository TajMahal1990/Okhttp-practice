package com.example.okhttppractice2

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private val networkClient = NetworkClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        textView = findViewById(R.id.TextView)
        fetchData()
    }

    private fun fetchData() {
        CoroutineScope(Dispatchers.IO).launch {
            val jsonResponse = networkClient.fenchData("https://jsonplaceholder.typicode.com/todos/1")
            withContext(Dispatchers.Main) {
                if(::textView.isInitialized) {
                    jsonResponse?.let {
                        val title = it.getString("title")
                        val userID = it.getString("userId")
                        val completed = it.optBoolean("completed")

                        textView.text = """"
                            Title = $title
                            UserID = $userID
                            Completed = $completed
                            """.trimIndent()
                    } ?: run {
                        textView.text = "ERROR"
                    }
                }
            }

        }
    }
}