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
        const val SORT_KEY = "SORT_KEY"
        const val REPEAT_MODE_KEY = "REPEAT_MODE_KEY"
    }


    fun saveSort(value:Int){
        viewModelScope.launch(Dispatchers.IO) {
            repo.putInt(SORT_KEY,value)
        }
    }

    fun getSort() : Int = runBlocking {
        repo.getInt(SORT_KEY) ?: 1
    }

    fun saveRepeatMode(value:Int){
        viewModelScope.launch(Dispatchers.IO) {
            repo.putInt(REPEAT_MODE_KEY,value)
        }
    }


    suspend fun getRepeatMode(): Int = repo.getInt(REPEAT_MODE_KEY) ?: 0

}