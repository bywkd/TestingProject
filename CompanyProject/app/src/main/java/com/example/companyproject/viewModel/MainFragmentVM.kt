package com.example.companyproject.viewModel

import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.companyproject.contract.MainFragmentCon
import com.example.companyproject.model.SearchImage
import com.example.companyproject.net.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragmentVM : ViewModel {

    private var timeHandler: Handler? = null
    private var currentTime: Long = 0
    private var currentPage: Int = 0
    private var isEndPage: Boolean = false
    private val contract: MainFragmentCon
    private val apiService: ApiService

    val searchText: ObservableField<String> = ObservableField()

    constructor(apiService: ApiService, contract: MainFragmentCon) {
        timeHandler = Handler()
        currentTime = 0
        this.contract = contract
        this.apiService = apiService
    }

    val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            searchText.set("")
            s?.let { searchText.set(it.toString()) }
            searchText.get()?.let {
                if (it.isNotEmpty()) {
                    currentTime = System.currentTimeMillis()
                    timeHandler?.removeCallbacksAndMessages(null)
                    timeHandler?.postDelayed({
                        currentPage = 0
                        requestSearchImage()
                    }, 1000)
                }
            }
        }
    }


    val scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val lastVisibleItemPosition =
                (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

            val itemTotalCount = recyclerView.adapter?.itemCount
            itemTotalCount?.let {
                if (it - 1 == lastVisibleItemPosition) {
                    if (!isEndPage) {
                        requestSearchImage(currentPage + 1)
                    }
                }
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
        }
    }

    private fun requestSearchImage(page: Int = 1, size: Int = 80) {
        if (searchText.get()!!.isEmpty()) {
            timeHandler?.removeCallbacksAndMessages(null)
            return
        }
        contract.progressState(true)
        val request = apiService.listEvents(searchText.get()!!, null, page, size)
        request.enqueue(object : Callback<SearchImage> {
            override fun onFailure(call: Call<SearchImage>, t: Throwable) {
                contract.progressState(false)
                contract.showToast("requestSearchImage 실패 하였습니다.")
            }

            override fun onResponse(call: Call<SearchImage>, response: Response<SearchImage>) {
                contract.progressState(false)
                var isSuccess = response.isSuccessful
                if (isSuccess) {
                    response.body()?.let {
                        val value = it
                        value.meta?.let { meta ->
                            isEndPage = meta.isEnd
                            if (meta.pageableCount > 0) {
                                value.documents?.let { documents ->
                                    if (currentPage < 1) {
                                        contract.setListAll(documents)
                                    } else {
                                        contract.addListAll(documents)
                                    }
                                    currentPage = page
                                }
                            }
                        }
                    } ?: let {
                        isSuccess = false
                    }
                }
                if (!isSuccess) {
                    contract.showToast("정보가 없습니다.")
                }
            }
        })
    }
}