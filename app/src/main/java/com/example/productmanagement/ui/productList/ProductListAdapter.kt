package com.example.productmanagement.ui.productList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productmanagement.data.model.Product
import com.example.productmanagement.databinding.ItemProductBinding
import com.example.productmanagement.databinding.ItemTitleBinding

class ProductListAdapter(private val onItemClick: (Product) -> Unit) :
    ListAdapter<ProductListItem, RecyclerView.ViewHolder>(DiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ProductListItem.Title -> VIEW_TYPE_TITLE
            is ProductListItem.ProductItem -> VIEW_TYPE_PRODUCT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_TITLE -> TitleViewHolder(
                ItemTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            VIEW_TYPE_PRODUCT -> ProductViewHolder(
                ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                onItemClick
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> holder.bind(getItem(position) as ProductListItem.Title)
            is ProductViewHolder -> holder.bind(getItem(position) as ProductListItem.ProductItem)
        }
    }

    companion object {
        private const val VIEW_TYPE_TITLE = 0
        private const val VIEW_TYPE_PRODUCT = 1
    }

    class DiffCallback : DiffUtil.ItemCallback<ProductListItem>() {
        override fun areItemsTheSame(oldItem: ProductListItem, newItem: ProductListItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ProductListItem,
            newItem: ProductListItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    class TitleViewHolder(private val binding: ItemTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductListItem.Title) {
            binding.title.text = item.title
        }
    }

    class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val onItemClick: (Product) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductListItem.ProductItem) {
            binding.productTitle.text = item.product.title
            binding.productDescription.text = item.product.description
            binding.root.setOnClickListener { onItemClick(item.product) }
            Glide.with(binding.root.context)
                .load(item.product.thumbnail)
                .into(binding.productImage)
        }
    }
}