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

import android.widget.ExpandableListView
import android.widget.ExpandableListView.*



var spinner_id:Int = 1

class MainActivity : AppCompatActivity(), MailSender.OnMailSendListener{
    val goodsList:ArrayList<GoodsInfo> = ArrayList<GoodsInfo>()
    val imglist:ArrayList<GoodsInfo> = ArrayList<GoodsInfo>()
    val hotlist:ArrayList<GoodsInfo> = ArrayList<GoodsInfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val layoutManager2 = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        val layoutManager = GridLayoutManager(this,2);
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
        //spinner.setSelection(1, true)
        var get_text =""
        val sender = mailsender()
        //spinner.setOnItemClickListener { view, view, i, l ->  }
        var sp_first = true
        var sp_first2 = true
        spinner.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,view: View,position: Int,id: Long)
            {
                val field: Field =AdapterView::class.java.getDeclaredField("mOldSelectedPosition")
                field.setAccessible(true) //设置mOldSelectedPosition可访问
                field.setInt(spinner, AdapterView.INVALID_POSITION) //设置mOldSelectedPosition的值
                if(sp_first) {
                    spinner.setSelection(-1, true)
                    sp_first = false
                }
                else{
                    val type = spinner.getSelectedItem().toString()
                    if(type == "访问商店界面"){
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
                field.setAccessible(true) //设置mOldSelectedPosition可访问
                field.setInt(spinner2, AdapterView.INVALID_POSITION) //设置mOldSelectedPosition的值
                if(sp_first2) {
                    spinner2.setSelection(-1, true)
                    sp_first2 = false
                }
                else{
                    val type = spinner2.getSelectedItem().toString()
                    if(type == "访问商店界面"){
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
    }

    override fun onError(e: Throwable) {
        Toast.makeText(this@MainActivity, "发送失败: $e.message", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess() {
        Toast.makeText(this@MainActivity, "邮件已发送，请确认查收", Toast.LENGTH_SHORT).show()
    }

    //双行小图
    class GoodsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val goodspic: ImageView = view.findViewById(R.id.goods_pic)
        val goodsname: TextView = view.findViewById(R.id.goods_name)
        val goodsprice: TextView = view.findViewById(R.id.goods_price)
        var id = ""
    }
    //适配器
    private inner class GoodsAdapter(val goodsList:List<GoodsInfo>): RecyclerView.Adapter<GoodsViewHolder>()
    {
        //创建视图
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.goods_elem_layout1,parent,false)
            return  GoodsViewHolder(view)
        }

        //绑定数据
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

        //列表行数
        override fun getItemCount(): Int {
            return goodsList.size
        }

    }


    //大图
    class ImgViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pic: ImageView = view.findViewById(R.id.img)
        val name : TextView = view.findViewById(R.id.text_name)
        val price : TextView = view.findViewById(R.id.text_price)
        var id = ""
    }
    //适配器
    private inner class ImgAdapter(val imgList:List<GoodsInfo>): RecyclerView.Adapter<ImgViewHolder>()
    {
        //创建视图
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.img_elem_layout1,parent,false)
            return  ImgViewHolder(view)
        }

        //绑定数据
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
        //列表行数
        override fun getItemCount(): Int {
            return imgList.size
        }

    }


    //热销
    class hotViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val goodspic: ImageView = view.findViewById(R.id.goods_pic)
        val goodsname: TextView = view.findViewById(R.id.goods_name)
        val goodsprice: TextView = view.findViewById(R.id.goods_price)
        var id = ""
    }
    //适配器
    private inner class hotAdapter(val hotList:List<GoodsInfo>): RecyclerView.Adapter<hotViewHolder>()
    {
        //创建视图
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): hotViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.hot_elem_layout1,parent,false)
            return  hotViewHolder(view)
        }

        //绑定数据
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

        //列表行数
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

    //下拉菜单初始值设定
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
        goodsList.add(GoodsInfo(R.drawable._2, 1, "双点医院", "20"))
        goodsList.add(GoodsInfo(R.drawable._5, 2, "双人成行", "20"))
        goodsList.add(GoodsInfo(R.drawable._2, 3, "双点医院", "20"))
        goodsList.add(GoodsInfo(R.drawable._5, 4, "双人成行", "20"))
    }

    fun putData2()
    {
        imglist.add(GoodsInfo(R.drawable._2, 1, "双点医院", "10"))
        imglist.add(GoodsInfo(R.drawable._5, 2, "双人成行", "20"))
        imglist.add(GoodsInfo(R.drawable._2, 3, "双点医院", "30"))
    }

    fun putData3()
    {
        hotlist.add(GoodsInfo(R.drawable._2, 1, "双点医院", "20"))
        hotlist.add(GoodsInfo(R.drawable._5, 2, "双人成行", "20"))
        hotlist.add(GoodsInfo(R.drawable._2, 3, "双点医院", "20"))
        hotlist.add(GoodsInfo(R.drawable._5, 4, "双人成行", "20"))
    }

}