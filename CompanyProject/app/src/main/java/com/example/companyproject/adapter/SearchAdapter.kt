package com.example.companyproject.adapter

import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.companyproject.R
import com.example.companyproject.databinding.ItemCardBinding
import com.example.companyproject.model.SearchImage
import com.example.companyproject.viewModel.SearchAdapterVM


class SearchAdapter(activity: FragmentActivity) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private var activity: FragmentActivity = activity
    private var list: ArrayList<SearchImage.Document> = arrayListOf()
    private var screenWidth: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCardBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_card,
            parent,
            false
        )
        val point = Point()
        activity.windowManager.defaultDisplay.getRealSize(point)
        screenWidth = point.x

        binding.vm = SearchAdapterVM()


        return ViewHolder(binding.root, binding.vm!!)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (list.size > 0) {
            holder.onBind(list[holder.adapterPosition])
        }
    }

    fun setListAll(list: ArrayList<SearchImage.Document>) {
        this.list.apply {
            this.clear()
            this.addAll(list)
        }
        notifyDataSetChanged()
    }

    fun addListAll(list: ArrayList<SearchImage.Document>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        view: View,
        vm: SearchAdapterVM
    ) : RecyclerView.ViewHolder(view) {
        private val vm: SearchAdapterVM = vm
        fun onBind(data: SearchImage.Document) {
            vm.setData(data, (screenWidth / 2))
        }
    }
}