package com.example.companyproject.adapter

import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.target.Target
import com.example.companyproject.R
import com.example.companyproject.databinding.ItemCardBinding
import com.example.companyproject.model.SearchImage
import com.example.companyproject.util.GlideApp
import com.example.companyproject.viewModel.SearchAdapterVM
import kotlinx.android.synthetic.main.item_card.view.*


class SearchAdapter(activity: FragmentActivity) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private var activity: FragmentActivity = activity
    private var list: ArrayList<SearchImage.Document> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCardBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_card,
            parent,
            false
        )

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
        private val flPicture = view.fl_picture
        private val imgViewPicture = view.imgView_picture
        fun onBind(data: SearchImage.Document) {
            vm.setData(data)
            val point = Point()
            activity.windowManager.defaultDisplay.getRealSize(point)

            val screenWidth = point.x

            val params = flPicture.layoutParams
            params.width = (screenWidth.toFloat() / 2).toInt()
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            flPicture.layoutParams = params


            val glideLoadUrl =
                GlideApp.with(imgViewPicture.context).asBitmap().load(data.imageUrl)
                    .override((screenWidth.toFloat() / 2).toInt(), Target.SIZE_ORIGINAL)
            if (data.width < (screenWidth.toFloat() / 2).toInt()) {
                glideLoadUrl.centerCrop().into(imgViewPicture)
            } else {
                glideLoadUrl.fitCenter().into(imgViewPicture)
            }
        }
    }
}