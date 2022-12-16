package com.example.demohiyamate.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.demohiyamate.R
import com.example.demohiyamate.model.google.Prediction

class AddressesSearchResultAdapter(private val _context: Activity, resultList: ArrayList<Prediction>) : BaseAdapter() {
    private val resultList: ArrayList<Prediction>

    init {
        this.resultList = resultList
    }

    override fun getCount(): Int {
        return resultList.size
    }

    fun notifyAdapter() {
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) convertView =
            LayoutInflater.from(_context).inflate(R.layout.item_address, parent, false)
        val txtMainText = convertView?.findViewById<TextView>(R.id.txtName)
        val txtDescriptionText = convertView?.findViewById<TextView>(R.id.txtaddress)
        for (i in resultList.indices) {
            txtMainText?.setText(resultList[position].structuredFormatting?.mainText)
            txtDescriptionText?.setText(
                resultList[position].structuredFormatting?.secondaryText
            )
        }
        convertView?.setOnClickListener {
            val intent = Intent()
            intent.putExtra("addressDiscription", resultList[position].description)
            intent.putExtra("placeId", resultList[position].placeId)
            _context.setResult(Activity.RESULT_OK, intent)
            _context.finish()
        }
        return convertView!!
    }
}
