package com.to.reqom

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class searchact : AppCompatActivity() {
    private lateinit var combinedAdapter: CombinedAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchact)
        val intent = intent
        try {
            this.supportActionBar!!.hide()
        } // catch block to handle NullPointerException
        catch (e: NullPointerException) {
        }
        window.statusBarColor = ContextCompat.getColor(this, R.color.redu)
        val movieList: List<Movie> = intent.getSerializableExtra("movieList") as List<Movie>
        var combinedList = movieList
        val recycle = findViewById<RecyclerView>(R.id.my_recycler_view)
        combinedAdapter = CombinedAdapter(combinedList){se ->
            val intp = Intent(this , detailact::class.java)
            intp.putExtra("id" , se)
            startActivity(intp)
        }
        val bacbut = findViewById<ImageButton>(R.id.imbinsear)
        bacbut.setOnClickListener(){
            finish()
        }
        recycle.layoutManager = GridLayoutManager(this, 2)
        recycle.adapter = combinedAdapter
        val svv = findViewById<SearchView>(R.id.searchView2)
        svv.requestFocus()
        val tvv = findViewById<TextView>(R.id.norefound)
        svv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchTerm ->
                    svv.clearFocus()
                    val filteredMovies = filterByWord(movieList, searchTerm)
                    combinedList = filteredMovies
                    if(combinedList.isEmpty()){
                        tvv.visibility = View.VISIBLE
                    }
                    else{
                        tvv.visibility = View.GONE
                    }
                    combinedAdapter.submitList(combinedList)
                }

                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean{
                return true
            }
        })
    }
    fun filterByWord(movies: List<Movie>, searchTerm: String): List<Movie> {
        return movies.filter { movie ->
            movie.title.contains(searchTerm, ignoreCase = true)
        }
    }
}