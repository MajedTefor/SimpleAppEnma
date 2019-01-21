package com.enma.app.simple.simpleapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.enma.app.simple.simpleapp.Models.Category
import com.google.gson.Gson
import com.himanshurawat.hasher.HashType
import com.himanshurawat.hasher.Hasher
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity(), ApiURLs {

    companion object {
        val catsList = ArrayList<Category>()
    }
    lateinit var adapter:CategoriesRvAdapter
    var url = ""
    lateinit var queue:RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = CategoriesRvAdapter(this)
        categoriesRV.adapter = adapter
        categoriesRV.layoutManager = LinearLayoutManager(this)

        val signature = Hasher.hash(APP_ID + APP_SECRET + HASH_KEY, HashType.MD5).toLowerCase()
        url = "$GET_CATEGORIES_URL&signature=$signature"
        queue = Volley.newRequestQueue(this)
        getCats()
    }

    private fun getCats(){
        val getCatsReq = StringRequest(Request.Method.GET, url,
                Response.Listener{s->
                    val data = JSONObject(s).getJSONArray("data")
                    for(i in 0 until data.length()){
                        val cat = Gson().fromJson(data.getJSONObject(i).toString(),Category::class.java)
                        catsList.add(cat)
                    }
                    adapter.notifyDataSetChanged()
                },
                Response.ErrorListener {
                    queue.cancelAll("c")
                    getCats()
                })
        getCatsReq.retryPolicy = DefaultRetryPolicy(0,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        getCatsReq.tag = "c"
        queue.add(getCatsReq)
    }
}
