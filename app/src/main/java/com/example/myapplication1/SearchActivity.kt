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
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.list1

class SearchActivity : AppCompatActivity() {
    val goodsList:ArrayList<GoodsInfo> = ArrayList<GoodsInfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val layout = LinearLayoutManager(this)
        list1.layoutManager = layout
        val adapter1 = GoodsAdapter(goodsList)
        list1.adapter = adapter1
    }
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
                LayoutInflater.from(parent.context).inflate(R.layout.search_elem_layout,parent,false)
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
                id_trans(holder)
            }
        }

        //列表行数
        override fun getItemCount(): Int {
            return goodsList.size
        }

    }
    fun id_trans(view : GoodsViewHolder)
    {
        val intent = Intent(this, GoodsActivity::class.java)
        intent.putExtra("id", view.id)
        startActivity(intent)
    }
}