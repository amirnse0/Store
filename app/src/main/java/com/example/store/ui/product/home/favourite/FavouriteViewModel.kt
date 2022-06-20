package com.example.store.ui.product.home.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.store.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.store.data.Result
import com.example.store.data.model.product.ProductItem
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    init {
        getFavouriteProducts(1, 100)
    }

    private val _favouriteProductsStateFlow =
        MutableStateFlow<Result<List<ProductItem>>>(Result.Loading)
    val favouriteProductsStateFlow = _favouriteProductsStateFlow

    fun getFavouriteProducts(page: Int, perPage:Int) {
        viewModelScope.launch {
            repository.getFavouriteProducts(page, perPage).collect {
                favouriteProductsStateFlow.value = it
            }
        }
    }
}