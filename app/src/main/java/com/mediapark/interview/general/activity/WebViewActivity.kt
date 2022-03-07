package com.mediapark.interview.general.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.mediapark.interview.R
import com.mediapark.interview.base.BaseActivity
import com.mediapark.interview.databinding.ActivityWebviewBinding
import com.mediapark.interview.util.UIUtil

class WebViewActivity : BaseActivity(), WebViewMvpView, View.OnClickListener {
    companion object {
        const val PARAM_WEBVIEW_URL = "webview_url"

        fun invokeActivity(context: Context, url: String) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(PARAM_WEBVIEW_URL, url)
            context.startActivity(intent)
        }
    }

    private val presenter = WebViewPresenter<WebViewMvpView>()
    private lateinit var binding: ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var url = "https://gnews.io/"
        if (intent.hasExtra(PARAM_WEBVIEW_URL)) {
            url = intent.getStringExtra(PARAM_WEBVIEW_URL) ?: ""
        }

        setup()
        presenter.onAttach(this)
        presenter.initContent(url)
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    override fun loadContent(url: String) {
        binding.webContent.loadUrl(url)
    }

    override fun displayLoading(shown: Boolean) {
        binding.progressContent.visibility = UIUtil.visibility(shown)
    }

    @SuppressLint("NonConstantResourceId")
    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_default_back -> invokeBack()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun setup() {
        val elevation = resources.getDimension(R.dimen.elevation)
        val headerColor = ContextCompat.getColor(this, R.color.white)

        binding.layoutHeader.btnHeaderAction.visibility = View.GONE
        binding.layoutHeader.root.elevation = elevation
        binding.layoutHeader.root.setBackgroundColor(headerColor)

        binding.webContent.clearCache(true)
        binding.webContent.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        binding.webContent.settings.allowFileAccess = false
        binding.webContent.settings.javaScriptEnabled = true
        binding.webContent.settings.loadWithOverviewMode = true
        binding.webContent.settings.useWideViewPort = true
        binding.webContent.webChromeClient = presenter.webChromeClient
        binding.webContent.webViewClient = presenter.webViewClient

        binding.layoutHeader.btnDefaultBack.setOnClickListener(this)
    }
}