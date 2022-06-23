package com.example.store.data

import com.example.store.data.model.category.CategoryItem
import com.example.store.data.model.coupons.CouponItem
import com.example.store.data.model.customer.body.Customer
import com.example.store.data.model.customer.result.CustomerResult
import com.example.store.data.model.order.body.Order
import com.example.store.data.model.order.result.OrderResult
import com.example.store.data.model.product.ProductItem
import com.example.store.data.model.reviews.body.ReviewBody
import com.example.store.data.model.reviews.result.ReviewItem
import com.example.store.data.remote.DataSource
import com.example.store.di.AppModule
import com.example.store.ui.cart.CartItem
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(@AppModule.RemoteProductDataSource private val remoteDataSource: DataSource) {

    suspend fun getLatestProducts(page: Int, perPage: Int): Flow<Result<List<ProductItem>>> = flow {
        emit(Result.Loading)
        try {
            val latestProducts = remoteDataSource.getLatestProducts(page, perPage)
            emit(Result.Success(latestProducts))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    suspend fun getFavouriteProducts(page: Int, perPage: Int): Flow<Result<List<ProductItem>>> =
        flow {
            emit(Result.Loading)
            try {
                val favouriteProducts = remoteDataSource.getFavouriteProducts(page, perPage)
                emit(Result.Success(favouriteProducts))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }

    suspend fun getBestProducts(page: Int, perPage: Int): Flow<Result<List<ProductItem>>> = flow {
        emit(Result.Loading)
        try {
            val bestProducts = remoteDataSource.getBestProducts(page, perPage)
            emit(Result.Success(bestProducts))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    suspend fun getProduct(id: String): Flow<Result<ProductItem>> = flow {
        emit(Result.Loading)
        try {
            val result = remoteDataSource.getProduct(id)
            emit(Result.Success(result))
        } catch (e:Exception){
            emit(Result.Error(e))
        }
    }

    suspend fun getCategories(): Flow<Result<List<CategoryItem>>> = flow {
        emit(Result.Loading)
        try {
            val categories = remoteDataSource.getCategories()
            emit(Result.Success(categories))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    suspend fun getSomeCategory(page: Int, categoryId: String): Flow<Result<List<ProductItem>>> =
        flow {
            emit(Result.Loading)
            try {
                val products = remoteDataSource.getSomeCategory(page, categoryId)
                emit(Result.Success(products))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }

    suspend fun search(perPage: Int, searchQuery: String): Flow<Result<List<ProductItem>>> = flow {
        emit(Result.Loading)
        try {
            val result = remoteDataSource.searchQuery(perPage, searchQuery)
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    suspend fun sortAndFilter(
        perPage: Int,
        searchQuery: String,
        sort: String,
        higherPrice: String,
        lowerPrice: String,
        categoryId: Int
    ): Flow<Result<List<ProductItem>>> = flow {
        emit(Result.Loading)
        try {
            val result = remoteDataSource.sortAndFilter(perPage, searchQuery, sort, lowerPrice, higherPrice, categoryId)
            emit(Result.Success(result))

        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    suspend fun getSpecialOffers(): Flow<Result<ProductItem>> = flow {
        emit(Result.Loading)
        try {
            val result = remoteDataSource.getSpecialOffers()
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    suspend fun createCustomer(customer: Customer): Flow<Result<CustomerResult>> = flow {
        try {
            val result = remoteDataSource.createCustomer(customer)
            emit(Result.Success(result))
        } catch (e: Exception) {

            emit(Result.Error(e))
        }
    }

    suspend fun updateCustomer(customer: Customer, id: String): Flow<Result<CustomerResult>> =
        flow {
            try {
                val result = remoteDataSource.updateCustomer(customer, id)
                emit(Result.Success(result))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }

    suspend fun getCustomer(email: String): Flow<Result<CustomerResult>> = flow {
        try {
            val result = remoteDataSource.getCustomer(email).first()
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    suspend fun createOrder(order: Order): Flow<Result<OrderResult>> = flow {
        try {
            val result = remoteDataSource.createOrder(order)
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    suspend fun updateOrder(order: Order, id: String): Flow<Result<OrderResult>> = flow {
        try {
            val result = remoteDataSource.updateOrder(order, id)
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    suspend fun getOrder(id: String): Flow<Result<OrderResult>> = flow {
        try {
            val result = remoteDataSource.getOrder(id)
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    suspend fun getItemsInCart(id:String): List<Pair<Int, Int>>{
        return try {
            remoteDataSource.getOrder(id).lineItems.map {
                Pair(it.productId, it.quantity)
            }
        } catch (e:Exception){
            listOf()
        }
    }

    suspend fun getReviews(productID: String): Flow<Result<List<ReviewItem>>> = flow {
        try {
            val result = remoteDataSource.getReviews(productID)
            emit(Result.Success(result))
        } catch (e:Exception){
            emit(Result.Error(e))
        }
    }

    suspend fun setReview(reviewBody: ReviewBody): Flow<Result<ReviewItem>> = flow {
        try {
            val result = remoteDataSource.createReview(reviewBody)
            emit(Result.Success(result))
        } catch (e:Exception){
            emit(Result.Error(e))
        }
    }

    suspend fun getCoupons(code:String): Flow<Result<CouponItem>> = flow {
        try {
            val result = remoteDataSource.getCoupons(code).first()
            emit(Result.Success(result))
        }catch (e:Exception){
            emit(Result.Error(e))
        }
    }
}