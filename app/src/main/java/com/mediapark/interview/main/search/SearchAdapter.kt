package com.mediapark.interview.main.search

import Articles
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mediapark.interview.base.BaseViewHolder
import com.mediapark.interview.custom.listener.OnItemClickListener
import com.mediapark.interview.databinding.LayoutNewsListItemBinding
import com.mediapark.interview.modal.ui.CheckListImageText
import com.mediapark.interview.modal.ui.RecyclerItem
import com.mediapark.interview.util.GlideApp
import com.mediapark.interview.util.NewGlideModule
import com.mediapark.interview.util.ParseUtil
import com.mediapark.interview.util.UIUtil

class SearchAdapter (var callback: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var arrayData = mutableListOf<RecyclerItem>()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutNewsListItemBinding.inflate(inflater, parent, false)
        return SearchItemHolder(binding, callback)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is SearchItemHolder -> holder.onBind(position, arrayData[position])
        }
    }

    override fun getItemCount(): Int {
        return arrayData.size
    }

    class SearchItemHolder internal constructor(var binding: LayoutNewsListItemBinding, var callback: OnItemClickListener) : BaseViewHolder(binding.root), View.OnClickListener {

        init {
            UIUtil.setViewClickListener(this,
                binding.clickItem
            )
        }

        override fun onBind(position: Int, item: RecyclerItem?) {
            super.onBind(position, item)

            val articles = item?.getParsedContent<Articles>() ?: return

            binding.txtTitle.text = articles.title
            binding.txtSubtitle.text = articles.description

            GlideApp.with(context)
                .load(articles.image)
                .apply(NewGlideModule.diskCacheRequestOption())
                .into(binding.imgBackground)
        }

        override fun onClick(v: View) {
            callback.onItemClick(v, absoluteAdapterPosition, currentItem)
        }
    }
}