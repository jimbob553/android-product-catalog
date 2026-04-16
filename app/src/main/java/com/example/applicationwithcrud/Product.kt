package com.example.applicationwithcrud

data class Product(
    val recordID: String,
    var name: String,
    var description: String,
    var price: Double,
    var rating: Int,
    val dateCreated: Long,
    var dateModified: Long
)
