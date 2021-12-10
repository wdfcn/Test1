package com.example.myapplication1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_wishlist.*

import androidx.recyclerview.widget.ItemTouchHelper



class WishlistActivity : AppCompatActivity() {
    val taglist:ArrayList<String> = ArrayList<String>()
    val goodslist:ArrayList<GoodsInfo> = ArrayList<GoodsInfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)
        val layout = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        list1.layoutManager = layout
        val adapter1 = TagAdapter(taglist)
        list1.adapter = adapter1
        putData()
        val layout2 = LinearLayoutManager(this)
        list2.layoutManager = layout2
        val adapter2 = GoodsAdapter(goodslist)
        list2.adapter = adapter2
        putData2()
        search_edit.setOnKeyListener(object : View.OnKeyListener {
            //输入完后按键盘上的搜索键【回车键改为了搜索键】
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (keyCode == KeyEvent.KEYCODE_ENTER) { //改动回车键功能
                }
                return false
            }
        })
    }


    class TagViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tag: Button = view.findViewById(R.id.tag_button)
    }
    //适配器
    private class TagAdapter(val taglist:List<String>): RecyclerView.Adapter<TagViewHolder>()
    {
        //创建视图
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.wishlist1_layout,parent,false)
            return  TagViewHolder(view)
        }

        //绑定数据
        override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
            val info = taglist[position]
            holder.tag.setText(info)
            }
        override fun getItemCount(): Int {
            return taglist.size
        }
    }

    class GoodsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.goods_name)
        val delete : Button = view.findViewById(R.id.delete)
        val num : TextView = view.findViewById(R.id.num)
        val id = ""
    }
    //适配器
    private class GoodsAdapter(val goodslist:List<GoodsInfo>): RecyclerView.Adapter<GoodsViewHolder>()
    {
        //创建视图
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.wishlist2_layout,parent,false)
            return  GoodsViewHolder(view)
        }

        //绑定数据
        override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
            val info = goodslist[position]
            holder.name.setText(info.goodsname)
            holder.num.setText(position)
        }
        override fun getItemCount(): Int {
            return goodslist.size
        }
    }


    fun putData()
    {
        taglist.add("a")
        taglist.add("b")
        taglist.add("c")
    }
    fun putData2()
    {
        goodslist.add(GoodsInfo(R.drawable._2, 1, "双点医院", "20"))
        goodslist.add(GoodsInfo(R.drawable._5, 2, "双人成行", "20"))
        goodslist.add(GoodsInfo(R.drawable._2, 3, "双点医院", "20"))
        goodslist.add(GoodsInfo(R.drawable._5, 4, "双人成行", "20"))
    }



}