package com.example.catfacts.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.catfacts.Cat
import com.example.catfacts.CatAdapter
import com.example.catfacts.databinding.FragmentHomeBinding
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONArray

class HomeFragment : Fragment() {
    private val url = "https://cat-fact.herokuapp.com/facts"
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val queue = Volley.newRequestQueue(context)
        getCatsFromServer(queue)

        return root
    }
    private fun getCatsFromServer(queue: RequestQueue) {
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val catList = parseResponse(response)
                setList(catList)
            },
            {
                Toast.makeText(context, "Ошибка запроса", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(stringRequest)
    }

    private fun parseResponse(responseText: String): List<Cat> {
        val catList: MutableList<Cat> = mutableListOf()
        val jsonArray = JSONArray(responseText)
        for (index in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(index)
            val catText = jsonObject.getString("text")
            val catID = jsonObject.getString("_id")
            //val catImage = jsonObject.getString("image")
            val cat = Cat()
                cat.text = catText
                cat._id = catID
            catList.add(cat)
        }
        return catList
    }

    private fun setList(cats: List<Cat>) {
        val adapter = CatAdapter(cats)
        recyclerid.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        recyclerid.layoutManager = layoutManager
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}