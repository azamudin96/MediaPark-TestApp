package com.mediapark.interview.main.news

import Articles
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mediapark.interview.R
import com.mediapark.interview.base.BaseFragment
import com.mediapark.interview.custom.listener.OnItemClickListener
import com.mediapark.interview.databinding.FragmentNewsBinding
import com.mediapark.interview.general.activity.WebViewActivity
import com.mediapark.interview.main.MainActivityInteractor
import com.mediapark.interview.modal.ui.RecyclerItem
import com.mediapark.interview.util.ParseUtil
import com.mediapark.interview.util.UIUtil

class NewsFragment : BaseFragment(), NewsFragmentMvpView, OnItemClickListener {

    companion object {
        const val ARGUMENT_TYPE = "position"
    }

    private val presenter = NewsFragmentPresenter<NewsFragmentMvpView>()
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val adapter = NewsAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        presenter.onAttach(this)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.initContent()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (baseActivity?.getPresenter() is MainActivityInteractor) {
            presenter.interactor = baseActivity?.getPresenter() as MainActivityInteractor
        }
    }

    override fun onDetach() {
        presenter.onDetach()
        super.onDetach()
    }

    override fun setup(view: View) {
        super.setup(view)
        binding.layoutRecycler.visibility = UIUtil.visibility(adapter.arrayData.isNotEmpty())

        binding.recyclerNews.adapter = adapter
    }

    override fun displayListing(
        data: MutableList<RecyclerItem>) {

        binding.layoutRecycler.visibility = UIUtil.visibility(data.isNotEmpty())
        binding.recyclerTitle.text = "${data.size} News"

        Log.i(this.javaClass.name,"News Size: ${data.size}")
        adapter.arrayData.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun invokeWebView(url: String, title: String) {
        val intent = Intent(requireContext(), WebViewActivity::class.java)
        intent.putExtra(WebViewActivity.PARAM_WEBVIEW_URL, url)
        startActivity(intent)
    }

    override fun onItemClick(view: View, position: Int, item: Any?) {
        when(view.id) {
            R.id.click_item -> {
                ParseUtil.safeParse<RecyclerItem>(item)?.getParsedContent<Articles>()?.apply {
                    presenter.performArticleSelect(this.url,this.title)
                }
            }
        }
    }
}