package com.github.demo.ui.trending

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.demo.R
import com.github.demo.model.Repo
import kotlinx.android.synthetic.main.lay_item_trending_repository.view.*

class RepositoryItemAdapter : RecyclerView.Adapter<RepositoryItemAdapter.RepositoryItemHolder>(),
    Filterable {
    private var alData = ArrayList<Repo>()
    private val alTemp = ArrayList<Repo>()
    var search: String? = null

    class RepositoryItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryItemHolder {
        return RepositoryItemHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.lay_item_trending_repository, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RepositoryItemHolder, position: Int) {
        val item = alData[position]
        Glide.with(holder.itemView)
            .load(item.owner.avatar_url)
            .error(android.R.drawable.stat_notify_error)
            .into(holder.itemView.ivPicture)
        val name = SpannableString(item.owner.login + " / " + item.name)
        name.setSpan(
            StyleSpan(Typeface.BOLD),
            item.owner.login.length,
            name.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        holder.itemView.tvName.text = name
        holder.itemView.tvDescription.text = item.description
        holder.itemView.tvLanguage.text = item.language
    }

    override fun getItemCount(): Int {
        return alData.size
    }

    fun addData(data: ArrayList<Repo>) {
        alData.addAll(data)
        alTemp.addAll(data)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return DataFilter()
    }

    inner class DataFilter : Filter() {
        override fun performFiltering(char: CharSequence?): FilterResults {
            val results = FilterResults()
            val alFilter = ArrayList<Repo>()
            char?.let { searchString ->
                search = searchString.toString()
                alTemp.forEach {
                    if (it.name.contains(searchString.toString(), true) || it.owner.login.contains(
                            searchString.toString(),
                            true
                        )
                    ) {
                        alFilter.add(it)
                    }
                }
                results.values = alFilter
                results.count = alFilter.size
            } ?: kotlin.run {
                results.values = alTemp
                results.count = alTemp.size
            }
            return results
        }

        override fun publishResults(char: CharSequence?, p1: FilterResults?) {
            p1?.let {
                alData = it.values as ArrayList<Repo>
                notifyDataSetChanged()
            }
        }
    }
}