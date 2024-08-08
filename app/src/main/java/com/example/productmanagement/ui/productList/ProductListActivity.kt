package com.example.productmanagement.ui.productList

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productmanagement.databinding.ActivityProductListBinding
import com.example.productmanagement.ui.productDetail.ProductDetailActivity
import com.example.productmanagement.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding
    private val viewModel: ProductListViewModel by viewModels()
    private lateinit var adapter: ProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ProductListAdapter {
            val intent = Intent(this, ProductDetailActivity::class.java).apply {
                putExtra("product", it)
            }
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.products.observe(this) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorTextView.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE

                    adapter.submitList(resource.data)
                }

                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.errorTextView.visibility = View.VISIBLE

                    binding.errorTextView.text = resource.message ?: "An unknown error occurred"
                }

                Status.LOADING -> {
                    binding.recyclerView.visibility = View.GONE
                    binding.errorTextView.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

        viewModel.fetchProducts()
    }
}