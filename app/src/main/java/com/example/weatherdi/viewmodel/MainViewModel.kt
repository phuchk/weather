package com.example.weatherdi.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherdi.model.ListWeather
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel() : ViewModel() {

    companion object {
        const val URL: String = "https://openweathermap.org/img/wn/"

        //convert date from format Unix,utc to format dd-MM-yyyy HH:mm:ss
        fun calculateDate(int: Int): String {
            val unixSeconds: Int = int
            val date: Date = Date(unixSeconds * 1000L)
            val sdf: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
            sdf.timeZone = TimeZone.getTimeZone("GMT+7")
            return sdf.format(date)
        }

        //get hour and minute with type HH:mm
        fun getHourMinute(int: Int): String {
            val time: String = calculateDate(int)
            val arr = time.split(" ")[1].split(":")
            return arr[0] + ":" + arr[1]
        }

        //get date and mon with type dd-MM
        fun getDateMonth(int: Int): String {
            val time: String = calculateDate(int)
            val arr = time.split(" ")[0].split("-")
            return arr[0] + "-" + arr[1]
        }
    }

    private val data = MutableLiveData<ListWeather>()
    private val error = MutableLiveData<String>()

    fun getWeathers(): MutableLiveData<ListWeather> {
        return data
    }

    fun getError(): MutableLiveData<String> {
        return error
    }

    fun setData(mList: ListWeather) {
        data.value = mList
    }

    fun putValueSharePre(lat: String, lon: String, sharedPreferences: SharedPreferences) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("lat", lat)
        editor.putString("long", lon)
        editor.apply()
        editor.commit()
    }


    fun getValueSharePre(sharedPreferences: SharedPreferences): List<String> {
        val lat = sharedPreferences.getString("lat", "")
        val lon = sharedPreferences.getString("long", "")
        return listOf(lat, lon) as List<String>
    }

}