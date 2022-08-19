package com.joeydee.picsum.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joeydee.picsum.PicsumApplication.Companion.context
import com.joeydee.picsum.databinding.ItemPersonBinding
import com.joeydee.picsum.model.Person

class PersonsAdapter : PagingDataAdapter<Person, PersonViewHolder>(RESULT_COMPARATOR){

    var onItemClicked: ((Person) -> Unit)? = null

    companion object {
        val RESULT_COMPARATOR = object : DiffUtil.ItemCallback<Person>() {
            override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean =
                oldItem.id == newItem.id
        }
    }
    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val repoItem = getItem(position)
        // Note that item may be null, ViewHolder must support binding null item as placeholder
        holder.bind(repoItem)
        holder.binding.holder.setOnClickListener {
            if (repoItem != null) {
                onItemClicked?.invoke(repoItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder.from(parent)
    }
}

class PersonViewHolder(var binding: ItemPersonBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Person?) {
        binding.post = item
        val initial = item?.author?.get(0).toString()
        binding.tvInitial.text = initial
        if (item != null) {
            Glide.with(context).load(item.download_url).into(binding.ivImage)
        }

        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): PersonViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemPersonBinding.inflate(layoutInflater, parent, false)
            return PersonViewHolder(binding)
        }
    }
}