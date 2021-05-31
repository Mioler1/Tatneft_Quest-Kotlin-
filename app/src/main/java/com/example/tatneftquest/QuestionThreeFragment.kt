package com.example.tatneftquest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.tatneftquest.Fragments.BaseFragment
import com.example.tatneftquest.TravelPackage.QuestFragment
import com.example.tatneftquest.TravelPackage.StartActionFragment
import com.example.tatneftquest.databinding.FragmentQuestionOneBinding
import com.example.tatneftquest.databinding.FragmentQuestionThreeBinding

class QuestionThreeFragment : BaseFragment() {
    private lateinit var binding: FragmentQuestionThreeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentQuestionThreeBinding.inflate(inflater)
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
            afterAnsweringOne("Ответ на вопрос 3/3:", "Объединение \"Татнефть\" получило статус акционерного общества 1994 году.","Результат", "7", "5", "9")
            binding.nextQuestion.visibility = View.VISIBLE
            binding.reply.visibility = View.GONE
        }
        binding.nextQuestion.setOnClickListener {
            mFragmentHandler?.replace(StartActionFragment())
        }
    }

}