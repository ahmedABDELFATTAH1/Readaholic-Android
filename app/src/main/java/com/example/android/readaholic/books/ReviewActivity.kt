package com.example.android.readaholic.books

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.android.readaholic.R
import com.example.android.readaholic.profile_and_profile_settings.Profile
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_review.*
import kotlinx.android.synthetic.main.commentticket.view.*

import org.json.JSONArray
import org.json.JSONObject
class ReviewActivity : AppCompatActivity() {
    var CommentList:ArrayList<CommentInfo>?=null
    var commentadapter: CommentsAdabterlist?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)
        CommentList= ArrayList()
        commentadapter= CommentsAdabterlist()
        commentlist.adapter=commentadapter
        feedBookData()
        feedReviewDate()
        feedCommentsDataFromURL(2)

        swiperefreshcomment.setOnRefreshListener {
            CommentList!!.clear()
            feedCommentsDataFromURL(4)
            swiperefreshcomment.isRefreshing=false
        }
    }

    /**
     * git the book data  and assign them to the ui
     *
     */

    fun feedBookData()
    {
        booktitleui.text=Cbookdata.book_title
        authornameui.text=Cbookdata.author_name
      //  Picasso.get().load("https://i.ytimg.com/vi/3nmoffllWTw/hqdefault.jpg").into(bookimage)
    }

    /**
     * git the review data and assign them to the ui
     *
     */
    fun feedReviewDate()
    {
        reviwernametxtui.text=Creviewdata.username
        ratingui.rating=Creviewdata.rating.toFloat()
        dateofreviewtxtui.text=Creviewdata.lastupdate
        descriptionreviewui.text=Creviewdata.reviewbody
        numberoflikesreviewtxtui.text=Creviewdata.numberoflikes.toString()
        numberofcommentreviewtxtui.text=Creviewdata.numberofcomments.toString()
       // Picasso.get().load("https://i.ytimg.com/vi/3nmoffllWTw/hqdefault.jpg").into(reviewerimage)
    }

    /**
     * got to the provided end point url and get the json return of the list of commints and send it to git the data from it
     *
     * @param bookid
     */
    fun feedCommentsDataFromURL(bookid:Int)
    {

        val queue = Volley.newRequestQueue(this)
        var urltry="https://api.myjson.com/bins/10fnna"
        val stringRequest = StringRequest(Request.Method.GET,urltry,
                Response.Listener<String> { response ->
                    var  jsonresponse= JSONObject(response)
                    feedFromJsonReturn(jsonresponse!!.getJSONArray("comment"))
                    commentadapter!!.notifyDataSetChanged()
                },
                Response.ErrorListener {

                    commentadapter!!.notifyDataSetChanged()
                })

        queue.add(stringRequest)

    }

    /**
     * extract the comments data from the jsno return and assign it to the array of comments
     *
     * @param jsonarray
     */
    fun feedFromJsonReturn(jsonarray: JSONArray)
    {

        for( i in 0..jsonarray.length()-1)
        {
            var jsonobject=jsonarray.getJSONObject(i)
            CommentList!!.add(CommentInfo(jsonobject.getString("id").toInt(),jsonobject.getJSONObject("user").getString("id").toInt(),jsonobject.getJSONObject("user").getString("name")
            ,jsonobject.getJSONObject("user").getString("image_url"),jsonobject.getString("body")))
        }
           // Toast.makeText(this,"dfsdf",Toast.LENGTH_SHORT).show()
    }

    /**
     * This adapter deal with comments list
     *
     */

    inner class CommentsAdabterlist(): BaseAdapter()
    {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myview=layoutInflater.inflate(R.layout.commentticket,null)
            var currentcomment= CommentList!![position]
            myview.reviwernametxtui.text=currentcomment.username
            myview.dateofreviewtxtui.text=currentcomment.commentdate
            myview.descriptionreviewui.text=currentcomment.commentbody
          //  Picasso.get().load("https://i.ytimg.com/vi/3nmoffllWTw/hqdefault.jpg").into(myview.usercommentimage)

            myview.usercommentimage.setOnClickListener {
                var intent= Intent(baseContext, Profile::class.java)
                startActivity(intent)
            }
            return myview
        }

        override fun getItem(position: Int): Any {
            return  CommentList!![position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return CommentList!!.size
        }


    }
}