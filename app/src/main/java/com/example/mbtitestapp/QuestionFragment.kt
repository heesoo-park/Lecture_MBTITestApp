package com.example.mbtitestapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast

class QuestionFragment : Fragment() {
    // 현재 질문 페이지
    private var questionType: Int = 0

    // 타이틀
    private val questionTitle = listOf(
        R.string.question1_title,
        R.string.question2_title,
        R.string.question3_title,
        R.string.question4_title,
    )

    // 질문
    private val questionTexts = listOf(
        listOf(
            R.string.question1_1,
            R.string.question1_2,
            R.string.question1_3,
        ),
        listOf(
            R.string.question2_1,
            R.string.question2_2,
            R.string.question2_3,
        ),
        listOf(
            R.string.question3_1,
            R.string.question3_2,
            R.string.question3_3,
        ),
        listOf(
            R.string.question4_1,
            R.string.question4_2,
            R.string.question4_3,
        ),
    )

    // 선택지
    private val questionAnswers = listOf(
        listOf(
            listOf(R.string.question1_1_answer1, R.string.question1_1_answer2),
            listOf(R.string.question1_2_answer1, R.string.question1_2_answer2),
            listOf(R.string.question1_3_answer1, R.string.question1_3_answer2),
        ),
        listOf(
            listOf(R.string.question2_1_answer1, R.string.question2_1_answer2),
            listOf(R.string.question2_2_answer1, R.string.question2_2_answer2),
            listOf(R.string.question2_3_answer1, R.string.question2_3_answer2),
        ),
        listOf(
            listOf(R.string.question3_1_answer1, R.string.question3_1_answer2),
            listOf(R.string.question3_2_answer1, R.string.question3_2_answer2),
            listOf(R.string.question3_3_answer1, R.string.question3_3_answer2),
        ),
        listOf(
            listOf(R.string.question4_1_answer1, R.string.question4_1_answer2),
            listOf(R.string.question4_2_answer1, R.string.question4_2_answer2),
            listOf(R.string.question4_3_answer1, R.string.question4_3_answer2),
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // newInstance 함수로 인해 생성되면 거기서 argument로 받아온 questionType 값으로 현재 페이지 설정
        arguments?.let {
            questionType = it.getInt(ARG_QUESTION_TYPE)
        }
    }

    // View가 만들어질 때
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 사용할 레이아웃 가져오기
        val view = inflater.inflate(R.layout.fragment_question, container, false)
        // 타이틀 세팅
        val title: TextView = view.findViewById(R.id.questionTitle_text)
        title.text = getString(questionTitle[questionType])
        // 질문 세팅
        val questionTextView = listOf<TextView>(
            view.findViewById(R.id.question1_text),
            view.findViewById(R.id.question2_text),
            view.findViewById(R.id.question3_text)
        )
        // 선택지 세팅
        val answerRadioGroup = listOf<RadioGroup>(
            view.findViewById(R.id.answer1_radioGroup),
            view.findViewById(R.id.answer2_radioGroup),
            view.findViewById(R.id.answer3_radioGroup)
        )
        // 질문 및 선택지 초기화
        for (i in questionTextView.indices) {
            questionTextView[i].text = getString(questionTexts[questionType][i])

            val radioBtn1 = answerRadioGroup[i].getChildAt(0) as RadioButton
            val radioBtn2 = answerRadioGroup[i].getChildAt(1) as RadioButton

            radioBtn1.text = getString(questionAnswers[questionType][i][0])
            radioBtn2.text = getString(questionAnswers[questionType][i][1])
        }

        return view
    }
    // View가 만들어지고나서
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 라디오그룹 세팅
        val answerRadioGroup = listOf<RadioGroup>(
            view.findViewById(R.id.answer1_radioGroup),
            view.findViewById(R.id.answer2_radioGroup),
            view.findViewById(R.id.answer3_radioGroup)
        )

        val nextBtn: Button = view.findViewById(R.id.next_btn)
        // 다음 버튼을 누를 때
        nextBtn.setOnClickListener {
            // 모든 라디오그룹에서 하나씩 선택되었는지
            val isAllAnswered = answerRadioGroup.all { it.checkedRadioButtonId != -1}

            if (isAllAnswered) {
                // map 함수로 하나씩 돌면서 선택 결과를 가져옴
                val response = answerRadioGroup.map { radioGroup ->
                    val firstRadioButton = radioGroup.getChildAt(0) as RadioButton
                    if (firstRadioButton.isChecked) 1 else 2
                }
                // TestActivity에서 초기화해놓은 questionnaireResults 클래스 안의 addResponses 함수 실행
                // -> 1과 2 중에 더 많은 값이 저장됨
                (activity as? TestActivity)?.questionnaireResults?.addResponses(response)
                // 프래그먼트 이동(다음 질문지로 이동)
                (activity as? TestActivity)?.moveToNextQuestion()
            } else {
                Toast.makeText(context, "모든 질문에 답해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    // 이건 아직 잘 모르겠음
    companion object {
        private const val ARG_QUESTION_TYPE = "questionType"

        fun newInstance(questionType: Int): QuestionFragment {
            val fragment = QuestionFragment()
            val args = Bundle()
            args.putInt(ARG_QUESTION_TYPE, questionType)
            fragment.arguments = args
            return fragment
        }
    }
}