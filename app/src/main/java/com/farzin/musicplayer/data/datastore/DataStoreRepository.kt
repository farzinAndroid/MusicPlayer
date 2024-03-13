package com.farzin.musicplayer.data.datastore

interface DataStoreRepository {
    suspend fun getInt(key:String) : Int?
    suspend fun putInt(key: String,value:Int)
}