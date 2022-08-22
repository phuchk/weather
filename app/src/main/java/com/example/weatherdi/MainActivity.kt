package com.example.weatherdi

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weatherdi.adapter.MyAdapter
import com.example.weatherdi.model.Current
import com.example.weatherdi.model.Daily
import com.example.weatherdi.model.ListWeather
import com.example.weatherdi.model.Weather
import com.example.weatherdi.retrofit.ApiService
import com.example.weatherdi.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.ceil

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var apiService: ApiService

    lateinit var mainViewModel: MainViewModel

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter
    private lateinit var table: TableLayout
    private lateinit var edt_lat: EditText
    private lateinit var edt_lon: EditText
    private lateinit var btn: Button
    private lateinit var text1: TextView
    private lateinit var text2: TextView
    private lateinit var text7: TextView
    private lateinit var text3: TextView
    private lateinit var text4: TextView
    private lateinit var text5: TextView
    private lateinit var text6: TextView
    private lateinit var text8: TextView
    private lateinit var text9: TextView
    private lateinit var text10: TextView
    private lateinit var text11: TextView
    private lateinit var text12: TextView
    private lateinit var text13: TextView
    private lateinit var text14: TextView
    private lateinit var text15: TextView
    private lateinit var text16: TextView
    private lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        setupUI();
        setupViewModel()
        setupSharePre()
        btn.setOnClickListener {
            handleButtonClick()
        }
        myAdapter.onItemClick = {
            handleItemClick(it)
        }
    }

    suspend fun getApi(lat: String, lon: String): Unit {
        val response =
            apiService.get(lat, lon, "vi", "metric", "a967dd47297960fe94f0ca8bd235a61a")
        if (response.isSuccessful) {
            val mList: ListWeather? = response.body()
            if (mList != null) {
                //handle data
                mainViewModel.setData(mList)
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Có lỗi xảy ra, vui lòng thủ lại",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private fun handleButtonClick() {
        val lat: String = edt_lat.text.toString()
        val lon: String = edt_lon.text.toString()
        try {
            val a: Float = lat.toFloat()
            val b: Float = lon.toFloat()
            val c: Boolean = -90 <= a && a <= 90
            val d: Boolean = -180 <= b && b <= 180
            if (!c || !d) {
                Toast.makeText(this, "Tọa độ không hợp lệ", Toast.LENGTH_SHORT).show()
                return
            }
            GlobalScope.launch(Dispatchers.Main) {
                getApi(lat, lon)
            }
            mainViewModel.putValueSharePre(lat, lon, sharedPreferences)
            displayView()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Hãy nhập tọa độ là số", Toast.LENGTH_SHORT).show()
        } catch (e: NullPointerException) {
            Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show()
        }
    }


    private fun setupSharePre() {
        sharedPreferences = this.getSharedPreferences("position", Context.MODE_PRIVATE)
        val position = mainViewModel.getValueSharePre(sharedPreferences)
        val lat: String = position[0]
        val lon: String = position[1]
        if (lat.isNotEmpty() && lon.isNotEmpty()) {
            edt_lat.setText(lat)
            edt_lon.setText(lon)
            GlobalScope.launch(Dispatchers.Main) {
                getApi(lat, lon)
            }
            displayView()
        }
    }

    private fun displayView() {
        table.visibility = View.VISIBLE
        text8.visibility = View.VISIBLE
    }


    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java);
        mainViewModel.getWeathers().observe(this, Observer {
            myAdapter.setList(it.daily)
            handleWeatherToday(it.current)
            handleItemClick(it.daily[0])
        })
        mainViewModel.getError().observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setupUI() {
        myAdapter = MyAdapter()
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = myAdapter
    }

    private fun handleWeatherToday(current: Current) {
        val weather: Weather = current.weather[0]
        image.load(MainViewModel.URL.plus(weather.icon + "@2x.png"))
        text1.text = weather.main
        text2.text = ceil(current.temp).toInt().toString() + " °C"
        text7.text = MainViewModel.getHourMinute(current.dt)
    }

    private fun init() {
        image = findViewById(R.id.image_weather_today)
        edt_lat = findViewById(R.id.edt_latitude)
        edt_lon = findViewById(R.id.edt_longitude)
        btn = findViewById(R.id.button_get_weather)
        text1 = findViewById(R.id.text_weather_today)
        text2 = findViewById(R.id.text_degree_weather_today)
        text7 = findViewById(R.id.text_time_update)
        text8 = findViewById(R.id.text_weather_detail)

        text3 = findViewById(R.id.text_sunrise)
        text4 = findViewById(R.id.text_feels_like)
        text5 = findViewById(R.id.text_moonrise)
        text6 = findViewById(R.id.text_humidity)
        text9 = findViewById(R.id.text_pressure)
        text10 = findViewById(R.id.text_dew_point)
        text11 = findViewById(R.id.text_wind_speed)
        text12 = findViewById(R.id.text_wind_deg)
        text13 = findViewById(R.id.text_wind_gust)
        text14 = findViewById(R.id.text_clouds)
        text15 = findViewById(R.id.text_rain)
        text16 = findViewById(R.id.text_uvi)
        recyclerView = findViewById(R.id.rev)
        table = findViewById(R.id.detail_weather)

    }

    private fun handleItemClick(daily: Daily) {
        text8.text = daily.weather[0].description
        text3.text = MainViewModel.getHourMinute(daily.sunrise)
        text4.text = daily.feels_like.day.toString() + "°C"
        text5.text = MainViewModel.getHourMinute(daily.moonrise)
        text6.text = daily.humidity.toString() + "%"
        text9.text = daily.pressure.toString() + "hPa"
        text10.text = daily.dew_point.toString() + "°C"
        text11.text = daily.wind_speed.toString() + "m/s"
        text12.text = daily.wind_deg.toString()
        text13.text = daily.wind_gust.toString() + "m/s"
        text14.text = daily.clouds.toString() + "%"
        text15.text = daily.rain.toString() + "mm"
        text16.text = daily.uvi.toString()
    }

}
