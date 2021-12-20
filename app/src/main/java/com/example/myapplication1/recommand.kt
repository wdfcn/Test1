package com.example.myapplication1

import okhttp3.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.lang.Exception
import java.net.URLEncoder
import kotlin.concurrent.thread

class recommand{

    //随机推荐用户最喜欢的两个tag里的未购买的游戏
    fun recommand(id:Int): Pair<Int,Int>{
        var pair = recommandtag(id)
        var idlist = arrayListOf<Int>()
        var id1 = 0
        var id2 = 0
        var num1 = 0
        var num2 = 0
        idlist = rec_list(id,pair.first)
        num2 = idlist.size
        num1 = (0..num2).random()
        id1 = num1
        idlist = rec_list(id,pair.second)
        num2 = idlist.size
        num1 = (0..num2).random()
        id2 = num1
        return Pair(id1, id2)
    }

    //用户所购游戏tag非重复集合
    fun userttag(id:Int): ArrayList<String>{
        var boughtlist = arrayListOf<Int>()//已购游戏
        var tag = arrayListOf<String>()//已购游戏的标签集合
        var taglist = arrayListOf<String>()//非重复的已购标签
        val sql = "select distinct id from bought where user_id = "+id
        var sql2 = ""
        boughtlist = getData(sql)
        for (item in boughtlist){
            sql2 = "select distinct tag from goodstag where goods_id = "+item
            tag = getData2(sql2)
            for (item in tag)
            {
                if(taglist.contains(item)==false)
                {
                    //如果没有此标签，添加
                    taglist.add(item)
                }
            }
        }
        return taglist
    }

    //用户没有的包含tag的游戏
    fun rec_list(id:Int, tag:String): ArrayList<Int> {
        var boughtlist = arrayListOf<Int>()//用户已购列表
        var taglist = arrayListOf<Int>()//此tag包含的所有游戏
        var list = arrayListOf<Int>()
        val sql = "select distinct goods_id from goodstag where tag = "+tag
        taglist = getData(sql)
        val sql2 = "select distinct goods_id from bought where user_id = "+id
        boughtlist = getData(sql2)
        var num = 0
        for (item in taglist)
        {
            num = item
            if (boughtlist.contains(num)==false)
                list.add(num)
        }
        return list
    }

    //用户第一第二喜欢的tag
    fun recommandtag(id:Int): Pair<String, String> {
        var boughtlist = arrayListOf<Int>()//已购游戏
        var tag = arrayListOf<String>()//已购游戏的标签集合
        var taglist = arrayListOf<String>()//非重复的已购标签
        var tagnum = arrayListOf<Int>()//标签的数量
        var first_tag : String = ""//重复最多的标签
        var second_tag : String = ""//重复第二多的标签
        var max:Int = 0
        val sql = "select id from bought where user_id = "+id
        var sql2 = ""
        boughtlist = getData(sql)
        for (item in boughtlist){
            sql2 = "select tag from goodstag where goods_id = "+item
            tag = getData2(sql2)
            for (item in tag)
            {
                if(taglist.contains(item)==false)
                {
                    //如果没有此标签，添加
                    taglist.add(item)
                    tagnum.add(1)
                }
                else
                {
                    val num = taglist.indexOf(item)
                    tagnum[num]++
                }
            }
        }
        //找出最大和第二大的tag
        for (i in tagnum.indices)
        {
            if(tagnum[i]>max)
            {
                max = tagnum[i]
                second_tag = first_tag
                first_tag = taglist[i]
            }
        }
        return Pair(first_tag, second_tag)
    }

    fun getData(sql:String): ArrayList<Int> {
        var list=arrayListOf<Int>()
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
                list = parseXml(responseData)
            }catch (e: Exception){
                e.printStackTrace()
            }

        }
        return list
    }

    fun parseXml(xmlData: String?) : ArrayList<Int>{
        val list=arrayListOf<Int>()
        try {
            val factory = XmlPullParserFactory.newInstance()
            val parser =factory.newPullParser()
            parser.setInput(StringReader(xmlData))
            var eventType = parser.eventType
            var id=""
            while (eventType!= XmlPullParser.END_DOCUMENT){
                val nodeName=parser.name
                when(eventType){
                    XmlPullParser.START_TAG->{
                        when(nodeName){
                            "id"->{
                                id = parser.nextText()

                            }
                        }

                    }
                    XmlPullParser.END_TAG->{
                        if("elem"==nodeName){
                            list.add(id.toInt())
                        }

                    }
                }
                eventType=parser.next()
            }
        }
        catch (e: Exception){
            e.printStackTrace()
        }
        return list
    }

    fun getData2(sql:String): ArrayList<String> {
        var list=arrayListOf<String>()
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
                list = parseXml2(responseData)
            }catch (e: Exception){
                e.printStackTrace()
            }

        }
        return list
    }

    fun parseXml2(xmlData: String?) : ArrayList<String>{
        val list=arrayListOf<String>()
        try {
            val factory = XmlPullParserFactory.newInstance()
            val parser =factory.newPullParser()
            parser.setInput(StringReader(xmlData))
            var eventType = parser.eventType
            var tag=""
            while (eventType!= XmlPullParser.END_DOCUMENT){
                val nodeName=parser.name
                when(eventType){
                    XmlPullParser.START_TAG->{
                        when(nodeName){
                            "tag"->{
                                tag = parser.nextText()

                            }
                        }

                    }
                    XmlPullParser.END_TAG->{
                        if("elem"==nodeName){
                            list.add(tag.toString())
                        }

                    }
                }
                eventType=parser.next()
            }
        }
        catch (e: Exception){
            e.printStackTrace()
        }
        return list
    }
}