package com.example.myapplication1

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
        //img1.setImageResource(list)
        val expTv1 : ExpandableTextView = findViewById(R.id.expand_text_view)
        expTv1.setText("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq")
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

}