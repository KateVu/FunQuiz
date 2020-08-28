package com.katevu.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_cheat.*

//pass back data to main activity
internal const val CHEAT_ANSWER_SHOWN = "answer shown"

class CheatActivity : AppCompatActivity() {
    private val TAG = "CheatActivity"
    private var answerIsTrue = false
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "CheatActivity called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answerIsTrue = intent.getBooleanExtra(CHEAT_ANSWER, false)

        showAnswerButton.setOnClickListener {
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }

            anwerTextView.setText(answerText)
            setAnswerShownResult(true)
        }

    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(CHEAT_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }
    }