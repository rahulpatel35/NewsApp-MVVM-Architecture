package com.rahulpatel.newsapp.ui.language

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rahulpatel.newsapp.R
import com.rahulpatel.newsapp.data.model.Language
import com.rahulpatel.newsapp.data.model.SelectionState
import com.rahulpatel.newsapp.databinding.LanguageItemLayoutBinding
import com.rahulpatel.newsapp.utils.ItemClickListener

class LanguageListAdapter(private val languageList: ArrayList<Language>) :
    RecyclerView.Adapter<LanguageListAdapter.LanguageViewHolder>() {

    lateinit var itemClickLister: ItemClickListener<Any>

    var selectedLanguageState = SelectionState()

    class LanguageViewHolder(private val languageItemLayoutBinding: LanguageItemLayoutBinding) :
        RecyclerView.ViewHolder(languageItemLayoutBinding.root) {
        fun bind(
            language: Language, itemClickListener: ItemClickListener<Any>, tracker: SelectionState
        ) {
            languageItemLayoutBinding.btnSource.text = language.name
            languageItemLayoutBinding.btnSource.setBackgroundColor(
                ContextCompat.getColor(
                    languageItemLayoutBinding.btnSource.context, R.color.orage
                )
            )
            if (tracker.selectedLanguage.contains(language)) {
                languageItemLayoutBinding.btnSource.setBackgroundColor(
                    ContextCompat.getColor(
                        languageItemLayoutBinding.btnSource.context, R.color.purple_700
                    )
                )
            }
            languageItemLayoutBinding.btnSource.setOnClickListener {
                itemClickListener(bindingAdapterPosition, language)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): LanguageViewHolder {

        return LanguageViewHolder(
            LanguageItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: LanguageViewHolder, position: Int
    ) {
        holder.bind(languageList[position], itemClickLister, selectedLanguageState)
    }

    override fun getItemCount(): Int = languageList.size

    fun addLanguage(list: List<Language>) {
        languageList.addAll(list)
    }

}