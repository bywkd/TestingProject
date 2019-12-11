package com.example.companyproject.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.companyproject.model.SearchImage

class SearchAdapterVM : ViewModel() {

    val displaySiteName: ObservableField<String> = ObservableField()
    val dateTime: ObservableField<String> = ObservableField()
    val collection: ObservableField<String> = ObservableField()

    fun setData(data: SearchImage.Document) {
        displaySiteName.set(data.displaySitename)
        dateTime.set(data.datetime)
        collection.set(data.collection)
    }
}