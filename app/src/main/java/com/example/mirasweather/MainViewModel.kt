package com.example.mirasweather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mirasweather.adapters.WeatherModel

class MainViewModel : ViewModel() {
    val LiveDataCurrent = MutableLiveData<WeatherModel>()
    val LiveDataList = MutableLiveData<List<WeatherModel>>()
}