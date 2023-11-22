package com.example.mbtitestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2

class TestActivity : AppCompatActivity() {

    // ViewPager 선언
    private lateinit var viewPager: ViewPager2
    // 프래그먼트에서 질문결과를 저장하기 위해 객체 선언 및 초기화
    val questionnaireResults = QuestionnaireResults()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        viewPager = findViewById(R.id.view_pager)
        // viewPager와 fragment를 연결하기 위한 어댑터 설정
        viewPager.adapter = ViewPagerAdapter(this)
        // 사용자가 만대로 슬라이드할 수 없도록 설정
        viewPager.isUserInputEnabled = false
    }

    fun moveToNextQuestion() {
        if (viewPager.currentItem == 3) {
            // 마지막 페이지이면 결과 화면으로 이동
            val intent = Intent(this@TestActivity, ResultActivity::class.java)
            // 구해놨던 질문 결과 넘겨주기
            intent.putIntegerArrayListExtra("results", ArrayList(questionnaireResults.results))
            startActivity(intent)
        } else {
            // 마지막 페이지가 아니라면 다음 화면으로 이동
            val nextItem = viewPager.currentItem + 1
            if (nextItem < (viewPager.adapter?.itemCount ?: 0)) {
                viewPager.setCurrentItem(nextItem, true)
            }
        }
    }
}

class QuestionnaireResults {
    val results = mutableListOf<Int>()

    fun addResponses(response: List<Int>) {
        // 들어온 리스트 값을 그룹화하여 더 큰 값을 가진 수를 results에 저장
        val mostFrequent = response.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
        // results += mostFrequent!!는 별로인가...?
        mostFrequent?.let { results.add(it) }
    }
}