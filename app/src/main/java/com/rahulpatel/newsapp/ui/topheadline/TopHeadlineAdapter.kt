package com.rahulpatel.newsapp.ui.topheadline

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rahulpatel.newsapp.data.model.topheadlines.ApiArticle
import com.rahulpatel.newsapp.databinding.TopHeadlineItemLayoutBinding
import androidx.core.net.toUri

class TopHeadlineAdapter(private val articleList: ArrayList<ApiArticle>) :
    RecyclerView.Adapter<TopHeadlineAdapter.HeadlineViewHolder>() {

    class HeadlineViewHolder(private val binding: TopHeadlineItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ApiArticle) {
            binding.txtTitle.text = article.title
            binding.txtDescription.text = article.description
            binding.txtSource.text = article.apiSource.name
            Glide.with(binding.imgBanner.context)
                .load(article.imageUrl)
                .into(binding.imgBanner)
            itemView.setOnClickListener {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(it.context, article.url.toUri())
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HeadlineViewHolder {
        return HeadlineViewHolder(
            TopHeadlineItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: HeadlineViewHolder,
        position: Int
    ) = holder.bind(articleList[position])

    override fun getItemCount(): Int = articleList.size

    fun addArticle(list: List<ApiArticle>) {
        articleList.addAll(list)
    }
}


