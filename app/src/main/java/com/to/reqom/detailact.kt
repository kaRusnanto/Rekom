package com.to.reqom

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class detailact : AppCompatActivity() {
    private lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailact)
        try {
            this.supportActionBar!!.hide()
        } // catch block to handle NullPointerException
        catch (e: NullPointerException) {
        }
        window.statusBarColor = ContextCompat.getColor(this, R.color.redu)
        val i = intent.getIntExtra("id" , 0)
        val bacbut = findViewById<ImageButton>(R.id.imbindet)
        bacbut.setOnClickListener(){
            finish()
        }
        GlobalScope.launch(Dispatchers.IO) {
            val client = OkHttpClient()
            val ull = "https://api.themoviedb.org/3/movie/$i?language=en-US"
            Log.d("Tag" , "$ull")
            val request = Request.Builder()
                .url(ull)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjNTcyMjZhOWZjZjkzNDdkMTBhNWIwNDJlNjBiNjBkMyIsInN1YiI6IjY1Yjg3NzQ1ZGE5ZWYyMDE0OWE1MDcyMCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.x1ObBmTcV4M7mqsQYU8LydCOa2thaB98fUHDfesdKHU")
                .build()

            try {
                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val jsonString = response.body?.string()

                    launch(Dispatchers.Main) {
                        val movieDetails = Gson().fromJson(jsonString, MovieDetails::class.java)
                        val aa = "https://image.tmdb.org/t/p/w500"
                        val imageUrls = mutableListOf<String>()
                        movieDetails.backdropPath?.let {
                            imageUrls.add(aa + it)
                        }

                        movieDetails.posterPath?.let {
                            imageUrls.add(aa + it)
                        }

                        movieDetails.collection?.backdropPath?.let {
                            imageUrls.add(aa + it)
                        }

                        movieDetails.collection?.posterPath?.let {
                            imageUrls.add(aa + it)
                        }
                        val adap = ViewPagerAdapter(this@detailact , imageUrls)
                        viewPager.adapter = adap
                        val dq = findViewById<TextView>(R.id.details)
                        dq.text = "Details of "+movieDetails.originalTitle
                        val picfound = findViewById<TextView>(R.id.picturesfound)
                        picfound.text = "${imageUrls.size} Images found"
                        val selectedFields = """
                        Budget: $${movieDetails.budget}
                        
                        Genres: ${movieDetails.genres?.joinToString(", ") { it.name.orEmpty() }}
                        
                        Original Title: ${movieDetails.originalTitle}
                        
                        Overview: ${movieDetails.overview}
                        
                        Production Companies: ${movieDetails.productionCompanies?.joinToString(", ") { it.name.orEmpty() }}
                        
                        Release Date: ${movieDetails.releaseDate}
                        
                        Revenue: $${movieDetails.revenue}
                        
                        Runtime: ${movieDetails.runtime} Minutes
                        
                        Status: ${movieDetails.status}
                        
                        Vote Average: ${movieDetails.voteAverage}
                        
                        Vote Count: ${movieDetails.voteCount}
                        
                        """.trimIndent()
                        val hello = findViewById<TextView>(R.id.hello)


                        hello.text = selectedFields
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        viewPager = findViewById(R.id.idViewPager)
    }
}