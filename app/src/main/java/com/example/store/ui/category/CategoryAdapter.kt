package com.example.store.ui.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.store.data.model.category.CategoryItem
import com.example.store.data.model.product.ProductItem
import com.example.store.databinding.CategoryCardViewBinding
import com.example.store.databinding.ProductCardViewBinding
import com.example.store.ui.product.ProductDiffUtil

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    private var oldList: List<CategoryItem> = emptyList()
    private lateinit var clickOnItem: ClickOnItem

    inner class MyViewHolder(private val binding: CategoryCardViewBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(position: Int) {
            binding.categoryTv.text = oldList[position].name

            binding.categoryIv.load("https://fantasy.premierleague.com/img/favicons/favicon-32x32.png")
            //binding.cardViewIv.load(oldList[position].images.first().src)
        }

        override fun onClick(p0: View?) {
            clickOnItem.clickOnItem(adapterPosition, p0)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = CategoryCardViewBinding.inflate(inflater)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return oldList.size
    }

    interface ClickOnItem {
        fun clickOnItem(position: Int, view: View?)
    }

    fun setToClickOnItem(clickOnItem: ClickOnItem) {
        this.clickOnItem = clickOnItem
    }

    fun setData(newList: List<CategoryItem>) {
        val diffUtil = CategoryDiffUtil(oldList, newList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        oldList = newList
        diffResults.dispatchUpdatesTo(this)
    }
}