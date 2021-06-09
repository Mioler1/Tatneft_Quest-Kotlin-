package com.example.tatneft_quest.travelPackage.testing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.*
import com.example.tatneft_quest.R
import com.example.tatneft_quest.fragments.BaseFragment
import com.example.tatneft_quest.databinding.FragmentQuestionOneBinding

class QuestionOneFragment : BaseFragment() {
    private lateinit var binding: FragmentQuestionOneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentQuestionOneBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Квест"
        if (arguments != null) {
            binding.question.text = arguments?.getString("question")
            binding.descriptionQuestions.text = arguments?.getString("descriptionQuestions")
            binding.answerOne.text = arguments?.getString("answerOne")
            binding.answerTwo.text = arguments?.getString("answerTwo")
            binding.correctAnswer.text = arguments?.getString("correctAnswer")
        }

        binding.answerOne.setOnClickListener {
            binding.answerOne.background = getDrawable(requireContext(),
                R.drawable.background_button_green)
            binding.answerOne.setTextColor(getColor(requireContext(), R.color.white))
            binding.answerTwo.background = getDrawable(requireContext(), R.drawable.border_button_green)
            binding.answerTwo.setTextColor(getColor(requireContext(), R.color.green))
            binding.correctAnswer.background = getDrawable(requireContext(),
                R.drawable.border_button_green)
            binding.correctAnswer.setTextColor(getColor(requireContext(), R.color.green))
        }
        binding.answerTwo.setOnClickListener {
            binding.answerTwo.background = getDrawable(requireContext(),
                R.drawable.background_button_green)
            binding.answerTwo.setTextColor(getColor(requireContext(), R.color.white))
            binding.answerOne.background = getDrawable(requireContext(), R.drawable.border_button_green)
            binding.answerOne.setTextColor(getColor(requireContext(), R.color.green))
            binding.correctAnswer.background = getDrawable(requireContext(),
                R.drawable.border_button_green)
            binding.correctAnswer.setTextColor(getColor(requireContext(), R.color.green))
        }
        binding.correctAnswer.setOnClickListener {
            binding.correctAnswer.background = getDrawable(requireContext(),
                R.drawable.background_button_green)
            binding.correctAnswer.setTextColor(getColor(requireContext(), R.color.white))
            binding.answerOne.background = getDrawable(requireContext(), R.drawable.border_button_green)
            binding.answerOne.setTextColor(getColor(requireContext(), R.color.green))
            binding.answerTwo.background = getDrawable(requireContext(), R.drawable.border_button_green)
            binding.answerTwo.setTextColor(getColor(requireContext(), R.color.green))
        }

        binding.reply.setOnClickListener {
                binding.nextQuestion.visibility = View.VISIBLE
                binding.reply.visibility = View.GONE
                afterAnsweringOne(
                    "Ответ на вопрос 1/3:",
                    "В состав ПАО \"Татнефть\" входят 9 НГДУ: \"Азнакаевскнефть\", \"Альметьевнефть\", \"Бавлынефть\", \"Джалильнефть\", \"Елховнефть\", \"Прикамнефть\", \"Ямашнефть\".",
                    "Результат",
                    "7",
                    "5",
                    "9"
                )
                binding.answerOne.isClickable = false
                binding.answerTwo.isClickable = false
                binding.correctAnswer.isClickable = false
        }

        binding.nextQuestion.setOnClickListener {
            fillingQuestTwo(
                "Вопрос 2/3:",
                "Немного истории. Промышленная добыча нефти в республике Татарстан началась в 1943 году со скважины №1 вблизи села Шугурово, следующей знаменательной датой стало открытие Ромашкинского месторождения в 1948 году. Как вы думаете, к каком году объединение \"Татнефть\" было преобразовано в акционерное общество?",
                "Выберите правильный ответ:",
                "1985",
                "1990",
                "1994"
            )
            binding.nextQuestion.visibility = View.GONE
            binding.reply.visibility = View.VISIBLE
        }
    }
}