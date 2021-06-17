package com.rsschool.quiz

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.radiobutton.MaterialRadioButton
import com.rsschool.quiz.data.getSampleQuestions
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.utils.getThemeCycled

class QuizFragment : Fragment() {
    private lateinit var binding: FragmentQuizBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val currentQuestionIndex = savedInstanceState?.getInt(CURRENT_QUESTION_INDEX_KEY) ?: 0
        val wrapper = ContextThemeWrapper(context, getThemeCycled(currentQuestionIndex))
        val customThemeInflater = inflater.cloneInContext(wrapper)
        binding = FragmentQuizBinding.inflate(customThemeInflater, container, false)

        val questions = getSampleQuestions()
        val question = questions[currentQuestionIndex]

        binding.toolbar.title = "Question ${currentQuestionIndex + 1}"
        binding.question.text = question.question
        for (answer in question.answers) {
            val button = MaterialRadioButton(requireContext())
            button.text = answer
            button.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            binding.radioGroup.addView(button)
        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            Toast.makeText(context, "Checked id: $checkedId", Toast.LENGTH_SHORT).show()
        }

        binding.nextButton.setOnClickListener {
            TODO("Go to next question")
        }

        binding.previousButton.setOnClickListener {
            TODO("Go to previous question")
        }

        return binding.root
    }

    companion object {
        private const val CURRENT_QUESTION_INDEX_KEY = "CURRENT_QUESTION_INDEX_KEY"
    }
}