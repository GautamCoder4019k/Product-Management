package com.example.productmanagement.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class ProductResponse(
    val products: List<Product> = emptyList()
)

@Parcelize
data class Product(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val price: Double = 0.0,
    val discountPercentage: Double = 0.0,
    val rating: Double = 0.0,
    val stock: Int = 0,
    val tags: List<String> = emptyList(),
    val brand: String = "",
    val sku: String = "",
    val weight: Int = 0,
    val dimensions: Dimensions = Dimensions(),
    val warrantyInformation: String = "",
    val shippingInformation: String = "",
    val availabilityStatus: String = "",
    val reviews: List<Review> = emptyList(),
    val returnPolicy: String = "",
    val minimumOrderQuantity: Int = 0,
    val meta: Meta = Meta(),
    val images: List<String> = emptyList(),
    val thumbnail: String = ""
) : Parcelable

@Parcelize
data class Dimensions(
    val width: Double = 0.0,
    val height: Double = 0.0,
    val depth: Double = 0.0
) : Parcelable

@Parcelize
data class Review(
    val rating: Int = 0,
    val comment: String = "",
    val date: String = "",
    val reviewerName: String = "",
    val reviewerEmail: String = ""
) : Parcelable

@Parcelize
data class Meta(
    val createdAt: String = "",
    val updatedAt: String = "",
    val barcode: String = "",
    val qrCode: String = ""
) : Parcelable