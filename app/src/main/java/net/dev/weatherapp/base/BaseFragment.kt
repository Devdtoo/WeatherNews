package net.dev.weatherapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import net.dev.weatherapp.viewmodels.MyViewModelFactory

abstract class BaseFragment<B : ViewDataBinding, VM : ViewModel>() : Fragment() {

    protected lateinit var binding : B
    protected lateinit var viewModel : VM
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
           getLayoutId() ,container,false)

        viewModel = ViewModelProviders.of(this, getMyViewModelFactory())
            .get(getViewModelClass())
        return binding.root
    }


    abstract fun getLayoutId(): Int

    abstract fun getViewModelClass():Class<VM>

    abstract fun getMyViewModelFactory() : MyViewModelFactory<VM>

}