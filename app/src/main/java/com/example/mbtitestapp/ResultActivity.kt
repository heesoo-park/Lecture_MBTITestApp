package com.example.mbtitestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.util.Locale

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        // intent로 넘어온 값 받기
        val results = intent.getIntegerArrayListExtra("results") ?: arrayListOf()

        val resultTypes = listOf(
            listOf("E", "I"),
            listOf("N", "S"),
            listOf("T", "F"),
            listOf("J", "P")
        )

        // 넘어온 값에 맞는 MBTI 유형 저장
        var resultString = ""
        for (i in results.indices) {
            resultString += resultTypes[i][results[i] - 1]
        }
        // MBTI 유형을 TextView에 띄우기
        val resultValueText: TextView = findViewById(R.id.resultValue_text)
        resultValueText.text = resultString

        // MBTI 유형을 ImageView에 띄우기
        val resultImg: ImageView = findViewById(R.id.result_img)
        // MBTI 유형에 맞고 drawable폴더에 있는 이미지 파일을 가져오기 위한 코드
        val imageResource = resources.getIdentifier("ic_${resultString.toLowerCase(Locale.ROOT)}", "drawable", packageName)
        resultImg.setImageResource(imageResource)

        // 다시 테스트 버튼 클릭 이벤트
        val retryBtn: Button = findViewById(R.id.retry_btn)
        retryBtn.setOnClickListener {
            // 첫 화면으로 이동
            val intent: Intent = Intent(this@ResultActivity, MainActivity::class.java)
            // 중간에 사용했던 액티비티의 내용을 지우고 새로 시작
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}