package com.example.applicationwithcrud

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddProductActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        ensureRepoLoaded()

        val edtName = findViewById<EditText>(R.id.edtName)
        val edtDescription = findViewById<EditText>(R.id.edtDescription)
        val edtPrice = findViewById<EditText>(R.id.edtPrice)
        val edtRating = findViewById<EditText>(R.id.edtRating)

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            val name = edtName.text.toString().trim()
            val description = edtDescription.text.toString().trim()


            if (name.isBlank()) {
                edtName.error = "Name is required"
                edtName.requestFocus()
                return@setOnClickListener
            }


            val priceText = edtPrice.text.toString().trim()
            val price = if (priceText.isBlank()) 0.0 else priceText.toDoubleOrNull()
            if (price == null || price < 0) {
                edtPrice.error = "Enter a valid price (0 or more)"
                edtPrice.requestFocus()
                return@setOnClickListener
            }


            val ratingText = edtRating.text.toString().trim()
            val rating = ratingText.toIntOrNull()
            if (rating == null || rating !in 1..5) {
                edtRating.error = "Rating must be from 1 to 5"
                edtRating.requestFocus()
                return@setOnClickListener
            }


            ProductRepository.add(
                context = this,
                name = name,
                description = description,
                price = price,
                rating = rating
            )

            Toast.makeText(this, "Product saved", Toast.LENGTH_SHORT).show()
            finish() // returns to MainActivity which refreshes in onResume()
        }

        findViewById<Button>(R.id.btnCancel).setOnClickListener {
            finish()
        }
    }
}
