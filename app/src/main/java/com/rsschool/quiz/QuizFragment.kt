package com.rsschool.quiz

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.radiobutton.MaterialRadioButton
import com.rsschool.quiz.data.getSampleQuestions
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.utils.getThemeCycled

class QuizFragment : Fragment() {
    private lateinit var binding: FragmentQuizBinding
    private val args: QuizFragmentArgs by navArgs()
    private val questions = getSampleQuestions()

    // Mutable state
    private var selectedAnswerIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val wrapper = ContextThemeWrapper(context, getThemeCycled(args.questionIndex))
        val customThemeInflater = inflater.cloneInContext(wrapper)
        binding = FragmentQuizBinding.inflate(customThemeInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: persist answers between fragment transactions
        populateWithData()
        attachListeners()
        adjustButtons()
    }

    private fun populateWithData() {
        val question = questions[args.questionIndex]
        binding.toolbar.title = "Question ${args.questionIndex + 1}"
        binding.question.text = question.question
        for (answer in question.answers) {
            val button = MaterialRadioButton(requireContext())
            button.text = answer
            button.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            binding.radioGroupAnswers.addView(button)
        }
    }

    private fun adjustButtons() {
        if (args.questionIndex == questions.lastIndex) {
            binding.nextButton.visibility = View.GONE
            binding.submitButton.visibility = View.VISIBLE
        }

        // Buttons are disabled by default
        if (args.questionIndex != 0) {
            binding.previousButton.isEnabled = true
        }
    }

    private fun attachListeners() {
        binding.radioGroupAnswers.setOnCheckedChangeListener { group, checkedId ->
            // Assuming there are only relevant views under the RadioGroup
            selectedAnswerIndex = group.indexOfChild(group.findViewById(checkedId))
            binding.nextButton.isEnabled = true
            binding.submitButton.isEnabled = true
        }

        binding.toolbar.setNavigationOnClickListener {
            if (args.questionIndex == 0) {
                activity?.moveTaskToBack(true)
            } else {
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
    }
}