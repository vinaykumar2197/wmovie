package com.vinay.myapplication.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vinay.myapplication.R
import com.vinay.myapplication.adapter.PrepAdapter
import com.vinay.myapplication.base.BaseActivity
import com.vinay.myapplication.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import com.vinay.myapplication.data.remote.model.Result;


class MainActivity : BaseActivity() {

    lateinit var adapter: PrepAdapter

    lateinit var  viewModel: MainActivityViewModel

    private lateinit var topRatedMoviesLayoutMgr: LinearLayoutManager

    private var mLoading = false

    private  var resultList :  ArrayList<Result> = ArrayList<Result>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setDialog(true, true)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        adapter = PrepAdapter()

        topRatedMoviesLayoutMgr = LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        )
        recyclerView.layoutManager = topRatedMoviesLayoutMgr

//        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.adapter = adapter


//        val snapHelper: SnapHelper = PagerSnapHelper()
//        snapHelper.attachToRecyclerView(recyclerView)

        viewModel.getLivePrepData(viewModel.topRatedMoviesPage)

        viewModel.getPrepStatus().observe(this, Observer {
//            if (it.s!!){
//                Timber.e("data inserted successfully")
            resultList.addAll(it.results)
            mLoading = false
            progress_view.visibility = View.GONE
            setDialog(false, true)
            viewModel.topRatedMoviesMaxPage = it.totalPages!!

//                userSharedPrefs.setCurrentDate(it.curTime)

            adapter.submitList(resultList)

//            }
//            else{
////                Timber.e("Not yet")
//            }
        })
        attachTopRatedMoviesOnScrollListener()
    }


    private fun attachTopRatedMoviesOnScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItem: Int = topRatedMoviesLayoutMgr.getItemCount()
                val lastVisibleItem: Int = topRatedMoviesLayoutMgr.findLastVisibleItemPosition()

                if (!mLoading && lastVisibleItem == totalItem - 1) {
                    progress_view.visibility = View.VISIBLE
                    mLoading = true
                    if(viewModel.topRatedMoviesPage<viewModel.topRatedMoviesMaxPage){
                        viewModel.topRatedMoviesPage = viewModel.topRatedMoviesPage +1
                        viewModel.getLivePrepData(viewModel.topRatedMoviesPage)
                    }
                    // Scrolled to bottom. Do something here.
                }
            }
        })
    }

    override fun onInternetAvailable() {
        viewModel.getLivePrepData(viewModel.topRatedMoviesPage)
    }


}
