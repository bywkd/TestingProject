package com.example.companyproject.viewModel

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.example.companyproject.model.SearchImage

class SearchAdapterVM : ViewModel() {

    val displaySiteName: ObservableField<String> = ObservableField()
    val dateTime: ObservableField<String> = ObservableField()
    val collection: ObservableField<String> = ObservableField()
    val imgUrl: ObservableField<String> = ObservableField()
    val imgWidth: ObservableInt = ObservableInt()
    fun setData(data: SearchImage.Document, imageWidth: Int) {
        displaySiteName.set(data.displaySitename)
        dateTime.set(data.datetime)
        collection.set(data.collection)
        imgUrl.set(data.imageUrl)
        imgWidth.set(imageWidth)
    }
}