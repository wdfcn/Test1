package com.example.myapplication1

import com.teprinciple.mailsender.Mail

class mailsender {
    fun mailsender(address:String, title:String, text:String): Mail {
        //三个参数依次为收件地址，邮件主题，邮件内容
        //gardle 加依赖
        //implementation 'com.teprinciple:mailsender:1.2.0'
        //android里加
        // packagingOptions {
        //        exclude 'META-INF/NOTICE.md'
        //        exclude 'META-INF/LICENSE.md'
        //}
        //Manifest加上
        //  <uses-permission android:name="android.permission.INTERNET" />
        //  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
        return Mail().apply {
            // 发件箱服务器地址
            mailServerHost = "smtp.qq.com"
            // 发件箱服务器端口
            mailServerPort = "587"
            // 发件邮箱地址
            fromAddress = "194456938@qq.com"
            // 发件邮箱授权码
            password = "zwtchenewcfibgjd"
            // 收件箱列表
            toAddress = arrayListOf(address)
            // 邮件主题
            subject =  title
            // 邮件内容
            content = text
        }
    }
    /*override fun onSuccess() {
        Toast.makeText(this@MainActivity, "邮件已发送，请确认查收", Toast.LENGTH_SHORT).show()
    }*/
    //生成验证码
    fun getcap(): String{
        val words = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var text = ""
        //生成五位随机验证码
        for (i in 1..5){
            val randoms = (0..61).random()
            val word = words[randoms]
            text = text + word
        }
        return text
    }

    //确认验证码
    fun checkcap(text:String, pass:String): Boolean{
        var check = true
        for (i in 0..4){
            if(text[i] != pass[i] && text[i] != pass[i]+ 32 && text[i] != pass[i] - 32)
                check = false
        }
        return check
    }
}