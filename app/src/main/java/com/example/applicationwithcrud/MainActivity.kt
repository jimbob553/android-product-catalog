package com.example.applicationwithcrud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : BaseActivity() {

    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ensureRepoLoaded()

        val recycler = findViewById<RecyclerView>(R.id.recyclerProducts)
        recycler.layoutManager = LinearLayoutManager(this)

        adapter = ProductAdapter(ProductRepository.getAll()) { product ->

            val i = Intent(this, ShowProductActivity::class.java)
            i.putExtra("recordID", product.recordID)
            startActivity(i)
        }
        recycler.adapter = adapter

        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            val i = Intent(this, AddProductActivity::class.java)
            startActivity(i)
        }

        findViewById<Button>(R.id.btnTestData).setOnClickListener {
            ProductRepository.generateTestData(this, 40)
            refresh()
            Toast.makeText(this, "Records created", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            ProductRepository.clear(this)
            refresh()
            Toast.makeText(this, "Database cleared", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        ensureRepoLoaded()
        refresh()
    }

    private fun refresh() {
        adapter.update(ProductRepository.getAll())
    }
}
