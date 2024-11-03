package com.example.flashquizzer.model

data class Flashcard ( val front: String, val back: String,var currentSide: String = front )