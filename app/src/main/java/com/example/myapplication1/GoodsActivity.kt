package com.example.myapplication1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ms.square.android.expandabletextview.ExpandableTextView
import kotlinx.android.synthetic.main.activity_goods.*

class GoodsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods)
        val text = intent.getStringExtra("id")
        text_id.text = text
        val expTv1 : ExpandableTextView = findViewById(R.id.expand_text_view)
        expTv1.setText("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq")
    }

}