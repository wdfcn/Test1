package com.example.myapplication1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.teprinciple.mailsender.Mail
import com.teprinciple.mailsender.MailSender
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.AdapterView
import java.lang.reflect.Field
import android.widget.TextView

import android.widget.BaseExpandableListAdapter
import android.widget.Toast
import android.widget.ExpandableListView.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.lang.Exception
import java.net.URLEncoder
import kotlin.concurrent.thread


var spinner_id:Int = 1

class MainActivity : AppCompatActivity(), MailSender.OnMailSendListener{
    val goodsList:ArrayList<GoodsInfo> = ArrayList<GoodsInfo>()
    val imglist:ArrayList<GoodsInfo> = ArrayList<GoodsInfo>()
    val hotlist:ArrayList<GoodsInfo> = ArrayList<GoodsInfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val layoutManager2 = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        val layoutManager = GridLayoutManager(this,2)
        val hotlayout = LinearLayoutManager(this)
        hot_list.layoutManager = hotlayout
        list1.layoutManager=layoutManager
        list2.layoutManager=layoutManager2
        val adapter1 = GoodsAdapter(goodsList)
        val adapter2 = ImgAdapter(imglist)
        val adapter3 = hotAdapter(hotlist)
        list1.adapter = adapter1
        list2.adapter = adapter2
        hot_list.adapter = adapter3
        putData()
        putData2()
        putData3()
        setspinner()

