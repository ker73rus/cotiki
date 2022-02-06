package com.example.catfacts


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {
    private val url = "https://aws.random.cat/meow "
    private fun getImageFromServer(queue: RequestQueue) {
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                takeImageUrl(response)
            },
            {
                Toast.makeText(this, "Ошибка картинки", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(stringRequest)
    }
    private fun takeImageUrl(json:String){
        val file = JSONObject(json)
        val urlfile = file.getString("file")
        Glide.with(this).load(urlfile).into(imageView)
    }










    companion object
    {
        const val CAT_FACT_TEXT_TEXT = "com.example.catsfacts.cat_fact_text_tag"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setupActionBar()
        setText()
        val queue = Volley.newRequestQueue((this))
        getImageFromServer(queue)
    }
    private fun setupActionBar(){
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = "Detail"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
    private fun setText(){
        val text = intent?.extras?.getString(CAT_FACT_TEXT_TEXT)
        textId.text = text
    }
}