package com.example.companyproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.companyproject.R
import com.example.companyproject.adapter.SearchAdapter
import com.example.companyproject.contract.MainFragmentCon
import com.example.companyproject.databinding.FragmentMainBinding
import com.example.companyproject.model.SearchImage
import com.example.companyproject.net.ApiClient
import com.example.companyproject.net.ApiService
import com.example.companyproject.util.ProgressLoadingClass
import com.example.companyproject.viewModel.MainFragmentVM

class MainFragment : Fragment(), MainFragmentCon {
    override fun showToast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    private var progressBar: ProgressLoadingClass? = null
    private var searchAdapter: SearchAdapter? = null
    private lateinit var binding: FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val apiService: ApiService = ApiClient.getClient()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.vm = MainFragmentVM(apiService, this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProgressBar()
        progressBarLoading(false)
        setRcViewList()
    }

    private fun setRcViewList() {
        activity?.let {
            searchAdapter = SearchAdapter(it)
            binding.rcViewList.apply {
                this.layoutManager = LinearLayoutManager(it)
                this.adapter = searchAdapter
                this.addOnScrollListener(binding.vm!!.scrollListener)
            }
        }
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

    override fun addListAll(list: ArrayList<SearchImage.Document>) {
        searchAdapter?.addListAll(list)
    }

    override fun setListAll(list: ArrayList<SearchImage.Document>) {
        searchAdapter?.setListAll(list)
    }

    override fun progressState(state: Boolean) {
        progressBarLoading(state)
    }

}