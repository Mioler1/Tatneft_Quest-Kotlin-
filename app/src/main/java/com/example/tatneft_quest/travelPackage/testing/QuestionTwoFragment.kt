package com.example.tatneft_quest.travelPackage.testing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.tatneft_quest.R
import com.example.tatneft_quest.fragments.BaseFragment
import com.example.tatneft_quest.databinding.FragmentQuestionTwoBinding

class QuestionTwoFragment : BaseFragment() {
    private lateinit var binding: FragmentQuestionTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentQuestionTwoBinding.inflate(inflater)
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
            binding.answerOne.background =
                ContextCompat.getDrawable(context!!, R.drawable.background_button_green)
            binding.answerOne.setTextColor(ContextCompat.getColor(context!!, R.color.white))
            binding.answerTwo.background =
                ContextCompat.getDrawable(context!!, R.drawable.border_button_green)
            binding.answerTwo.setTextColor(ContextCompat.getColor(context!!, R.color.green))
            binding.correctAnswer.background =
                ContextCompat.getDrawable(context!!, R.drawable.border_button_green)
            binding.correctAnswer.setTextColor(ContextCompat.getColor(context!!, R.color.green))
        }
        binding.answerTwo.setOnClickListener {
            binding.answerTwo.background =
                ContextCompat.getDrawable(context!!, R.drawable.background_button_green)
            binding.answerTwo.setTextColor(ContextCompat.getColor(context!!, R.color.white))
            binding.answerOne.background =
                ContextCompat.getDrawable(context!!, R.drawable.border_button_green)
            binding.answerOne.setTextColor(ContextCompat.getColor(context!!, R.color.green))
            binding.correctAnswer.background =
                ContextCompat.getDrawable(context!!, R.drawable.border_button_green)
            binding.correctAnswer.setTextColor(ContextCompat.getColor(context!!, R.color.green))
        }
        binding.correctAnswer.setOnClickListener {
            binding.correctAnswer.background =
                ContextCompat.getDrawable(context!!, R.drawable.background_button_green)
            binding.correctAnswer.setTextColor(ContextCompat.getColor(context!!, R.color.white))
            binding.answerOne.background =
                ContextCompat.getDrawable(context!!, R.drawable.border_button_green)
            binding.answerOne.setTextColor(ContextCompat.getColor(context!!, R.color.green))
            binding.answerTwo.background =
                ContextCompat.getDrawable(context!!, R.drawable.border_button_green)
            binding.answerTwo.setTextColor(ContextCompat.getColor(context!!, R.color.green))
        }
        binding.reply.setOnClickListener {
            afterAnsweringOne("Ответ на вопрос 2/3:", "Объединение \"Татнефть\" получило статус акционерного общества 1994 году.","Результат", "7", "5", "9")
            binding.nextQuestion.visibility = View.VISIBLE
            binding.reply.visibility = View.GONE
        }
        binding.nextQuestion.setOnClickListener {
            fillingQuestThree("Вопрос 3/3:", "Имя какого выдающегося совествого деятеля носит ПАО \"Татнефть\"?", "Выберите правильный ответ:", "Василия Александровича Динкова", "Валентина Дмитриевича Шашина","Алексея Тихоновича Шмарева")
            binding.nextQuestion.visibility = View.GONE
            binding.reply.visibility = View.VISIBLE
        }
    }

}