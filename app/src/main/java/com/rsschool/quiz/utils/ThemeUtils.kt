package com.rsschool.quiz.utils

import com.rsschool.quiz.R

fun getThemeCycled(n: Int) = arrayOf(
    R.style.Theme_Quiz_First,
    R.style.Theme_Quiz_Second,
    R.style.Theme_Quiz_Third,
).run { get(n % size) }