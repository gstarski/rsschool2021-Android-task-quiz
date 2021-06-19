package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.radiobutton.MaterialRadioButton
import com.rsschool.quiz.data.QuestionStorage
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.utils.getThemeCycled

class QuizFragment : Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!! // valid only between onCreateView and onDestroyView

    private val args: QuizFragmentArgs by navArgs()

    private var selectedAnswerIndex = -1

    private lateinit var questionStorage: QuestionStorage

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.setTheme(getThemeCycled(args.questionIndex))
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questionStorage = QuestionStorage(activity?.getPreferences(Context.MODE_PRIVATE)!!)
        selectedAnswerIndex = questionStorage.getAnswerIndex(args.questionIndex)
        populateWithData()
        attachListeners()
        adjustButtons()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun populateWithData() {
        val question = questionStorage.getQuestions()[args.questionIndex]
        binding.toolbar.title = "Question ${args.questionIndex + 1}"
        binding.question.text = question.question
        for ((index, answer) in question.answers.withIndex()) {
            val button = MaterialRadioButton(requireContext())
            button.text = answer
            button.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            binding.radioGroupAnswers.addView(button)
            if (index == selectedAnswerIndex) {
                binding.radioGroupAnswers.check(button.id)
            }
        }
    }

    private fun adjustButtons() {
        if (args.questionIndex == questionStorage.getQuestions().lastIndex) {
            binding.nextButton.visibility = View.GONE
            binding.submitButton.visibility = View.VISIBLE
        }

        if (args.questionIndex == 0) {
            binding.toolbar.navigationIcon = null
        }

        // Buttons are disabled by default
        if (args.questionIndex != 0) {
            binding.previousButton.isEnabled = true
        }

        if (selectedAnswerIndex != -1) {
            binding.nextButton.isEnabled = true
            binding.submitButton.isEnabled = true
        }
    }

    private fun attachListeners() {
        binding.radioGroupAnswers.setOnCheckedChangeListener { group, checkedId ->
            // Assuming there are only relevant views under the RadioGroup
            selectedAnswerIndex = group.indexOfChild(group.findViewById(checkedId))
            binding.nextButton.isEnabled = true
            binding.submitButton.isEnabled = true
            questionStorage.saveAnswerIndex(selectedAnswerIndex, args.questionIndex)
        }

        binding.toolbar.setNavigationOnClickListener {
            if (args.questionIndex != 0) {
                it.findNavController().popBackStack()
            }
        }

        // Safety is guaranteed by buttons visibility and enabled state
        binding.nextButton.setOnClickListener {
            val nextIndex = args.questionIndex + 1
            val action = QuizFragmentDirections.actionQuizFragmentSelf(nextIndex)
            it.findNavController().navigate(action)
        }

        binding.previousButton.setOnClickListener {
            it.findNavController().popBackStack()
        }

        binding.submitButton.setOnClickListener {
            val action = QuizFragmentDirections.actionQuizFragmentToResultFragment()
            it.findNavController().navigate(action)
        }
    }
}