package com.rsschool.quiz.data

data class SingleChoiceQuizQuestion(
    val question: String,
    val answers: Iterable<String>,
    val correctAnswerIndex: Int
)