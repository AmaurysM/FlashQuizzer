package com.example.flashquizzer.ui.homepage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Folder(folder: Folder){
    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
            , horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = folder.name, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 10.dp))
            Text(text = "See more", modifier = Modifier.padding(horizontal = 10.dp), fontSize = MaterialTheme.typography.bodyMedium.fontSize)
        }

        if (folder.deck == null){
            Button(
                onClick = { /*TODO("Create A Deck")*/ }
                , shape = RoundedCornerShape(15.dp)
            ) {
                Icon( Icons.Default.Add, "", modifier = Modifier.size(150.dp))
            }
        } else {
            Row{
                folder.deck?.forEach { deck ->
                    Box(){
                        Text(text = deck.name)
                    }
                }
            }
        }
    }
}