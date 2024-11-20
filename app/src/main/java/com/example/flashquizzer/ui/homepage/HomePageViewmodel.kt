package com.example.flashquizzer.ui.homepage

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class Folder(var name: String = "", var deck: List<Deck>? = null)
data class Deck(var name: String = "", var cards: List<Card>? = null)
data class Card(var question: String = "", var answer: String = "")

class HomePageViewmodel : ViewModel() {

    val folderCreation = mutableStateOf(false)
    val newFolderName = mutableStateOf("")
    val query = mutableStateOf("")

    var userFolders = mutableListOf<Folder>()

    fun onSearch() {
        query.value = ""
        TODO("Not yet implemented")
    }

    fun hasFolders(): Boolean {
        return userFolders.isNotEmpty()
    }

    fun goCreateFolder() {
        folderCreation.value = !folderCreation.value
    }

    fun createNewFolder() {
        goCreateFolder()
        val newFolder = Folder(name = newFolderName.value)
        userFolders.add(newFolder)
    }

    fun dontCreateFolder() {
        folderCreation.value = !folderCreation.value
    }
}

