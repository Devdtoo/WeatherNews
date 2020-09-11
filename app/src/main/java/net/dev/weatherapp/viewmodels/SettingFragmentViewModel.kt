package net.dev.weatherapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import net.dev.weatherapp.SwipeController
import net.dev.weatherapp.adapter.NewsAdapter
import net.dev.weatherapp.repo.NewsRepository
import net.dev.weatherapp.repo.local.model.News

class SettingFragmentViewModel(val newsRepository : NewsRepository) : ViewModel() {
    // TODO: Implement the ViewModel



    fun setAdapter(recyclerView: RecyclerView){
        var newsAdapter = NewsAdapter(mutableListOf<News>())
        recyclerView.adapter = newsAdapter
        val swipeController = SwipeController{
            newsRepository.unbookmarkNews(newsAdapter.topHeadlines.get(it))
        }
        val itemTouchHelper = ItemTouchHelper(swipeController)

        itemTouchHelper.attachToRecyclerView(recyclerView)
        newsRepository.getAllBookmarkedNews {
            newsAdapter = NewsAdapter(it)
            recyclerView.adapter = newsAdapter
            newsAdapter.notifyDataSetChanged()
        }
    }
}