        //list2 ????????????
        val rec = recommand()
        val cmd = sqlcommand()
        val pair = rec.recommand(user_id)
        val pair2 = rec.recommandtag(user_id)
        var sql = "select * from goods where id = "
        sql = sql + pair.first.toString()
        getData(sql, goodsList)
        sql = sql + pair.second.toString()
        getData(sql, goodsList)
        recommand1.setText(pair2.first)
        recommand2.setText(pair2.first)
        /*var sql1 = "select name from goods where goods_id = "+pair.first
        cmd.GetInfo(sql1,"name")*/
        var get_text =""
        val sender = mailsender()
        //spinner.setOnItemClickListener { view, view, i, l ->  }
        var sp_first = true
        var sp_first2 = true
        spinner.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,view: View,position: Int,id: Long)
            {
                val field: Field =AdapterView::class.java.getDeclaredField("mOldSelectedPosition")
                field.setAccessible(true) //??????mOldSelectedPosition?????????
                field.setInt(spinner, AdapterView.INVALID_POSITION) //??????mOldSelectedPosition??????
                if(sp_first) {
                    spinner.setSelection(-1, true)
                    sp_first = false
                }
                else{
                    val type = spinner.getSelectedItem().toString()
                    if(type == "??????????????????"){
                        spinner_tranid(spinner_id)
                        Toast.makeText(this@MainActivity, type, Toast.LENGTH_SHORT).show()
                    }
                    else{
                        spinner_tranid(spinner_id)
                        Toast.makeText(this@MainActivity, type, Toast.LENGTH_SHORT).show()
                    }
                }
                sp_first = false
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {sp_first=false }
        })

        spinner2.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,view: View,position: Int,id: Long)
            {
                val field: Field =AdapterView::class.java.getDeclaredField("mOldSelectedPosition")
                field.setAccessible(true) //??????mOldSelectedPosition?????????
                field.setInt(spinner2, AdapterView.INVALID_POSITION) //??????mOldSelectedPosition??????
                if(sp_first2) {
                    spinner2.setSelection(-1, true)
                    sp_first2 = false
                }
                else{
                    val type = spinner2.getSelectedItem().toString()
                    if(type == "??????????????????"){
                        spinner_tranid2(spinner_id)
                        Toast.makeText(this@MainActivity, type, Toast.LENGTH_SHORT).show()
                    }
                    else{
                        spinner_tranid2(spinner_id)
                        Toast.makeText(this@MainActivity, type, Toast.LENGTH_SHORT).show()
                    }
                }
                sp_first = false
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {sp_first2=false }
        })

        towishlist.setOnClickListener {
            val intent = Intent(this, WishlistActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onError(e: Throwable) {
        Toast.makeText(this@MainActivity, "????????????: $e.message", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess() {
        Toast.makeText(this@MainActivity, "?????????????????????????????????", Toast.LENGTH_SHORT).show()
    }

    //????????????
    class GoodsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val goodspic: ImageView = view.findViewById(R.id.goods_pic)
        val goodsname: TextView = view.findViewById(R.id.goods_name)
        val goodsprice: TextView = view.findViewById(R.id.goods_price)
        var id = ""
    }
    //?????????
    private inner class GoodsAdapter(val goodsList:List<GoodsInfo>): RecyclerView.Adapter<GoodsViewHolder>()
    {
        //????????????
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.goods_elem_layout1,parent,false)
            return  GoodsViewHolder(view)
        }

        //????????????
        override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
            val info =goodsList[position]
            holder.goodspic.setImageResource(info.goodspic)
            holder.goodsname.text=info.goodsname
            holder.goodsprice.text=info.goodsprice
            holder.id = info.goodsid.toString()
            holder.itemView.setOnClickListener {
                id_trans2(holder)
            }
        }

        //????????????
        override fun getItemCount(): Int {
            return goodsList.size
        }

    }

    //??????
    class ImgViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pic: ImageView = view.findViewById(R.id.img)
        val name : TextView = view.findViewById(R.id.text_name)
        val price : TextView = view.findViewById(R.id.text_price)
        var id = ""
    }
    //?????????
    private inner class ImgAdapter(val imgList:List<GoodsInfo>): RecyclerView.Adapter<ImgViewHolder>()
    {
        //????????????
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.img_elem_layout1,parent,false)
            return  ImgViewHolder(view)
        }

        //????????????
        override fun onBindViewHolder(holder: ImgViewHolder, position: Int) {
            val info = imgList[position]
            holder.pic.setImageResource(info.goodspic)
            holder.name.text = info.goodsname
            holder.price.text = info.goodsprice
            holder.id = info.goodsid.toString()
            holder.itemView.setOnClickListener {
                id_trans1(holder)
            }
        }
        //????????????
        override fun getItemCount(): Int {
            return imgList.size
        }

    }


    //??????
    class hotViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val goodspic: ImageView = view.findViewById(R.id.goods_pic)
        val goodsname: TextView = view.findViewById(R.id.goods_name)
        val goodsprice: TextView = view.findViewById(R.id.goods_price)
        var id = ""
    }
    //?????????
    private inner class hotAdapter(val hotList:List<GoodsInfo>): RecyclerView.Adapter<hotViewHolder>()
    {
        //????????????
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): hotViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.hot_elem_layout1,parent,false)
            return  hotViewHolder(view)
        }

        //????????????
        override fun onBindViewHolder(holder: hotViewHolder, position: Int) {
            val info =goodsList[position]
            holder.goodspic.setImageResource(info.goodspic)
            holder.goodsname.text=info.goodsname
            holder.goodsprice.text=info.goodsprice
            holder.id = info.goodsid.toString()
            holder.itemView.setOnClickListener {
                id_trans3(holder)
            }
        }

        //????????????
        override fun getItemCount(): Int {
            return goodsList.size
        }

    }




    /*private var mOnItemClickListener: OnItemClickListener? = null


    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }*/
    fun id_trans1(view : ImgViewHolder)
    {
        val intent = Intent(this, GoodsActivity::class.java)
        intent.putExtra("id", view.id)
        startActivity(intent)
    }

    fun id_trans2(view : GoodsViewHolder)
    {
        val intent = Intent(this, GoodsActivity::class.java)
        intent.putExtra("id", view.id)
        startActivity(intent)
    }

    fun id_trans3(view : hotViewHolder)
    {
        val intent = Intent(this, GoodsActivity::class.java)
        intent.putExtra("id", view.id)
        startActivity(intent)
    }

    fun spinner_tranid(id:Int){
        val intent = Intent(this, GoodsActivity::class.java)
        intent.putExtra("id", id.toString())
        startActivity(intent)
    }

    fun spinner_tranid2(id:Int){
        val intent = Intent(this, GoodsActivity::class.java)
        intent.putExtra("id", id.toString())
        startActivity(intent)
    }

    //???????????????????????????
    fun setspinner()
    {
        val  mItems=resources.getStringArray(R.array.spinnername)
        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, mItems)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(R.layout.spinner_dropdown_item)
        //Set Adapter to Spinner
        spinner!!.setAdapter(aa)
        spinner2!!.setAdapter(aa)
    }

    fun putData()
    {
        goodsList.add(GoodsInfo(R.drawable._2, 1, "????????????", "20"))
        goodsList.add(GoodsInfo(R.drawable._5, 2, "????????????", "20"))
        goodsList.add(GoodsInfo(R.drawable._2, 3, "????????????", "20"))
        goodsList.add(GoodsInfo(R.drawable._5, 4, "????????????", "20"))
    }

    fun putData2()
    {
        imglist.add(GoodsInfo(R.drawable._2, 1, "????????????", "10"))
        imglist.add(GoodsInfo(R.drawable._5, 2, "????????????", "20"))
        imglist.add(GoodsInfo(R.drawable._2, 3, "????????????", "30"))
    }

    fun putData3()
    {
        hotlist.add(GoodsInfo(R.drawable._2, 1, "????????????", "20"))
        hotlist.add(GoodsInfo(R.drawable._5, 2, "????????????", "20"))
        hotlist.add(GoodsInfo(R.drawable._2, 3, "????????????", "20"))
        hotlist.add(GoodsInfo(R.drawable._5, 4, "????????????", "20"))
    }



    fun getData(sql:String, listm:ArrayList<GoodsInfo>) {
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
                parseXml(responseData,listm)
            }catch (e: Exception){
                e.printStackTrace()
            }

        }

    }

    fun parseXml(xmlData: String?, listn:ArrayList<GoodsInfo>) {
        try {
            val factory = XmlPullParserFactory.newInstance()
            val parser =factory.newPullParser()
            parser.setInput(StringReader(xmlData))
            var eventType = parser.eventType
            var name=""
            var price=""
            var id=""
            while (eventType!= XmlPullParser.END_DOCUMENT){
                val nodeName=parser.name
                when(eventType){
                    XmlPullParser.START_TAG->{
                        when(nodeName){
                            "id"->{
                                id = parser.nextText()

                            }
                            "name"->{
                                name=parser.nextText()

                            }
                            "price"->{
                                price=parser.nextText()
                            }
                        }

                    }
                    XmlPullParser.END_TAG->{
                        if("elem"==nodeName){
                            listn.add(GoodsInfo(id.toInt(),id.toInt(),name,price))
                        }

                    }
                }
                eventType=parser.next()
            }
        }
        catch (e: Exception){
            e.printStackTrace()
        }

    }

}