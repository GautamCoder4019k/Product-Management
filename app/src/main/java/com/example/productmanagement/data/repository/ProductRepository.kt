package com.example.productmanagement.data.repository

import com.example.productmanagement.data.api.ProductApiService
import com.example.productmanagement.data.model.Product
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productApiService: ProductApiService) {
    suspend fun getProducts(): List<Product> {
        if (productApiService.getProducts().isSuccessful) {
            return productApiService.getProducts().body()?.products ?: emptyList()
        }
        return emptyList()
    }
}