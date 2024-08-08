package com.example.productmanagement.data.api

import com.example.productmanagement.data.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): Response<ProductResponse>
}