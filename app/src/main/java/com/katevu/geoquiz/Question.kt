package com.katevu.geoquiz

import androidx.annotation.StringRes

data class Question (@StringRes val textResId: Int, val answer: Boolean) {
    override fun toString(): String {
        return "Question(textResId=$textResId, answer=$answer)"
    }
}