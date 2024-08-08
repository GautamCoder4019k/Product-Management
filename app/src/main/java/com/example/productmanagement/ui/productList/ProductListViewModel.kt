package com.example.productmanagement.ui.productList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmanagement.data.model.Product
import com.example.productmanagement.data.repository.ProductRepository
import com.example.productmanagement.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(private val repository: ProductRepository) :
    ViewModel() {

    private val _products = MutableLiveData<Resource<List<ProductListItem>>>()
    val products: LiveData<Resource<List<ProductListItem>>> = _products

    fun fetchProducts() {
        viewModelScope.launch {
            _products.postValue(Resource.loading(null))
            try {
                val response = repository.getProducts()
                val items = mutableListOf<ProductListItem>()
                items.add(ProductListItem.Title("Products List"))
                response.map { items.add(ProductListItem.ProductItem(it)) }
                _products.postValue(Resource.success(items))
            } catch (e: Exception) {
                _products.postValue(Resource.error("Something went wrong", null))
            }
        }
    }
}

sealed class ProductListItem {
    data class Title(val title: String) : ProductListItem()
    data class ProductItem(val product: Product) : ProductListItem()
}