package com.example.mirasweather.fragment

import android.Manifest
import android.content.pm.LauncherApps
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mirasweather.R
import com.example.mirasweather.adapters.vpAdapter
import com.example.mirasweather.databinding.ActivityMainBinding
import com.example.mirasweather.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator

const val API_KEY = "abc15da4eed6432f95c174032221612"

class MainFragment : Fragment() {
    private val flist = listOf(
        HoursFragment.newInstance(),
        DaysFragment.newInstance()
    )
    private val tlist = listOf(
        "HOURS",
        "DAYS"
    )
    private lateinit var pLauncher: ActivityResultLauncher<String>
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        init()
        requestWeztherData("Moscow")
    }

    private fun init() = with(binding) {
        val adapter = vpAdapter(activity as FragmentActivity, flist)
        vp.adapter = adapter
        TabLayoutMediator(tabLayout, vp){
            tab, pos -> tab.text = tlist[pos]
        }.attach()
    }

    private fun permissionListener(){
        pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            Toast.makeText(activity, "Permission is $it", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkPermission(){
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)){
            permissionListener()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun requestWeztherData(city: String){
        val url = "https://api.weatherapi.com/v1/forecast.json?key=" +
                API_KEY +
                "&q=" +
                city +
                "&days=" +
                "3" +
                "&aqi=no&alerts=no"

        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET,
            url,
            {
                result -> Log.d("MyLog", "Result: $result")
            },
            {
                error -> Log.d("MyLog", "Error: $error")
            }
        )
        queue.add(request)
    }

    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
            }
    }
