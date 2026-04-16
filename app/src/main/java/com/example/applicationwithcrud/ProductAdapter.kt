package com.example.applicationwithcrud

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    private var items: List<Product>,
    private val onClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.VH>() {

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtDescription: TextView = itemView.findViewById(R.id.txtDescription)
        val txtPrice: TextView = itemView.findViewById(R.id.txtPrice)
        val txtRating: TextView = itemView.findViewById(R.id.txtRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_product, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val p = items[position]
        holder.txtName.text = p.name
        holder.txtDescription.text = p.description
        holder.txtPrice.text = "$%.2f".format(p.price)
        holder.txtRating.text = "Rating: ${p.rating}"

        holder.itemView.setOnClickListener { onClick(p) }
    }

    override fun getItemCount(): Int = items.size

    fun update(newItems: List<Product>) {
        items = newItems
        notifyDataSetChanged()
    }
}
