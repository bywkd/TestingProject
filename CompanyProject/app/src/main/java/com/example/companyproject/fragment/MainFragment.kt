package com.example.companyproject.fragment

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.companyproject.R
import com.example.companyproject.adapter.ImageAdapter
import com.example.companyproject.model.SearchImage
import com.example.companyproject.net.ApiClient
import com.example.companyproject.util.ProgressLoadingClass
import kotlinx.android.synthetic.main.fragment_main.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {

    private var progressBar: ProgressLoadingClass? = null
    private var currentTime: Long = 0

    private var timeHandler: Handler? = null
    private var adapter: ImageAdapter? = null

    private var currentPage: Int = 0
    private var isEndPage: Boolean = false

    private lateinit var rcView: RecyclerView
    private lateinit var etSearch: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        etSearch = view.et_search
        rcView = view.rcView_list

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timeHandler = Handler()

        etSearch.addTextChangedListener(etTextWatcher)

        activity?.let {
            adapter = ImageAdapter(it)
            rcView.adapter = adapter
            rcView.layoutManager = LinearLayoutManager(it)
            setPaging()
        }
        setProgressBar()
        progressBarLoading(false)

    }

    private fun setProgressBar() {

        this.context?.let {
            progressBar = ProgressLoadingClass(it)
            progressBar?.let { progressView ->
                val params =
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                val viewGroup = this.view as ViewGroup
                viewGroup.addView(progressView.getProgress(), params)
            }
        }
    }

    private fun progressBarLoading(state: Boolean) {
        if (state) {
            activity?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
        progressBar?.loadingProgressBar(state)
    }

    private val etTextWatcher = object : TextWatcher {

        override fun afterTextChanged(s: Editable?) {

            s?.let {
                if (s.isNotEmpty()) {
                    currentTime = System.currentTimeMillis()
                    timeHandler?.removeCallbacksAndMessages(null)
                    timeHandler?.postDelayed({
                        currentPage = 0
                        requestSearchImage(it.toString())
                    }, 1000)
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    private fun setPaging() {
        rcView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (rcView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

                val itemTotalCount = recyclerView.adapter?.itemCount
                itemTotalCount?.let {
                    if (it - 1 == lastVisibleItemPosition) {
                        if (!isEndPage) {
                            requestSearchImage(etSearch.text.toString(), currentPage + 1)
                        }
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }


    private fun requestSearchImage(text: String, page: Int = 1, size: Int = 80) {
        if (text.isEmpty()) {
            timeHandler?.removeCallbacksAndMessages(null)
            return
        }
        progressBar?.loadingProgressBar(true)
        val request = ApiClient.getClient().listEvents(text, null, page, size)
        request.enqueue(object : Callback<SearchImage> {
            override fun onFailure(call: Call<SearchImage>, t: Throwable) {
                progressBar?.loadingProgressBar(false)
                Toast.makeText(activity, "requestSearchImage 실패 하였습니다.", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(call: Call<SearchImage>, response: Response<SearchImage>) {
                progressBar?.loadingProgressBar(false)
                var isSuccess = response.isSuccessful
                if (isSuccess) {
                    response.body()?.let {
                        val value = it
                        value.meta?.let { meta ->
                            isEndPage = meta.isEnd
                            if (meta.pageableCount > 0) {
                                value.documents?.let { documents ->
                                    if (!isEndPage) {
                                        adapter?.addListAll(documents)
                                    } else {
                                        adapter?.setListAll(documents)
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
                    Toast.makeText(activity, "정보가 없습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }
}