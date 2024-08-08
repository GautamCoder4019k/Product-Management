package com.example.productmanagement.ui.productDetail

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.productmanagement.R
import com.example.productmanagement.data.model.Product
import com.example.productmanagement.databinding.ActivityProductDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding

    @SuppressLint("SetTextI18n", "DefaultLocale")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product = intent.getParcelableExtra("product", Product::class.java)

        product?.let {
            binding.productTitle.text = it.title
            binding.productDescription.text = it.description
            binding.productPrice.apply {
                text = String.format("$%.2f", it.price)
                paintFlags = paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
            }
            binding.discountedPrice.text =
                String.format("$%.2f", it.price * (1 - it.discountPercentage / 100))
            binding.stockStatus.text =
                if (it.stock > 0) "In stock • Ships in 24 hours" else "Out of stock"

            Glide.with(this)
                .load(it.thumbnail)
                .placeholder(R.drawable.baseline_broken_image_24)
                .error(R.drawable.baseline_broken_image_24)
                .into(binding.productImage)

            binding.skuValue.text = it.sku
            binding.dimensionsValue.text =
                "W: ${it.dimensions.width}, H: ${it.dimensions.height}, D: ${it.dimensions.depth}"
            binding.warrantyValue.text = it.warrantyInformation
            binding.shippingValue.text = it.shippingInformation
            binding.availabilityValue.text = it.availabilityStatus
            binding.returnPolicyValue.text = it.returnPolicy

            if (it.reviews.isNotEmpty()) {
                val review = it.reviews[0]
                binding.reviewerName.text = review.reviewerName
                binding.reviewDate.text = review.date
                binding.reviewText.text = review.comment
            }
        }
    }
}