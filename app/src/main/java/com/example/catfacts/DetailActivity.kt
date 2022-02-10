package com.example.catfacts


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_detail.*
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {
    private val url = "https://aws.random.cat/meow "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setupActionBar()
        setText()
        val cat = Cat()
        cat.text = intent?.extras?.getString(CAT_FACT_TEXT_TEXT).toString()
        cat.num = intent?.extras?.getString(CAT_FACT_NUM).toString()
        cat.fav = intent?.extras?.getString(CAT_FACT_FAV).toString()
        favourite.setOnClickListener {
            saveIntoDB(cat)
        }
        if (cat.fav == "true") {
            favourite.text = "Удалить из избранного"
        } else if (cat.fav == "false") {
            favourite.text = "Добавить в избранное"
        }
        val queue = Volley.newRequestQueue((this))
        getImageFromServer(queue)
    }


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

    private fun takeImageUrl(json: String) {
        val file = JSONObject(json)
        val urlfile = file.getString("file")
        Glide.with(this).load(urlfile).into(imageView)
    }


    private fun saveIntoDB(cat: Cat) {
        val realm = Realm.getDefaultInstance()
        val kitty = realm.where(Cat::class.java).equalTo("num", cat.num)
        if (cat.fav == "false") {
            cat.fav = "true"
            realm.beginTransaction()
            kitty.findFirst()?.fav = "true"
            realm.commitTransaction()
            favourite.text = "Удалить из избранного"
        } else if (cat.fav == "true") {
            cat.fav = "false"
            realm.beginTransaction()
            kitty.findFirst()?.fav = "false"
            realm.commitTransaction()
            favourite.text = "Добавить в избранное"
        }

    }

    private fun loadFromDB(): List<Cat> {
        val realm = Realm.getDefaultInstance()
        return realm.where(Cat::class.java).findAll()
    }


    companion object {
        const val CAT_FACT_TEXT_TEXT = "com.example.catsfacts.cat_fact_text_tag"
        const val CAT_FACT_NUM = "com.example.catsfacts.cat_fact_num_tag"
        var CAT_FACT_FAV = "com.example.catsfacts.cat_fact_fav_tag"
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = "Детально"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setText() {
        val text = intent?.extras?.getString(CAT_FACT_TEXT_TEXT)
        textId.text = text
    }
}