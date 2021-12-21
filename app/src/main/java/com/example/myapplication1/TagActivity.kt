package com.example.myapplication1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_goods.*

class TagActivity : AppCompatActivity() {
    val tag_list = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag)
        val layoutManager2 = GridLayoutManager(this,4)
        taglist.layoutManager=layoutManager2
        val adapter2 = TagAdapter(tag_list)
        taglist.adapter = adapter2
    }

    class TagViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tag : Button = view.findViewById(R.id.tag)
    }

    //适配器
    private inner class TagAdapter(val tag_list:List<String>): RecyclerView.Adapter<TagActivity.TagViewHolder>()
    {
        //创建视图
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagActivity.TagViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.tag_elem_layout,parent,false)
            return  TagViewHolder(view)
        }

        //绑定数据
        override fun onBindViewHolder(holder: TagActivity.TagViewHolder, position: Int) {
            val info =tag_list[position]
            holder.tag.setText(info.toString())
        }

        //列表行数
        override fun getItemCount(): Int {
            return tag_list.size
        }

    }

    fun gettag(){
        var gamelist = arrayListOf<Int>()
        var tag = arrayListOf<String>()//游戏的标签
        var taglist = arrayListOf<String>()//非重复的已购标签
        //从数据库读所有游戏id，存入gamelist
        for (item in gamelist){
            //用gamelist里的id在goodstag里搜索，存入tag
            for (item in tag)
            {
                if(taglist.contains(item)==false)
                {
                    //如果没有此标签，添加
                    taglist.add(item)
                }
            }
        }
        for (item in taglist){
            tag_list.add(item)
        }
    }

}