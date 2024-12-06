package com.example.flashquizzer.ui.homepage

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashquizzer.model.FolderDC
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomePageViewmodel : ViewModel() {

    val folderCreation = mutableStateOf(false)
    val newFolderName = mutableStateOf("")
    val query = mutableStateOf("")

    private val _userFolders = MutableStateFlow<List<FolderDC>>(emptyList()) // MutableStateFlow to hold the list of user folders
    val userFolders: StateFlow<List<FolderDC>> = _userFolders // Expose the user folders as a StateFlow

    private val firebaseFirestore = FirebaseFirestore.getInstance() // Get an instance of FirebaseFirestore

    init {
        fetchFoldersFromFirebase() // Fetch the user folders from Firebase when the ViewModel is initialized
    }

    fun onSearch() {
        // Implement search functionality if needed.
    }

    fun hasFolders(): Boolean {
        return _userFolders.value.isNotEmpty()
    }

    fun goCreateFolder() {
        folderCreation.value = true
    }

    fun dontCreateFolder() {
        folderCreation.value = false
        newFolderName.value = ""
    }

    fun createNewFolder() {
        val folderName = newFolderName.value.trim()
        if (folderName.isNotEmpty()) {
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            if (userId == null) {
                // Handle unauthenticated user
                Log.e("HomePageViewmodel", "User not authenticated")
                return
            }
            viewModelScope.launch { // Launch a coroutine in the viewModelScope
                try {
                    val folderData = hashMapOf("name" to folderName)
                    val newFolderRef = firebaseFirestore.collection("users").document(userId)
                        .collection("folders")
                        .add(folderData)
                        .await()
                    fetchFoldersFromFirebase()
                    folderCreation.value = false
                    newFolderName.value = ""
                } catch (e: Exception) {
                    Log.e("HomePageViewmodel", "Error creating folder", e)
                }
            }
        }
    }

    private fun fetchFoldersFromFirebase() { // Function to fetch the user folders from Firebase
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            // Handle unauthenticated user
            Log.e("HomePageViewmodel", "User not authenticated")
            return
        }
        viewModelScope.launch { // Launch a coroutine in the viewModelScope
            try {
                val snapshot = firebaseFirestore.collection("users").document(userId)
                    .collection("folders")
                    .get()
                    .await()
                // Map the documents to a list of folders
                val foldersList = snapshot.documents.mapNotNull { document ->
                    val name = document.getString("name") ?: return@mapNotNull null
                    val id = document.id
                    FolderDC(name = name, id = id, decks = null)
                }
                _userFolders.value = foldersList
            } catch (e: Exception) {
                Log.e("HomePageViewmodel", "Error fetching folders", e)
            }
        }
    }
}
