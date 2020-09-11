package net.dev.weatherapp.repo

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.dev.weatherapp.repo.local.WeatherAppDatabase
import net.dev.weatherapp.repo.local.model.News
import net.dev.weatherapp.repo.remote.Networking
import net.dev.weatherapp.repo.remote.Networking.NEWS_BASE_URL
import java.io.File

class NewsRepository(val owner : LifecycleOwner, val context : Context) {
    private var db : WeatherAppDatabase
    private var disposables : CompositeDisposable
    init {
        db = WeatherAppDatabase.getInstance(context)
        disposables = CompositeDisposable()
    }

   suspend fun requestTopHeadlines(){
        /*
        Handling api calls with RxJava
        disposables.add(Networking.create(NEWS_BASE_URL, File(""), 5 * 1024 * 1024)
            .queryTopHeadlines("in","technology")
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                it.articles.forEach {
                    db.newsDao().insertNews(it)
                    Timber.d("successful fetch of Top Headlines")
                }
            },{
                Timber.e("$it")
            }))
         */

        withContext(Dispatchers.IO) {
            val response =   Networking.create(NEWS_BASE_URL, File(""), 5 * 1024 * 1024)
                .queryTopHeadlines("in","technology").body()

            response?.let {
                it.articles.forEach {
                    db.newsDao().insertNews(it)
                }
            }
        }

   }

    suspend fun requestNews(onResult : (Boolean) -> Unit){
        /*
        Handling API Calls with RxJava
         disposables.add(Networking.create(NEWS_BASE_URL, File(""), 5 * 1024 * 1024)
            .queryTopHeadlines("in","technology")
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                it.articles.forEach {
                    db.newsDao().insertNews(it)
                    Timber.d("successful fetch of Top Headlines")
                }
                onResult(true)
            },{
                Timber.e("$it")
                onResult(false)
            }))
         */

        withContext(Dispatchers.IO){
         val response = Networking.create(NEWS_BASE_URL, File(""), 5 * 1024 * 1024)
                .queryTopHeadlines("in","technology").body()

            response?.let {
                it.articles.forEach {
                    db.newsDao().insertNews(it)
                }
                onResult(true)
            }
        }

    }

    fun getTopHeadlines(onSuccess : (List<News>) -> Unit){

        db.newsDao().getTopNews().observe(owner, Observer{
            if(it.isNotEmpty()){
                onSuccess(it)
            }
        })
    }

    fun getNews(onSuccess: (List<News>) -> Unit){
//        var observer = Observer<List<News>>()
       db.newsDao().getAllNews().observe(owner, Observer{
           if(it.isNotEmpty()){
               onSuccess(it)
           }
        })


    }

    fun getAllBookmarkedNews(onResult: (List<News>) -> Unit){
        db.newsDao().getBookmarkedNews().observe(owner, Observer {
            onResult(it)
        })

    }

    fun bookmarkNews(news: News?){
        Completable.fromAction{
            news?.let {
                it.bookmark()
                db.newsDao().updateNews(it)
            }
        }.subscribeOn(Schedulers.io())
        .subscribe()


    }

    fun unbookmarkNews(news : News?){
        Completable.fromAction{
            news?.let {
                it.unbookmark()
                db.newsDao().updateNews(it)
            }
        }.subscribeOn(Schedulers.io())
            .subscribe()



    }

//    fun getAllNews():LiveData<News>{
//
//    }

    fun onDestroy(){
        disposables.clear()

    }
}