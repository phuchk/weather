package com.example.weatherdi.adapter

import kotlin.math.ceil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weatherdi.R
import com.example.weatherdi.model.Daily
import com.example.weatherdi.viewmodel.MainViewModel

class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    var mList: List<Daily> = mutableListOf<Daily>()
    var onItemClick: ((Daily) -> Unit)? = null

    fun setList(list: List<Daily>) {
        this.mList = list
        notifyDataSetChanged()
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text1: TextView = view.findViewById(R.id.text_date)
        val text2: TextView = view.findViewById(R.id.text_temperature)
        val text3: TextView = view.findViewById(R.id.text_weather_main)
        val image: ImageView = view.findViewById(R.id.image_weather)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false);
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val daily: Daily = mList[position]
        daily?.let {
            holder.text1.text = MainViewModel.getDateMonth(it.dt)
            holder.image.load(MainViewModel.URL.plus(daily.weather[0].icon) + "@2x.png")
            holder.text2.text =
                ceil(daily.temp.max).toInt().toString() + "°" + "\t" + ceil(daily.temp.min).toInt().toString() + "°"
            holder.text3.text = " ${daily.weather[0].main}"
            holder.itemView.setOnClickListener {
                onItemClick?.invoke(daily)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mList != null)
            mList.size
        else 0
    }
}