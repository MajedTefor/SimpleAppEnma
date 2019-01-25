package com.enma.app.simple.simpleapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.enma.app.simple.simpleapp.Models.Post
import com.google.gson.Gson
import com.himanshurawat.hasher.HashType
import com.himanshurawat.hasher.Hasher
import kotlinx.android.synthetic.main.activity_feeds.*
import org.json.JSONObject
import java.util.*

class FeedsActivity : AppCompatActivity(), ApiURLs {

    companion object {
        val postsList = ArrayList<Post>()
    }
    lateinit var adapter:PostsRvAdapter
    lateinit var queue: RequestQueue

    var url = ""

    var page = 1
    var limit = 10
    var sort = "recent"
    var categoryId = 2
    var isGetting = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feeds)
        FeedsActivity.postsList.clear()
        categoryId = intent.extras.getString("cat").toInt()
        adapter = PostsRvAdapter(this)
        postsRV.adapter = adapter
        postsRV.layoutManager = LinearLayoutManager(this)

        queue = Volley.newRequestQueue(this)
        getPosts()
        postsRV.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!recyclerView!!.canScrollVertically(1)){
                    if(!isGetting){
                        isGetting = true
                        page++
                        getPosts()

                    }
                }
            }
        })
    }

    private fun getPosts(){
        val signature = Hasher.hash(categoryId.toString() + sort + page.toString() + limit.toString()
                + APP_ID + APP_SECRET + HASH_KEY, HashType.MD5).toLowerCase()

        url = "$GET_POSTS_URL&category=$categoryId&sort=$sort&page=$page&limit=$limit&signature=$signature&app_id=$APP_ID&app_secret=$APP_SECRET"

        progressPB.visibility = View.VISIBLE
        val getPostsReq = StringRequest(Request.Method.GET, url,
                Response.Listener{ s->

                    Log.i("MAJD", s)
                    val data = JSONObject(s).getJSONArray("data")
                    for(i in 0 until data.length()){
                        val post = Gson().fromJson(data.getJSONObject(i).toString(),Post::class.java)
                        FeedsActivity.postsList.add(post)
                    }
                    adapter.notifyDataSetChanged()
                    progressPB.visibility = View.INVISIBLE
                    if(data.length() == 0)
                    {
                        progressPB.visibility = View.GONE
                    }
                    else
                    {
                        isGetting = false
                        Toast.makeText(this@FeedsActivity, "Page: " + page.toString(), Toast.LENGTH_LONG).show()
                    }
                },
                Response.ErrorListener {
                    queue.cancelAll("c")
                    getPosts()
                })
        getPostsReq.retryPolicy = DefaultRetryPolicy(0,0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        getPostsReq.tag = "c"
        queue.add(getPostsReq)
    }
}
