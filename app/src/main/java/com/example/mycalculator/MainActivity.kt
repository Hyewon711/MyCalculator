package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.mycalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var resultTextView: TextView
    private var currentInput = ""
    private var currentOperator: ((Double, Double) -> Double)? = null
    private var currentResult = 0.0
    val TAG: String = "로그"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        resultTextView = binding.resultED
        with(binding) {
            BtnPlus.setOnClickListener { onOperatorClick("BtnPlus") }
            BtnMinus.setOnClickListener { onOperatorClick("BtnMinus") }
            BtnMul.setOnClickListener { onOperatorClick("BtnMul") }
            BtnDiv.setOnClickListener { onOperatorClick("BtnDiv") }
            BtnResult.setOnClickListener { onOperatorClick("BtnResult") }
            BtnDel.setOnClickListener { onOperatorClick("BtnDel") }
            Btn00.setOnClickListener { onNumberClick(0) }
            Btn01.setOnClickListener { onNumberClick(1) }
            Btn02.setOnClickListener { onNumberClick(2) }
            Btn03.setOnClickListener { onNumberClick(3) }
            Btn04.setOnClickListener { onNumberClick(4) }
            Btn05.setOnClickListener { onNumberClick(5) }
            Btn06.setOnClickListener { onNumberClick(6) }
            Btn07.setOnClickListener { onNumberClick(7) }
            Btn08.setOnClickListener { onNumberClick(8) }
            Btn09.setOnClickListener { onNumberClick(9) }
            BtnPoint.setOnClickListener { onDecimalPointClick(BtnPoint) }
        }
    }
    /* 결과 업데이트 */
    private fun updateResult() {
        if (currentOperator != null && currentInput.isNotEmpty()) {
            val inputNumber = currentInput.toDouble()
            currentResult = currentOperator!!(currentResult, inputNumber)

            if (currentResult % 1 == 0.0) { // 소수점이 .0인 경우 int형으로 출력
                resultTextView.text = (currentResult.toInt()).toString()
            } else { // 소수점이 .0이 아닌 경우 double형으로 출력
                resultTextView.text = currentResult.toString()
            }
            currentInput = ""
        }
    }

    /* 숫자 버튼 */
    private fun onNumberClick(btnNumber: Int) {
        currentInput += btnNumber
        resultTextView.text = currentInput
    }

    /* point (.) 버튼 */
    private fun onDecimalPointClick(view: View) {
        if(currentInput.isNotEmpty()) {
            if (currentInput.isEmpty() || !currentInput.contains(".")) {
                currentInput += "."
                resultTextView.text = currentInput
            }
        } else {
            Toast.makeText(applicationContext, "소수점은 맨 앞에 올 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    /* 사칙연산 버튼 */
    private fun onOperatorClick(btnOperation: String) {
        when (btnOperation) {
                "BtnPlus" -> { currentOperator = { a, b -> a + b }
                    updateResult()
                }
                "BtnMinus" -> { currentOperator = { a, b -> a - b }
                    updateResult()
                }
                "BtnMul" -> { currentOperator = { a , b -> if(currentResult == 0.0) 1.0 * b else a * b }
                    updateResult()
                }
                "BtnDiv" -> { currentOperator = { a, b -> if(currentResult == 0.0) b / 1.0 else a / b  }
                    updateResult()
                }
                "BtnDel" -> {
                    currentOperator = null
                    resultTextView.text = ""
                    currentInput = ""
                    currentResult = 0.0
                }
                "BtnResult" -> updateResult()
            }
            currentInput = ""
    }
}