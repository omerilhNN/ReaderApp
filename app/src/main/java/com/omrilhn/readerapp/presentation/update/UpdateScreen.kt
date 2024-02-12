package com.omrilhn.readerapp.presentation.update

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.omrilhn.readerapp.core.domain.models.DataOrException
import com.omrilhn.readerapp.data.model.MBook
import com.omrilhn.readerapp.presentation.components.ReaderAppBar
import com.omrilhn.readerapp.presentation.home.HomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(navController: NavController,bookItemId:String,
                 viewModel:HomeViewModel = hiltViewModel()){

    Scaffold(topBar = { ReaderAppBar(title = "Update Book",
        icon = Icons.Default.ArrowBack,
        showProfile = false,
        navController = navController){
            navController.popBackStack()
    }}) {paddingValues->

        val bookInfo = produceState<DataOrException<List<MBook>,
                Boolean,
                Exception>>(initialValue = DataOrException(data = emptyList(),
                true,Exception(""))){
                    value = viewModel.data.value
        }.value

        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)){

            Column(modifier = Modifier.padding(top = 3.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally){
                Log.d("INFO","UpdateScreen ${viewModel.data.value.data.toString()}")

                if(bookInfo.loading == true){
                    LinearProgressIndicator()
                    bookInfo.loading = false
                }
                else{
                    Surface(modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth(),
                        shape = CircleShape,
                        shadowElevation = 4.dp){
                            ShowBookUpdate(bookInfo = viewModel.data.value,bookItemId = bookItemId)

                    }
                }
            }

        }

    }

}