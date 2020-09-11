package net.dev.weatherapp.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.news_item.view.*
import net.dev.weatherapp.R
import net.dev.weatherapp.base.BaseRecyclerViewAdapter
import net.dev.weatherapp.base.BaseViewHolder
import net.dev.weatherapp.databinding.NewsItemBinding
import net.dev.weatherapp.fragments.WebFragment
import net.dev.weatherapp.repo.local.model.News

class NewsAdapter(val topHeadlines: List<News>) : BaseRecyclerViewAdapter<News, NewsItemBinding>() {

    init {
        addItems(topHeadlines)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<NewsItemBinding>, position: Int) {
        onBind(holder.binding, itemList[position])
    }

    override fun getLayoutId(): Int = R.layout.news_item

    private fun onBind(binding: NewsItemBinding, news: News) {
        binding.news = news
        binding.root.card_view.setOnClickListener {
            showWebFragment(binding, news)
        }

    }

    private fun showWebFragment(binding: NewsItemBinding, news: News) {
        val fragment = WebFragment()
        val activity = binding.root.context as AppCompatActivity
        val data = Bundle()
        data.putString("newsLink", news.url)
        fragment.arguments = data
        activity.supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment)
            .addToBackStack("backStack").commit()

    }
}