package com.example.applicationwithcrud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class ShowProductActivity : BaseActivity() {

    private var recordID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_product)

        ensureRepoLoaded()

        recordID = intent.getStringExtra("recordID")
        if (recordID.isNullOrBlank()) {
            Toast.makeText(this, "Missing record id", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnEdit).setOnClickListener {
            val i = Intent(this, EditProductActivity::class.java)
            i.putExtra("recordID", recordID)
            startActivity(i)
        }

        findViewById<Button>(R.id.btnDelete).setOnClickListener {
            confirmDelete()
        }
    }

    override fun onResume() {
        super.onResume()
        ensureRepoLoaded()
        loadAndDisplay()
    }

    private fun loadAndDisplay() {
        val id = recordID ?: return
        val p = ProductRepository.getById(id)

        if (p == null) {
            Toast.makeText(this, "Record not found ", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        findViewById<TextView>(R.id.txtName).text = p.name
        findViewById<TextView>(R.id.txtDescription).text = p.description
        findViewById<TextView>(R.id.txtPrice).text = "$%.2f".format(p.price)
        findViewById<TextView>(R.id.txtRating).text = p.rating.toString()
        findViewById<TextView>(R.id.txtRecordId).text = p.recordID
        findViewById<TextView>(R.id.txtCreated).text = formatDate(p.dateCreated)
        findViewById<TextView>(R.id.txtModified).text = formatDate(p.dateModified)
    }

    private fun confirmDelete() {
        val id = recordID ?: return

        AlertDialog.Builder(this)
            .setTitle("Delete record?")
            .setMessage("Are you sure you want to delete this product?")
            .setPositiveButton("Yes") { _, _ ->
                val ok = ProductRepository.delete(this, id)
                if (ok) {
                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
                    finish() // go back to list
                } else {
                    Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("No", null)
            .show()
    }
}
