package com.to.reqom

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.io.Serializable

class MainActivity : AppCompatActivity(){

    private lateinit var recyclerView: RecyclerView
    private lateinit var rcvfortv: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private var movies = listOf<Movie>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        try {
            this.supportActionBar!!.hide()
        } // catch block to handle NullPointerException
        catch (e: NullPointerException) {
        }
        window.statusBarColor = ContextCompat.getColor(this, R.color.redu)
        val sb = findViewById<TextView>(R.id.searchView)
        recyclerView = findViewById(R.id.recyclerView)
        movieAdapter = MovieAdapter(listOf<Movie>()){sele ->
            val intp = Intent(this , detailact::class.java)
            intp.putExtra("id" , sele.id)
            intp.putExtra("what" , "movie")
            startActivity(intp)
        }
        recyclerView.layoutManager = GridLayoutManager(this , 2)
        recyclerView.adapter = movieAdapter
        sb.setOnClickListener() {
            val inte  = Intent(this, searchact::class.java)
            inte.putExtra("movieList" , movies as Serializable)
            startActivity(inte)
        }
        GlobalScope.launch(Dispatchers.IO) {
            val client = OkHttpClient()

            val request = Request.Builder()
                .url("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjNTcyMjZhOWZjZjkzNDdkMTBhNWIwNDJlNjBiNjBkMyIsInN1YiI6IjY1Yjg3NzQ1ZGE5ZWYyMDE0OWE1MDcyMCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.x1ObBmTcV4M7mqsQYU8LydCOa2thaB98fUHDfesdKHU")
                .build()

            try {
                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val jsonString = response.body?.string()

                    launch(Dispatchers.Main) {
                        val movieResponse = Gson().fromJson(jsonString, MovieResponse::class.java)
                        movies = movieResponse.results
                        movieAdapter.submitList(movieResponse.results)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}