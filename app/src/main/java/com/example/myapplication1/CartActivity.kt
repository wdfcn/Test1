package com.example.myapplication1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {
    val cartlist:ArrayList<GoodsInfo> = ArrayList<GoodsInfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        val layout = LinearLayoutManager(this)
        list.layoutManager = layout
        val adapter1 = CartAdapter(cartlist)
        list.adapter = adapter1
        putData()
    }
    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val goodspic: ImageView = view.findViewById(R.id.goods_pic)
        val goodsname: TextView = view.findViewById(R.id.goods_name)
        val goodsprice: TextView = view.findViewById(R.id.goods_price)
        val delete : Button = view.findViewById(R.id.delete)
        var id = ""
    }
    //适配器
    private class CartAdapter(val cartlist:List<GoodsInfo>): RecyclerView.Adapter<CartViewHolder>()
    {
        //创建视图
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.cart_elem_layout1,parent,false)
            return  CartViewHolder(view)
        }

        //绑定数据
        override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
            val info = cartlist[position]
            holder.goodspic.setImageResource(info.goodspic)
            holder.goodsname.text=info.goodsname
            holder.goodsprice.text=info.goodsprice
            holder.id = info.goodsid.toString()
            holder.delete.setOnClickListener {

            }
        }

        //列表行数
        override fun getItemCount(): Int {
            return cartlist.size
        }

    }

    fun putData()
    {
        cartlist.add(GoodsInfo(R.drawable._2, 1, "双点医院", "20"))
        cartlist.add(GoodsInfo(R.drawable._5, 2, "双人成行", "20"))
        cartlist.add(GoodsInfo(R.drawable._2, 3, "双点医院", "20"))
        cartlist.add(GoodsInfo(R.drawable._5, 4, "双人成行", "20"))
    }
}