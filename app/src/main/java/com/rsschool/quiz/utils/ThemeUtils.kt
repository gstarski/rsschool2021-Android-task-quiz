package com.rsschool.quiz.utils

import com.rsschool.quiz.R

fun getThemeCycled(n: Int) = arrayOf(
    R.style.Theme_Quiz_First,
    R.style.Theme_Quiz_Second,
    R.style.Theme_Quiz_Third,
    R.style.Theme_Quiz_Fourth,
    R.style.Theme_Quiz_Fifth,
    R.style.Theme_Quiz_Sixth,
    R.style.Theme_Quiz_Seventh,
).run { get(n % size) }