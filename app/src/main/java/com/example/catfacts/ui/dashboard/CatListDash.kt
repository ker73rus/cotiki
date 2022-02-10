package com.example.catfacts.ui.dashboard

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.catfacts.Cat
import com.example.catfacts.DetailActivity
import com.example.catfacts.DetailActivity.Companion.CAT_FACT_TEXT_ID
import com.example.catfacts.DetailActivity.Companion.CAT_FACT_TEXT_TEXT
import com.example.catfacts.R

class CatAdapterDash(private val cats: List<Cat>) : RecyclerView.Adapter<CatViewHolderDash>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolderDash {
        val rootView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.cat_item,parent,false)
        return CatViewHolderDash((rootView))
    }

    override fun getItemCount(): Int {
        return cats.size
    }

    override fun onBindViewHolder(holder: CatViewHolderDash, position: Int) {
        holder.bind(cats.get(position))
    }
}

class CatViewHolderDash(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //private val imageView: ImageView = itemView.findViewById((R.id.TextId))
    private val textView: TextView = itemView.findViewById((R.id.TextId))


    fun bind(cat: Cat) {
        textView.text = cat.text
        itemView.setOnClickListener {
            openDetail(itemView.context, cat)
        }
    }
    private fun openDetail(context: Context, cat: Cat){
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(CAT_FACT_TEXT_TEXT,cat.text)
        intent.putExtra(CAT_FACT_TEXT_ID,cat._id)
        context.startActivity(intent)
    }
}