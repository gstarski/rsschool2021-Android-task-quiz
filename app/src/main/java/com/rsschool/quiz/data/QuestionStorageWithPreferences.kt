package com.rsschool.quiz.data

import android.content.SharedPreferences
import androidx.core.content.edit

internal class QuestionStorageWithPreferences(private val prefs: SharedPreferences) :
        QuestionStorage {
    override fun getQuestions() = getSampleQuestions()

    override fun saveAnswerIndex(answerIndex: Int, questionIndex: Int) {
        prefs.edit {
            val key = getKeyForSelectedAnswerIndex(questionIndex)
            putInt(key, answerIndex)
        }
    }

    override fun getAnswerIndex(questionIndex: Int): Int {
        val key = getKeyForSelectedAnswerIndex(questionIndex)
        return prefs.getInt(key, -1)
    }

    override fun clearAnswers() {
        prefs.edit { clear() }
    }

    private fun getKeyForSelectedAnswerIndex(questionIndex: Int)
        = "QUESTION_${questionIndex}_ANSWER"
}