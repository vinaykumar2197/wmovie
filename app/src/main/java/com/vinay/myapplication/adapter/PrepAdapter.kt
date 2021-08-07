package com.vinay.myapplication.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.vinay.myapplication.R
import com.vinay.myapplication.local.UserSharedPrefs

import kotlinx.android.synthetic.main.row_movie.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

import com.vinay.myapplication.data.remote.model.Result;
import com.vinay.myapplication.ui.MovieDetailActivity


class PrepAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BotViewHolder) {
            return holder.bind(position)
        }
    }

    //    RecyclerView.Adapter<BotListRecyclerAdapter.BotViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BotViewHolder {
        //To change body of created functions use File | Settings | File Templates.
        return BotViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_movie,
                parent,
                false
            ), parent.context
        )
    }


    inner class BotViewHolder(itemView: View, context: Context) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(posiiton: Int) {
            val item = getItem(posiiton)

            itemView.tv_movie_name.text =if(item.originalTitle!=null) item.originalTitle!! else ""
            itemView.tv_release_year.text = if(item.releaseDate!=null) item.releaseDate!! else ""
            itemView.tv_rating.text = if(item.voteAverage!=null) item.voteAverage!!.toString() else ""
            itemView.tv_overview.text = if(item.overview!=null) item.overview!! else ""

            itemView.rl_root.setOnClickListener(View.OnClickListener {
               var intent : Intent = Intent(it.context,MovieDetailActivity::class.java)
                intent.putExtra("result",item)
               it.context.startActivity(intent)
            });


//            https://image.tmdb.org/t/p/w185/2DtPSyODKWXluIRV7PVru0SSzja.jpg
            if (item.posterPath != null) {
                Glide
                        .with(itemView.context)
                        .load("http://image.tmdb.org/t/p/w185/" + item.posterPath!!)
                        .into(itemView.iv_movie);
            }
        }
    }


    val arrayList = ArrayList<Result>()
    var word : String = ""


    fun submitList(list: List<Result> ) {
        val diffUtil = DiffUtil.calculateDiff(PrepAdapter.DiffUtils(list, arrayList))
        diffUtil.dispatchUpdatesTo(this)
        arrayList.clear()
        arrayList.addAll(list)
//        notifyDataSetChanged()
    }

    fun getItem(position: Int): Result {
        return arrayList.get(position)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }



    class DiffUtils(val newList: List<Result> , val oldList: ArrayList<Result>) :
        DiffUtil.Callback() {
        val currentGson = Gson()
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return newList.get(newItemPosition) == oldList.get(oldItemPosition)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val result =
                currentGson.toJson(newList.get(newItemPosition))
                    .compareTo(currentGson.toJson(oldList.get(oldItemPosition)))
            if (result == 0) {
                return true
            }
            return false
        }

        override fun getOldListSize(): Int {
            return if (oldList != null) oldList.size else 0
        }

        override fun getNewListSize(): Int {
            return if (newList != null) newList.size else 0
        }

    }

}
