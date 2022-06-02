package com.example.store.data.source

import com.example.store.data.model.category.CategoryItem
import com.example.store.data.model.product.ProductItem

interface DataSource {
    suspend fun getLatestProducts(page: Int, perPage: Int): List<ProductItem>
    suspend fun getFavouriteProducts(page: Int, perPage: Int): List<ProductItem>
    suspend fun getBestProducts(page: Int, perPage: Int): List<ProductItem>
    suspend fun getCategories(): List<CategoryItem>
    suspend fun getSomeCategory(page: Int, category: String): List<ProductItem>
    suspend fun searchQuery(perPage: Int, searchQuery: String): List<ProductItem>
    suspend fun sort(perPage: Int, searchQuery: String, sort:String): List<ProductItem>
}