package com.wirasukmasaputra.pemesanantiketpesawat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.wirasukmasaputra.pemesanantiketpesawat.database.DatabaseClient
import com.wirasukmasaputra.pemesanantiketpesawat.database.dao.DatabaseDao
import com.wirasukmasaputra.pemesanantiketpesawat.model.ModelDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    val dataList: LiveData<List<ModelDatabase>>
    private val databaseDao: DatabaseDao?

    init {
        databaseDao = DatabaseClient.getInstance(application)?.appDatabase?.databaseDao()
        dataList = databaseDao?.getAllData() ?: throw IllegalStateException("DatabaseDao is null")
    }

    fun deleteDataById(uid: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseDao?.deleteDataById(uid)
        }
    }
}
