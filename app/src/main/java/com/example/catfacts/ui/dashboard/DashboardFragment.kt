package com.example.catfacts.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catfacts.Cat
import com.example.catfacts.CatAdapter
import com.example.catfacts.databinding.FragmentDashBinding
import io.realm.Realm
import io.realm.RealmConfiguration

class DashboardFragment : Fragment() {

    lateinit var binding: FragmentDashBinding

    override fun onResume() {
        super.onResume()
        showListFromDB()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initRealm()
        showListFromDB()
        return root
    }

    private fun showListFromDB() {
        val cats = loadFromDB()
        setList(cats)
    }

    private fun loadFromDB(): List<Cat> {
        val realm = Realm.getDefaultInstance()
        return realm.where(Cat::class.java).equalTo("fav", "true").findAll()
    }

    private fun setList(cats: List<Cat>) {
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerViewId.layoutManager = layoutManager
        val adapter = CatAdapter(cats)
        binding.recyclerViewId.adapter = adapter
    }

    private fun initRealm() {
        Realm.init(context)
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
    }

}