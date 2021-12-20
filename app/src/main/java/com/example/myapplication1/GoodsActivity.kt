package com.example.myapplication1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ms.square.android.expandabletextview.ExpandableTextView
import kotlinx.android.synthetic.main.activity_goods.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.img_elem_layout2.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.net.URLEncoder
import kotlin.concurrent.thread

class GoodsActivity : AppCompatActivity() {
    val goodsList:ArrayList<GoodsInfo> = ArrayList<GoodsInfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods)
        val text = intent.getStringExtra("id")
        text_id.text = text
        val layoutManager1 = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        list.layoutManager=layoutManager1
        val adapter1 = GoodsAdapter(goodsList)
        list.adapter = adapter1
        putData()
        val sql1 = "select price from goods where goods_id = "+text
        buyprice.setText(GetInfo(sql1,"price"))
        val sql2 = "select name from goods where goods_id = "+text
        val name = GetInfo(sql2,"name")
        buyname.setText(name)
        goodsname.setText(name)
        //img1.setImageResource(list)
        val expTv1 : ExpandableTextView = findViewById(R.id.expand_text_view)
        expTv1.setText("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq")
        btn_addtocart.setOnClickListener {
            val sql1 = "insert into cart (user_id, goods_id) values ('"+ user_id+"','"+text+"')"
            InsertData(sql1)
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }


    class GoodsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val goodspic: ImageView = view.findViewById(R.id.img2)
        var id = ""
    }

    //适配器
    private inner class GoodsAdapter(val goodsList:List<GoodsInfo>): RecyclerView.Adapter<GoodsViewHolder>()
    {
        //创建视图
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.img_elem_layout2,parent,false)
            return  GoodsViewHolder(view)
        }

        //绑定数据
        override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
            val info =goodsList[position]
            holder.goodspic.setImageResource(info.goodspic)
            holder.id = info.goodsid.toString()
            holder.itemView.setOnClickListener {
                img1.setImageResource(info.goodspic)
            }
        }

        //列表行数
        override fun getItemCount(): Int {
            return goodsList.size
        }

    }

    fun putData()
    {
        goodsList.add(GoodsInfo(R.drawable._2, 1, "双点医院", "20"))
        goodsList.add(GoodsInfo(R.drawable._5, 2, "双人成行", "20"))
        goodsList.add(GoodsInfo(R.drawable._2, 3, "双点医院", "20"))
        goodsList.add(GoodsInfo(R.drawable._5, 4, "双人成行", "20"))
    }

    fun InsertData(sql:String) {
        thread {
            try {
                val client = OkHttpClient()
                val requestBody= FormBody.Builder()
                    .add("sql", URLEncoder.encode(sql, "UTF-8"))
                    .build()
                val request = Request.Builder()
                    .url("http://$ip:8080/mobile_steam_server_war_exploded/get_goods.jsp")
                    .post(requestBody)
                    .build()
                val response = client.newCall(request).execute()
                val responseData =response.body?.string()
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun GetInfo(sql:String,text: String): String{
        var result = ""
        thread {
            try {
                val client = OkHttpClient()
                val requestBody= FormBody.Builder()
                    .add("sql", URLEncoder.encode(sql, "UTF-8"))
                    .build()
                val request = Request.Builder()
                    .url("http://$ip:8080/mobile_steam_server_war_exploded/get_goods.jsp")
                    .post(requestBody)
                    .build()
                val response = client.newCall(request).execute()
                val responseData =response.body?.string()
                result = parseXml(responseData,text)
            }catch (e: Exception){
                e.printStackTrace()
            }

        }
        return result
    }

    fun parseXml(xmlData: String?, text:String): String{
        var result = ""
        try {
            val factory = XmlPullParserFactory.newInstance()
            val parser =factory.newPullParser()
            parser.setInput(StringReader(xmlData))
            var eventType = parser.eventType
            while (eventType!= XmlPullParser.END_DOCUMENT){
                val nodeName=parser.name
                when(eventType){
                    XmlPullParser.START_TAG->{
                        when(nodeName){
                            text->{
                                result = parser.nextText()
                            }
                        }

                    }
                    XmlPullParser.END_TAG->{
                        if("elem"==nodeName){
                            return result
                        }

                    }
                }
                eventType=parser.next()
            }
        }
        catch (e: Exception){
            e.printStackTrace()
        }
        return result
    }
}