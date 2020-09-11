package net.dev.weatherapp.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.news_fragment.*
import net.dev.weatherapp.R
import net.dev.weatherapp.base.BaseFragment
import net.dev.weatherapp.databinding.NewsFragmentBinding
import net.dev.weatherapp.repo.NewsRepository
import net.dev.weatherapp.viewmodels.MyViewModelFactory
import net.dev.weatherapp.viewmodels.NewsFragmentViewModel


class NewsFragment : BaseFragment<NewsFragmentBinding, NewsFragmentViewModel>() {


    private lateinit var newsRepository : NewsRepository

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.newsViewModel = viewModel
        viewModel.isCurrentNewsBookmarked.observe(viewLifecycleOwner, Observer {
            if(it){
               btn_bookmark.setImageResource(R.drawable.ic_bookmarked)
            }else{
                btn_bookmark.setImageResource(R.drawable.ic_bookmark)
            }
        })


        tv_newsDescription.setOnClickListener {
         showWebFragment()
        }

        tv_newsTitle.setOnClickListener {
            showWebFragment()
        }
    }



    fun showWebFragment(){
        val fragment = WebFragment()
        val data =  Bundle()
        data.putString("newsLink",viewModel.news.get()!!.url)
        fragment.arguments = data
        activity?.supportFragmentManager?.beginTransaction()?.add(R.id.fragmentContainer,fragment)?.addToBackStack("backStack")?.commit()

    }

    override fun getLayoutId() = R.layout.news_fragment

    override fun getViewModelClass() = NewsFragmentViewModel::class.java

    override fun getMyViewModelFactory() = MyViewModelFactory(NewsFragmentViewModel::class){
        NewsFragmentViewModel(NewsRepository(viewLifecycleOwner,context!!))
    }

}
