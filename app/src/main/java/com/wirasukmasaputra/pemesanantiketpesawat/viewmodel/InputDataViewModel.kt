package com.wirasukmasaputra.pemesanantiketpesawat.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.wirasukmasaputra.pemesanantiketpesawat.database.DatabaseClient.Companion.getInstance
import com.wirasukmasaputra.pemesanantiketpesawat.database.dao.DatabaseDao
import com.wirasukmasaputra.pemesanantiketpesawat.model.ModelDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

class InputDataViewModel(application: Application) : AndroidViewModel(application) {
    private var databaseDao: DatabaseDao?

    // Function to insert data into the database
    @SuppressLint("CheckResult")
    fun addDataPemesan(
        namaPenumpang: String, keberangkatan: String,
        tujuan: String, tanggal: String, nomorTelepon: String,
        anakAnak: Int, dewasa: Int, hargaTiket: Int, kelas: String, status: String
    ) {
        Completable.fromAction {
            val modelDatabase = ModelDatabase().apply {
                this.namaPenumpang = namaPenumpang
                this.keberangkatan = keberangkatan
                this.tujuan = tujuan
                this.tanggal = tanggal
                this.nomorTelepon = nomorTelepon
                this.anakAnak = anakAnak
                this.dewasa = dewasa
                this.hargaTiket = hargaTiket
                this.kelas = kelas
                this.status = status
            }
            databaseDao?.insertData(modelDatabase)  // Safe call to insert data
        }
            .subscribeOn(Schedulers.io())  // Use IO thread for DB operation
            .observeOn(AndroidSchedulers.mainThread())  // Switch back to main thread for UI updates
            .subscribe(
                { /* onSuccess: you can handle success here */ },
                { throwable -> /* onError: handle error here */ }
            )
    }

    init {
        // Initialize the DAO instance safely
        databaseDao = getInstance(application)?.appDatabase?.databaseDao()
    }
}
