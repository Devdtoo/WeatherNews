package net.dev.weatherapp.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.dev.weatherapp.repo.NewsRepository
import net.dev.weatherapp.repo.local.model.News

class NewsFragmentViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    var news = ObservableField<News>()
    private var newsList = arrayListOf<News>()

    var isCurrentNewsBookmarked = MutableLiveData<Boolean>()

    var nextTitle = ObservableField<String>("")
    private var currrentPosition = 0
    init {
        newsRepository.getNews { it ->
            news.set(it[0])
            isCurrentNewsBookmarked.value =  it[0].isBookmarked
            it.forEach {
                newsList.add(it) }
            changeTitle()
        }





    }

    fun changeTitle(){
        if(!newsList.isEmpty() && currrentPosition < newsList.size - 1){
            nextTitle.set(newsList[currrentPosition + 1].title)
        }
    }


    fun changeNews(){
        currrentPosition++
        if(currrentPosition == newsList.size - 1) currrentPosition = 0
        news.set(newsList.get(currrentPosition))
        isCurrentNewsBookmarked.value = news.get()!!.isBookmarked

        changeTitle()
    }


    fun onBookmarkButtonClicked(){
        if(news.get() != null){
            if(news.get()!!.isBookmarked){
                onUnbookmark()
                isCurrentNewsBookmarked.value = false
            }else{
                onBookmark()
                isCurrentNewsBookmarked.value = true

            }
        }

    }
    private fun onBookmark(){
        newsRepository.bookmarkNews(news.get())
    }

    private fun onUnbookmark(){
        newsRepository.unbookmarkNews(news.get())
    }


}
