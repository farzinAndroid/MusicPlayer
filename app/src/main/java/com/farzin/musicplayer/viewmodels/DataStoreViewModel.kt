package com.farzin.musicplayer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farzin.musicplayer.data.datastore.DataStoreRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val repo:DataStoreRepositoryImpl
) : ViewModel(){



    companion object{
        val SORT_KEY = "SORT_KEY"
    }


    fun saveSort(value:Int){
        viewModelScope.launch(Dispatchers.IO) {
            repo.putInt(SORT_KEY,value)
        }
    }

    fun getSort() : Int = runBlocking {
        repo.getInt(SORT_KEY) ?: 1
    }

}