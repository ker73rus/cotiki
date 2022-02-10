package com.example.catfacts.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.catfacts.Cat
import com.example.catfacts.CatAdapter
import com.example.catfacts.databinding.FragmentDashboardBinding
import com.example.catfacts.databinding.FragmentHomeBinding
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.recyclerid
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONArray

class DashboardFragment : Fragment() {

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
        initRealm()
        showListFromDB()
        return root
    }
    private fun showListFromDB(){
        val cats = loadFromDB()
        setList(cats)
    }
    private fun  loadFromDB():List<Cat>{
        val realm = Realm.getDefaultInstance()
        return realm.where(Cat::class.java).equalTo("fav", "true").findAll()
    }
    private fun setList(cats: List<Cat>) {
        val adapter = CatAdapter(cats)
        //recyclerid.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        //recyclerid.layoutManager = layoutManager
    }
    private fun initRealm(){
        Realm.init(context)
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}