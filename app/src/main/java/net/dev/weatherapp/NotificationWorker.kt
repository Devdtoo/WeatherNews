package net.dev.weatherapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.dev.weatherapp.repo.local.WeatherAppDatabase
import net.dev.weatherapp.repo.local.model.News
import net.dev.weatherapp.repo.remote.Networking
import timber.log.Timber
import java.io.File

class NotificationWorker(private val appContext: Context, workerParams: WorkerParameters) : CoroutineWorker(appContext, workerParams) {

   private val CHANNEL_ID = "News_Notification"
    override suspend fun doWork(): Result {
        requestTopNews()
        return Result.success()

    }

    suspend fun requestTopNews(){
       /*
       handling api calls with RxJava
        Networking.create(Networking.NEWS_BASE_URL, File(""), 5 * 1024 * 1024)
            .queryTopHeadlines("in","technology")
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                it.articles.forEach {
                    WeatherHuntDatabase.getInstance(appContext).newsDao().insertNews(it)
                    Timber.d("successful fetch of Top Headlines")
                }
                showNewsNotification(it.articles.first())

            },{
                Timber.e("$it")

            })
        */

        withContext(Dispatchers.IO){
            val response = Networking.create(Networking.NEWS_BASE_URL, File(""), 5 * 1024 * 1024)
                .queryTopHeadlines("in","technology").body()
            response?.let{
                it.articles.forEach {
                    WeatherAppDatabase.getInstance(appContext).newsDao().insertNews(it)
                    Timber.d("successful fetch of Top Headlines")
                }
                showNewsNotification(it.articles.first())
            }
        }
    }

    fun showNewsNotification(news : News){
        createNewsChannel()

        val builder = NotificationCompat.Builder(appContext,CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("{${news.title}}")
            .setContentText("{${news.description}}")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(appContext)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, builder.build())
        }

    }

    fun createNewsChannel(){
        if(SDK_INT >= Build.VERSION_CODES.O){
            val name = appContext.getString(R.string.channel_name)
            val descriptionText = appContext.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}