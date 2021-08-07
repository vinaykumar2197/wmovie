package com.vinay.myapplication.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.vinay.myapplication.R
import com.vinay.myapplication.data.remote.model.Result;
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.row_movie.view.*

class MovieDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val intent = intent
        val item  : Result= intent.getParcelableExtra("result")!!

        if (item.backdropPath != null) {
            Glide
                .with(this)
                .load("http://image.tmdb.org/t/p/w500/" + item.backdropPath!!)
                .into(iv_movie_backdrop);
        }

        if (item.posterPath != null) {
            Glide
                .with(this)
                .load("http://image.tmdb.org/t/p/w185/" + item.posterPath!!)
                .into(iv_movie_poster_thumbnail);
        }

        if(item.title!=null){
            tv_title.text = item.title!!.toString()
        }

        if(item.popularity!=null){
            tv_popular.text = item.popularity!!.toString()
        }

        if(item.voteCount!=null){
            tv_count.text = item.voteCount!!.toString()
        }

        if(item.voteAverage!=null){
            tv_rating.text = item.voteAverage!!.toString()
        }

        if(item.releaseDate!=null){
            tv_date_released.text = item.releaseDate!!.toString()
        }

        if(item.overview!=null){
            tv_overview.text = item.overview!!.toString()
        }

    }
}