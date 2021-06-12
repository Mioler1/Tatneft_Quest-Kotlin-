package com.example.tatneft_quest.models

class TestQuestionsModel(
    private var idPoint: Int,
    private var numberQuestion: Int,
    private var textQuestion: String,
    private var answerOne: String,
    private var answerTwo: String,
    private var answerThree: String,
    private var answerCorrect: String,
) {
    fun getIdPoint(): Int {
        return idPoint
    }

    fun getNumberQuestion(): Int {
        return numberQuestion
    }

    fun getTextQuestion(): String {
        return textQuestion
    }

    fun getAnswerOne(): String {
        return answerOne
    }

    fun getAnswerTwo(): String {
        return answerTwo
    }

    fun getAnswerThree(): String {
        return answerThree
    }

    fun getAnswerCorrect(): String {
        return answerCorrect
    }
}