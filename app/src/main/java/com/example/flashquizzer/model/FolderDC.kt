package com.example.flashquizzer.model

data class FolderDC(var name: String = "", var id: String = "", var decks: List<Deck>? = null) // Data class to represent a folder of decks