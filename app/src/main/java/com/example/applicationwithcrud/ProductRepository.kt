package com.example.applicationwithcrud

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.UUID

object ProductRepository {

    private const val FILE_NAME = "products.json"
    private val gson = Gson()

    private var products: MutableList<Product> = mutableListOf()

    fun load(context: Context) {
        val json = readFile(context)
        products = if (json.isBlank()) {
            mutableListOf()
        } else {
            val type = object : TypeToken<MutableList<Product>>() {}.type
            gson.fromJson(json, type) ?: mutableListOf()
        }
    }

    fun getAll(): List<Product> = products

    fun getById(id: String): Product? = products.find { it.recordID == id }

    fun add(context: Context, name: String, description: String, price: Double, rating: Int) {
        val now = System.currentTimeMillis()
        val p = Product(
            recordID = UUID.randomUUID().toString(),
            name = name,
            description = description,
            price = price,
            rating = rating,
            dateCreated = now,
            dateModified = now
        )
        products.add(0, p)
        save(context)
    }

    fun update(context: Context, id: String, name: String, description: String, price: Double, rating: Int): Boolean {
        val p = getById(id) ?: return false
        p.name = name
        p.description = description
        p.price = price
        p.rating = rating
        p.dateModified = System.currentTimeMillis()
        save(context)
        return true
    }

    fun delete(context: Context, id: String): Boolean {
        val removed = products.removeIf { it.recordID == id }
        if (removed) save(context)
        return removed
    }

    fun clear(context: Context) {
        products.clear()
        save(context)
    }

    fun generateTestData(context: Context, count: Int = 40) {
        val now = System.currentTimeMillis()
        val random = java.util.Random()

        val words = listOf("Large", "Huge", "Enormous", "Massive", "Gigantic", "Colossal", "Substantial", "Extensive", "Giant", "Big-time")
        val things = listOf("Dog", "Cat", "Fish", "Lizard", "Parrot", "Snake", "Tarantula", "Rat", "Turtle", "Alligator")

        val newList = mutableListOf<Product>()
        repeat(count) {
            val name = "${words[random.nextInt(words.size)]} ${things[random.nextInt(things.size)]}"
            val desc = "Product #${it + 1}"
            val price = (5 + random.nextInt(200)) + (random.nextInt(100) / 100.0)
            val rating = 1 + random.nextInt(5)

            newList.add(
                Product(
                    recordID = UUID.randomUUID().toString(),
                    name = name,
                    description = desc,
                    price = price,
                    rating = rating,
                    dateCreated = now,
                    dateModified = now
                )
            )
        }
        products = newList
        save(context)
    }

    private fun save(context: Context) {
        val json = gson.toJson(products)
        context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use { it.write(json.toByteArray()) }
    }

    private fun readFile(context: Context): String {
        return try {
            context.openFileInput(FILE_NAME).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            ""
        }
    }
}
