package com.rsschool.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rsschool.quiz.data.QuestionStorage
import com.rsschool.quiz.databinding.FragmentResultBinding
import kotlin.math.roundToInt

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!! // valid only between onCreateView and onDestroyView

    private lateinit var questionStorage: QuestionStorage

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questionStorage = QuestionStorage(activity?.getPreferences(Context.MODE_PRIVATE)!!)
        val questions = questionStorage.getQuestions()
        val results = questions.mapIndexed { questionIndex, question ->
            val answerIndex = questionStorage.getAnswerIndex(questionIndex)
            object {
                val question = question.question
                val answeredCorrectly = answerIndex == question.correctAnswerIndex
                val answer = when {
                    answerIndex != -1 -> question.answers.toList()[answerIndex]
                    else -> "Error: no answer"
                }
            }
        }

        val correctCount = results.count { it.answeredCorrectly }
        val percents = (correctCount.toFloat() / questions.count() * 100).roundToInt()

        binding.textResult.text = "Your result: $percents %"

        binding.buttonShare.setOnClickListener {
            val sb = StringBuilder()
            sb.appendLine("Quiz results\n\nYour result: $percents %\n")

            for ((index, result) in results.withIndex()) {
                sb.appendLine("${index + 1}) ${result.question}")
                sb.appendLine("Your answer: ${result.answer}\n")
            }

            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, sb.toString())
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        binding.buttonRestart.setOnClickListener {
            restartTest()
        }

        binding.buttonExit.setOnClickListener {
            questionStorage.clearAnswers()
            activity?.finishAndRemoveTask()
        }

        activity?.onBackPressedDispatcher?.addCallback {
            restartTest()
        }
    }

    private fun restartTest() {
        questionStorage.clearAnswers()
        val action = ResultFragmentDirections.actionResultFragmentToQuizFragment(0)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}