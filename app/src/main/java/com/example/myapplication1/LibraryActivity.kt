package com.example.myapplication1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_library.*

class LibraryActivity : AppCompatActivity() {
    val taglist:ArrayList<String> = ArrayList<String>()
    val goodslist:ArrayList<GoodsInfo> = ArrayList<GoodsInfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)
        val layout = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        list1.layoutManager = layout
        val adapter1 = TagAdapter(taglist)
        list1.adapter = adapter1
        //putData()
        val layout2 = LinearLayoutManager(this)
        list2.layoutManager = layout2
        val adapter2 = GoodsAdapter(goodslist)
        list2.adapter = adapter2
        search_edit.setOnKeyListener(object : View.OnKeyListener {
            //输入完后按键盘上的搜索键【回车键改为了搜索键】
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (keyCode == KeyEvent.KEYCODE_ENTER) { //改动回车键功能
                    val tag = search_edit.text.toString()
                    if (tag!=""){
                        taglist.add(tag)
                        list1.adapter?.notifyDataSetChanged()
                        search_edit.setText("")
                    }
                }
                return false
            }
        })
    }

    class TagViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tag: Button = view.findViewById(R.id.tag_button)
    }
    //适配器
    private inner class TagAdapter(val taglist:List<String>): RecyclerView.Adapter<TagViewHolder>()
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
            holder.itemView.setOnClickListener {
                delete_tag(position)
                list1.adapter?.notifyDataSetChanged()
                //list1.adapter?.notifyItemRemoved(position)
                //notifyItemRangeChanged(position, getItemCount())
            }
        }
        override fun getItemCount(): Int {
            return taglist.size
        }
    }

    class GoodsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.goods_name)
        val img : ImageView = view.findViewById(R.id.img)
        var id = ""
    }
    //适配器
    private inner class GoodsAdapter(val goodslist:List<GoodsInfo>): RecyclerView.Adapter<GoodsViewHolder>()
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
            holder.id = info.goodsid.toString()
            holder.name.setText(info.goodsname)
            holder.img.setImageResource(info.goodspic)
        }
        override fun getItemCount(): Int {
            return goodslist.size
        }
    }

    fun delete_tag(p:Int){
        taglist.removeAt(p)
    }
}