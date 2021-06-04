package com.br.natanfc.filmesflix.framework.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.br.natanfc.filmesflix.framework.api.MovieRestApiTask
import com.br.natanfc.filmesflix.data.MovieRepository
import com.br.natanfc.filmesflix.domain.Movie
import com.br.natanfc.filmesflix.implamentations.MovieDataSourceImplamentation
import com.br.natanfc.filmesflix.usecase.MovieListUseCase
import java.lang.Exception

class MovieListViewModel : ViewModel() {

    companion object{
        const val TAG = "MovieListViewModel"
    }

    private val moviesRestApiTask = MovieRestApiTask()
    private val movieDataSource = MovieDataSourceImplamentation(moviesRestApiTask)
    private val movieRepository = MovieRepository(movieDataSource)
    private val movieListUseCase = MovieListUseCase(movieRepository)

    private var _movieList = MutableLiveData<List<Movie>>()
    val moviesList :LiveData<List<Movie>>
    get() = _movieList

    fun init(){
        getAllMoviews()
    }

    private fun getAllMoviews() {
        Thread{
            try {
                val allMovies = movieListUseCase.invoke()
                _movieList.postValue(allMovies)
            }catch (exception: Exception){
                Log.d(TAG,exception.message.toString())
            }
        }.start()

    }
}