package com.rsschool.quiz.data

import android.content.SharedPreferences

interface QuestionStorage {
    fun getQuestions(): List<SingleChoiceQuizQuestion>
    fun saveAnswerIndex(answerIndex: Int, questionIndex: Int)
    fun getAnswerIndex(questionIndex: Int): Int
    fun clearAnswers()

    companion object {
        operator fun invoke(prefs: SharedPreferences): QuestionStorage
            = QuestionStorageWithPreferences(prefs)
    }
}

