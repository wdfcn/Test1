package com.example.myapplication1

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.net.URLEncoder
import kotlin.concurrent.thread

class sqlcommand {
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