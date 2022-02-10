package com.example.catfacts.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catfacts.Cat
import com.example.catfacts.CatAdapter
import com.example.catfacts.databinding.FragmentDashboardBinding
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_home.*

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initRealm()
        setList(loadFromDB())


        return root
    }
    private fun setList(catList: List<Cat>) {
        val adapter = CatAdapterDash(catList)
        recyclerfavid.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        recyclerfavid.layoutManager = layoutManager

    }
    private fun initRealm() {
        Realm.init(context)
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
    }
    private fun loadFromDB(): List<Cat> {
        val realm = Realm.getDefaultInstance()
        var catList:List<Cat> = realm.where(Cat::class.java).findAll()
        return catList
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}