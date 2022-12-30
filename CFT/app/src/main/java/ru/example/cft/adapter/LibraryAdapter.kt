package ru.example.cft.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.example.cft.R
import ru.example.cft.databinding.LibraryListItemBinding
import ru.example.cft.entities.LibraryItem

class LibraryAdapter(private val listener: Listener) : ListAdapter<LibraryItem, LibraryAdapter.ItemHolder>(ItemComparator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }


    class ItemHolder(view: View) : RecyclerView.ViewHolder(view){
        private val binding = LibraryListItemBinding.bind(view)

        fun setData(item: LibraryItem, listener: Listener) = with(binding){
            tvName.text = item.name
            itemView.setOnClickListener{
                listener.onClick(item.name)
            }
        }
        companion object{
            fun create(parent: ViewGroup): ItemHolder{
                return ItemHolder(
                    LayoutInflater.from(parent.context).
                    inflate(R.layout.library_list_item, parent, false))
            }
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<LibraryItem>(){
        override fun areItemsTheSame(oldItem: LibraryItem, newItem: LibraryItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: LibraryItem, newItem: LibraryItem): Boolean {
            return oldItem == newItem
        }

    }
    interface Listener{
        fun onClick(code: String)
    }


}