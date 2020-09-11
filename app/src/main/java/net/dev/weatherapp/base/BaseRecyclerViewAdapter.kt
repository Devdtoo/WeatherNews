package net.dev.weatherapp.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T : Any, VB : ViewDataBinding>
    : RecyclerView.Adapter<BaseViewHolder<VB>>() {

    var itemList = mutableListOf<T>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val binding = DataBindingUtil.inflate<VB>(
            LayoutInflater.from(parent.context),
           getLayoutId(), parent, false)
        return BaseViewHolder<VB>(binding)
    }

    fun addItems(list : List<T>){
        this.itemList = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = itemList.size

    abstract fun getLayoutId():Int



}