package com.example.catfacts


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.catfacts.ui.dashboard.CatAdapterDash
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {
    private val url = "https://aws.random.cat/meow "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initRealm()
        setupActionBar()
        setText()

        favourite.setOnClickListener {
            insertIntoDB()
        }
        val queue = Volley.newRequestQueue((this))
        getImageFromServer(queue)
    }

    private fun insertIntoDB() {
        val realm = Realm.getDefaultInstance()
        val cat = Cat()
        cat.text = intent?.extras?.getString(CAT_FACT_TEXT_TEXT).toString()
        cat._id = intent?.extras?.getString(CAT_FACT_TEXT_ID).toString()
        realm.beginTransaction()
        realm.insertOrUpdate(cat)
        realm.commitTransaction()

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

    private fun initRealm() {
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
    }


    companion object {
        const val CAT_FACT_TEXT_TEXT = "com.example.catsfacts.cat_fact_text_tag"
        const val CAT_FACT_TEXT_ID = "com.example.catsfacts.cat_fact_id_tag"
    }

    private fun setupActionBar() {
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

    private fun setText() {
        val text = intent?.extras?.getString(CAT_FACT_TEXT_TEXT)
        textId.text = text
    }
}