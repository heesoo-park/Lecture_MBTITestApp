package com.example.mbtitestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startBtn: ImageView = findViewById(R.id.start_img)

        // 이미지뷰를 버튼으로 사용
        startBtn.setOnClickListener {
            val intent: Intent = Intent(this@MainActivity, TestActivity::class.java)
            startActivity(intent)
        }
    }
}