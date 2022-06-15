package com.example.store.data

import android.util.Log
import com.example.store.data.model.category.CategoryItem
import com.example.store.data.model.customer.body.Customer
import com.example.store.data.model.customer.result.CustomerResult
import com.example.store.data.model.product.ProductItem
import com.example.store.data.remote.DataSource
import com.example.store.di.AppModule
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
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
        }catch (e:Exception){
            emit(Result.Error(e))
        }
    }

    suspend fun sort(perPage: Int, searchQuery: String, sort: String): Flow<Result<List<ProductItem>>> = flow {
        emit(Result.Loading)
        try {
            val result = remoteDataSource.sort(perPage, searchQuery, sort)
            emit(Result.Success(result))

        }catch (e:Exception){
            emit(Result.Error(e))
        }
    }

    suspend fun getSpecialOffers(): Flow<Result<ProductItem>> = flow {
        emit(Result.Loading)
        try {
            val result = remoteDataSource.getSpecialOffers()
            emit(Result.Success(result))
        }catch (e:Exception){
            emit(Result.Error(e))
        }
    }
    suspend fun createCustomer(customer:Customer): Flow<Result<CustomerResult>> = flow{
        try {
            val result = remoteDataSource.createCustomer(customer)
            emit(Result.Success(result))
        }catch (e:Exception){

            emit(Result.Error(e))
        }
    }

    suspend fun updateCustomer(customer: Customer,id: String): Flow<Result<CustomerResult>> = flow{
        try {
            val result = remoteDataSource.updateCustomer(customer, id)
            emit(Result.Success(result))
        }catch (e:Exception){
            emit(Result.Error(e))
        }
    }

    suspend fun getCustomer(email: String): Flow<Result<CustomerResult>> = flow{
        try {
            val result = remoteDataSource.getCustomer(email).first()
            emit(Result.Success(result))
        }catch (e:Exception){
            emit(Result.Error(e))
        }
    }
}