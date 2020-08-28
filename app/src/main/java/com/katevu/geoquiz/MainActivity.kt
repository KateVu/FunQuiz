package com.katevu.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*


internal const val CHEAT_ANSWER = "Answer"
internal const val KEY_INDEX = "index"
internal const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private val quizViewModel: QuizViewModel by lazy { ViewModelProvider(this).get(QuizViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")
        updateQuestion()

        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        previousButton.setOnClickListener {
            quizViewModel.moveBack()
            updateQuestion()
        }

        cheatButton.setOnClickListener {
            val intent = Intent(this, CheatActivity::class.java).apply {
                putExtra(CHEAT_ANSWER, quizViewModel.currentQuestionAnswer)
            }
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
            Log.d(TAG, "cheatButton.setOnClickListener called intend: $intent")

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            quizViewModel.isCheater = data?.getBooleanExtra(CHEAT_ANSWER_SHOWN, false) ?: false
        }

        checkAnswer(false)

    }

    private fun updateQuestion() {
        questionTextView.setText(getString(quizViewModel.currentQuestionText))
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResID = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            (userAnswer == correctAnswer) -> R.string.correct_toast
            else -> {
                R.string.incorrect_toast
            }
        }
        quizViewModel.isCheater = false
        Toast.makeText(this, messageResID, Toast.LENGTH_LONG).show()
    }

}
