package com.rsschool.quiz.data

fun getSampleQuestions() = listOf(
    SingleChoiceQuizQuestion(
        "I am lying. Am I telling the truth?",
        listOf(
            "Yes",
            "No",
            "It depends...",
            "It's a contradiction",
            "I don't know"
        ),
        3
    ),
    SingleChoiceQuizQuestion(
        "What is the largest moon in the Solar system?",
        listOf(
            "Europa",
            "Phobos",
            "Titan",
            "Ganymede",
            "Io",
            "I don't know"
        ),
        3
    ),
    SingleChoiceQuizQuestion(
        "The night is dark and full of ...",
        listOf(
            "pleasures",
            "terrors",
            "werewolves",
            "lunatics",
            "I don't know"
        ),
        1
    ),
    SingleChoiceQuizQuestion(
        "What is five times nine?",
        listOf(
            "Forty",
            "At least forty",
            "At most forty-three",
            "Undefined",
            "I don't know"
        ),
        1
    ),
    SingleChoiceQuizQuestion(
        "What was the first artificial Earth satellite?",
        listOf(
            "Sputnik 1",
            "Explorer 1",
            "Soyuz",
            "Pioneer 1",
            "Progress",
            "I don't know"
        ),
        0
    ),
    SingleChoiceQuizQuestion(
        "What kind of bear is best?",
        listOf(
            "That's ridiculous question",
            "That's debatable",
            "White bear",
            "Black Bear",
            "There are basically two schools of thought"
        ),
        3
    ),
    SingleChoiceQuizQuestion(
        "What is the Answer to the Ultimate Question of Life, The Universe, and Everything?",
        listOf(
            "22",
            "24",
            "42",
            "44",
            "I don't know"
        ),
        2
    ),
)