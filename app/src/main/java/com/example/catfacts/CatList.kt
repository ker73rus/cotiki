package com.example.catfacts

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.catfacts.DetailActivity.Companion.CAT_FACT_FAV
import com.example.catfacts.DetailActivity.Companion.CAT_FACT_NUM
import com.example.catfacts.DetailActivity.Companion.CAT_FACT_TEXT_TEXT

class CatAdapter(private val cats: List<Cat>) : RecyclerView.Adapter<CatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val rootView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.cat_item, parent, false)
        return CatViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return cats.size
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(cats.get(position))
    }
}

class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //private val imageView: ImageView = itemView.findViewById((R.id.TextId))
    private val textView: TextView = itemView.findViewById((R.id.TextId))


    fun bind(cat: Cat) {
        textView.text = cat.text
        itemView.setOnClickListener {
            openDetail(itemView.context, cat)
        }
    }

    private fun openDetail(context: Context, cat: Cat) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(CAT_FACT_TEXT_TEXT, cat.text)
        intent.putExtra(CAT_FACT_NUM, cat.num)
        intent.putExtra(CAT_FACT_FAV, cat.fav)
        context.startActivity(intent)
    }
}