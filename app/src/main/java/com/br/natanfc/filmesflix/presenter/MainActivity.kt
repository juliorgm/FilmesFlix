package com.br.natanfc.filmesflix.presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.br.natanfc.filmesflix.R
import com.br.natanfc.filmesflix.domain.Movie
import com.br.natanfc.filmesflix.framework.viewmodel.MovieListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var movieListViewModel: MovieListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieListViewModel = ViewModelProvider.NewInstanceFactory().create(MovieListViewModel::class.java)
        movieListViewModel.init()
        initObserver()
        loadingVisibility(true)
    }

    private fun initObserver(){
        movieListViewModel.moviesList.observe(this,{
            populateList(it)
            if (it.isNotEmpty()){
                populateList(it)
                loadingVisibility(false)
            }
        })
    }

    private fun populateList(list: List<Movie>){
        moviesList.apply {
            layoutManager = LinearLayoutManager(context)
            hasFixedSize()
            adapter= MoviesAdapter(list)
        }
    }

    private fun loadingVisibility(isLoading: Boolean){
        progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }
}